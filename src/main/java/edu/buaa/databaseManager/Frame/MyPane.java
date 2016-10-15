package edu.buaa.databaseManager.Frame;

import java.awt.Dimension;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import edu.buaa.databaseManager.database.DatabaseHelper;
import edu.buaa.databaseManager.database.DatabaseResult;
import edu.buaa.databaseManager.database.Pair;
import edu.buaa.databaseManager.excel.ExcelExporter;
import edu.buaa.databaseManager.excel.ExcelFile;
import edu.buaa.databaseManager.excel.ExcelReader;

public class MyPane extends JPanel{
	
	String panelname;
	DatabaseHelper data;
	public List<Pair> columnhead = new ArrayList<>();
	JComboBox box = new JComboBox();
	TextField textfiled = new TextField();
	//public String[] columnnames = null ;
	public Vector<String> columnnames = new Vector<>();
	public Vector<Vector<Object>>columndata = new Vector<>();
	//public Object[][] columndata = null;
	private DefaultTableModel tableModel;
	 private JTable table;
	
	public MyPane(String name , DatabaseHelper helper){
		this.panelname=name;
		this.data = helper;
		
		initial();
		
		setLayout(new java.awt.BorderLayout());
		
		/************文本输入�?**************/
		
		textfiled.setColumns(30);
		
		/***********查询按钮*************/
		JButton check = new JButton();
		check.setText("查询");
		check.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				search(data);
			}});
		
		JPanel center = new JPanel();
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		

		
		/*************下拉框********************/
		
		
		for(int i = 0;i<columnhead.size();i++){
			box.addItem(columnhead.get(i).key);
		}
		int num = box.getSelectedIndex();
		//System.out.println(num);  
		
		
		
	
		
		/****************表格****************/
		
		
		for(int i = 0;i<columnhead.size();i++){
			columnnames.add(columnhead.get(i).key) ;
			//System.out.println(i);
		}
		
		
		DatabaseResult dataresult = new DatabaseResult();
		try {
			dataresult = data.search(panelname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = dataresult.getData();
		
		
	
		
		for(int i=0;i<attribute.size();i++){
			Vector<Object> temp = new Vector();
			for(int j = 0;j<columnhead.size();j++){
			
				temp.add(attribute.get(i).get(columnhead.get(j).key));
				
			}
			columndata.add(temp);
		}
		tableModel = new DefaultTableModel(columndata,columnnames);
        table = new JTable(tableModel);
        FitTableColumns(table);
        
        /******************多选****************/
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
		
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, 500));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		center.add(scrollPane);
	
		
	//	table.setPreferredScrollableViewportSize(new Dimension(500, 0));
     
        
		/**************按钮******************/
		JButton allselect = new JButton();
		JButton add  = new JButton();
		JButton dele  = new JButton ();
		JButton out = new JButton();
		JButton alldele = new JButton();
		JButton cleartable = new JButton();
		
		allselect.setText("全选");
		cleartable.setText("清空表格数据");
		add.setText("插入");
		dele.setText("删除数据");
		out.setText("导出");
		alldele.setText("删除表格");
		
		
		cleartable.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				clear();
			}});
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				add();
			}});
		
		out.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				out();
			}});
		
		allselect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				allselect();
			}});
		
		dele.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dele();
			}});
		alldele.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				alldele(data);
			}});
		
		/***************布局*******************/
		this.add("Center",center);
		this.add("North",north);
		this.add("South",south);
		
		north.add(textfiled);
		north.add(box);
		north.add(check);
		
		//center.add(table);	
		
	//	south.add(allselect);
		south.add(add);
		south.add(out);
	//	south.add(dele);
		south.add(cleartable);
		south.add(alldele);
	}
	
	public void add(){
		JFrame f  = new JFrame();
		JFileChooser jfc = new JFileChooser();
		
	     if(jfc.showOpenDialog(f)==JFileChooser.APPROVE_OPTION ){
	    	 
	    	 /*******************写入*****************/
	    	 
	    	 if(!((jfc.getSelectedFile().getAbsolutePath().endsWith(".xls"))||(jfc.getSelectedFile().getAbsolutePath().endsWith(".xlsx")))){
				JOptionPane.showMessageDialog(null, "请选择excel文件！", "错误", JOptionPane.ERROR_MESSAGE);
	    		 return;
	    	 }
	    	
	 		ExcelFile excelFile = null;
			try {
				excelFile = new ExcelFile(jfc.getSelectedFile().getAbsolutePath(), 'r');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		ExcelReader reader = null;
			try {
				reader = excelFile.reader();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		
	 		DatabaseResult result = reader.read();
	 		System.out.println("sad"+result.toString());
	 		System.out.println(result);
	 		try {
				DatabaseHelper.insertRecord(panelname,result);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	
	     }
	     refresh();
	}
	
	public void out(){
		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.xls","xls");
		JFileChooser fc=new JFileChooser();
		fc.setFileFilter(filter);
		fc.setMultiSelectionEnabled(false);
		int result=fc.showSaveDialog(null);
		if (result==JFileChooser.APPROVE_OPTION) {
			File file=fc.getSelectedFile();
			if (!file.getPath().endsWith(".xls")) {
				file=new File(file.getPath()+".xls");
			}
			System.out.println("file path="+file.getPath());
			
			
			/***************************导出**************************/
			DatabaseResult results = null;
			try {
				results = data.search(panelname);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ExcelExporter excelExporter = null;
			try {
				excelExporter = new ExcelExporter(file.getPath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				excelExporter.export(results);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void allselect(){
		
	}
	
	public void dele(){
		
		int select[] = table.getSelectedColumns();
		
		for(int i = 0 ;i<select.length;i++ ){
		}
		
	}
	
	public void alldele(DatabaseHelper helper){
	    int n = JOptionPane.showConfirmDialog(null, "确认将数据库全部删除吗?删除后数据将无法恢复！！", "确认删除框", JOptionPane.YES_NO_OPTION);  
        if (n == JOptionPane.YES_OPTION) {  
            try {
				helper.deleteTable(this.panelname);
				JOptionPane.showMessageDialog(null, "重新启动之后生效！ ", "删除成功", JOptionPane.ERROR_MESSAGE);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (n == JOptionPane.NO_OPTION) {  
           
        }  
	}
	
	public void initial(){
		
		try {
			columnhead = data.getColumns(this.panelname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void search(DatabaseHelper helper){
		
		DatabaseResult newdata = new DatabaseResult();
		box.getSelectedItem();
		String attri = (String) box.getSelectedItem();
		String infor = textfiled.getText();
		if(infor.equals("")){
			JOptionPane.showMessageDialog(null, "请输入有效的文本 ", "错误", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		Pair mypair  = new Pair(attri,infor);
		
		List<Pair> check = new ArrayList<>();
		check.add(mypair);
		
		try {
			newdata =	data.search(panelname, check);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int num  = table.getRowCount();
		for(int i = 0;i<num;i++){
			tableModel.removeRow(0);	
		}
		
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = newdata.getData();
		
		//System.out.println(attribute.size());
	//	System.out.println(attribute.toString());
		for(int i=0;i<attribute.size();i++){
			Vector<Object> changes = new Vector();
			for(int j = 0;j<columnhead.size();j++){
				
				changes.add(attribute.get(i).get(columnhead.get(j).key));
			}
			tableModel.addRow(changes);
		}
		
		textfiled.setText("");
		
	}
	
	public void refresh(){
		
		
		int num  = table.getRowCount();
		for(int i = 0;i<num;i++){
			tableModel.removeRow(0);	
		}
		
		
		DatabaseResult dataresult = new DatabaseResult();
		try {
			dataresult = data.search(panelname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = dataresult.getData();
		
		Vector<Vector<Object>> newdata = new Vector();
		for(int i=0;i<attribute.size();i++){
			Vector<Object> temp = new Vector();
			for(int j = 0;j<columnhead.size();j++){
			
				temp.add(attribute.get(i).get(columnhead.get(j).key));
				
			}
			newdata.add(temp);
		}
		for(int i=0;i<attribute.size();i++){
			tableModel.addRow(newdata.get(i));
		}
	}

	public void clear(){
		
		
		int n = JOptionPane.showConfirmDialog(null, "确认将数据库全部删除吗?删除后数据将无法恢复！！", "确认删除框", JOptionPane.YES_NO_OPTION);  
        if (n == JOptionPane.YES_OPTION) {  
            try {
            	data.clearTable(panelname);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else if (n == JOptionPane.NO_OPTION) {  
           
        }  
		int num  = table.getRowCount();
		for(int i = 0;i<num;i++){
			tableModel.removeRow(0);	
		}
	}
	
	public void FitTableColumns(JTable myTable){
		  JTableHeader header = myTable.getTableHeader();
		     int rowCount = myTable.getRowCount();

		     Enumeration columns = myTable.getColumnModel().getColumns();
		     while(columns.hasMoreElements()){
		         TableColumn column = (TableColumn)columns.nextElement();
		         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
		         int width = (int)myTable.getTableHeader().getDefaultRenderer()
		                 .getTableCellRendererComponent(myTable, column.getIdentifier()
		                         , false, false, -1, col).getPreferredSize().getWidth();
		         for(int row = 0; row<rowCount; row++){
		             int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
		               myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
		             width = Math.max(width, preferedWidth);
		         }
		         header.setResizingColumn(column); // 此行很重要
		         column.setWidth(width+myTable.getIntercellSpacing().width);
		     }
	}
}
