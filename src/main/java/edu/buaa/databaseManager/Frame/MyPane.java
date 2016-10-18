package edu.buaa.databaseManager.Frame;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import java.awt.Graphics;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import edu.buaa.databaseManager.config.Config;
import edu.buaa.databaseManager.database.DatabaseHelper;
import edu.buaa.databaseManager.database.DatabaseResult;
import edu.buaa.databaseManager.database.Pair;
import edu.buaa.databaseManager.excel.ExcelExporter;
import edu.buaa.databaseManager.excel.ExcelFile;
import edu.buaa.databaseManager.excel.ExcelReader;
import edu.buaa.databaseManager.util.NetUtil;
import edu.buaa.databaseManager.util.Util;

public class MyPane extends JPanel{
	
	String panelname;
	DatabaseHelper data;
	public List<Pair> columnhead = new ArrayList<>();
	JComboBox box = new JComboBox();
	TextField textfiled = new TextField();
	public Vector<String> columnnames = new Vector<>();
	public Vector<Vector<Object>>columndata = new Vector<>();
	private DefaultTableModel tableModel;
	private JTable table;
	public DeleMessage delet;
	public  JPopupMenu mymenu = new JPopupMenu();  
	public MyPane(String name , DatabaseHelper helper,DeleMessage delete){
		this.panelname=name;
		this.data = helper;
		this.delet = delete;
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
			if(columnhead.get(i).key.equals("id")){
				continue;
			}
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
        
        JPanel tablepane = new JPanel();
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        FitTableColumns(table);
        
        /******************多选****************/
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		
	
		scrollPane.setPreferredSize(new Dimension(800, 500));
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
		JButton insert = new JButton();
		
		
		allselect.setText("全选");
		cleartable.setText("清空表格数据");
		add.setText("插入");
		dele.setText("删除所选数据");
		insert.setText("插入数据");
		out.setText("导出");
		alldele.setText("删除表格");
		
		insert.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				insert();
			}});
		
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
		
		/************************右键菜单*********************************/
		
		table.addMouseListener(new java.awt.event.MouseAdapter() {  
            public void mouseClicked(java.awt.event.MouseEvent evt) {  
                jTable1MouseClicked(evt);  
            }  
		}); 
		
		/***************布局*******************/
		this.add("Center",center);
		this.add("North",north);
		this.add("South",south);
		
		north.add(textfiled);
		north.add(box);
		north.add(check);
		
		//center.add(table);	
		
	//	south.add(allselect);
		south.add(insert);
		south.add(dele);
		south.add(add);
		south.add(out);
	//	
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
		
		int select[] = table.getSelectedRows();
		table.getValueAt(select[0], 0);
		List<Integer> id = new ArrayList<>();;
		for(int i = 0;i<select.length;i++){
			id.add((Integer) table.getValueAt(select[i], 0));
			//System.out.println(id[i]);
			
		}
		try {
			data.deleteRecord(panelname,id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refresh();
		
		
	}
	
	public void alldele(DatabaseHelper helper){
	    int n = JOptionPane.showConfirmDialog(null, "确认将数据库全部删除吗?删除后数据将无法恢复！！", "确认删除框", JOptionPane.YES_NO_OPTION);  
        if (n == JOptionPane.YES_OPTION) {  
            try {
				helper.deleteTable(this.panelname);
				delet.setIsdele(true);
				delet.setPanelname(this);
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
		createPopupMenu();
		
		
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
	
	public void insert(){
		
		Map<String,Object> newdata = new HashMap();
		for(int i =0;i<columnhead.size();i++){
			
			if(columnhead.get(i).key.equals("id")){
				continue;
			}
			Object name = JOptionPane.showInputDialog("请输入"+columnhead.get(i).key+":");
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "请输入有效的文字！！！");
				i--;
				continue;
			}
			newdata.put(columnhead.get(i).key, name);
		}
		
		try {
			data.insertRecord(panelname, newdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refresh();
	}
	
	
    private void createPopupMenu() {  
    	
          
        JMenuItem delMenItem = new JMenuItem();  
        delMenItem.setText("修改此行数据    ");  
        JMenuItem insertImage = new JMenuItem();  
        insertImage.setText("插入图片文件      ");  
        JMenuItem openimage = new JMenuItem();  
        openimage.setText("打开图片文件      ");  
        
        
        delMenItem.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	dataedit();
            }  
        });  
        
        openimage.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	openimage();
            }  
        }); 
        
        insertImage.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	insertimage();
            }  
        });  
        
        mymenu.add(delMenItem);  
        mymenu.add(insertImage);
        mymenu.add(openimage);
    }  
    
    protected void openimage() {
		// TODO Auto-generated method stub
		
    	int k = table.getSelectedRow();
		Object id = table.getValueAt(k, 0);
		Vector<Pair> pairs = new Vector();
		Pair idpair = new Pair("id",id);
		pairs.add(idpair);
		DatabaseResult result = new DatabaseResult();
		try {
			 result=data.search(panelname, pairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = result.getData();
    	
    	Object[] head = new Object[columnhead.size()-1];
    	for(int i = 0;i<columnhead.size();i++){
    		if(columnhead.get(i).key.equals("id")) continue;
    		head[i-1]=columnhead.get(i).key;
    	}
    	String type =(String) JOptionPane.showInputDialog(null, "请选择查看哪一列插入图片","选择表头",JOptionPane.INFORMATION_MESSAGE, null, head,head[0]);
    	
    	Object path  = attribute.get(0).get(type);
    	
    	File file=new File((String)path);    
		System.out.println("asdsda"+Config.getInstance().get("programDir"));

    	/*if (!new File(Config.getInstance().get("programDir")).exists()){
    		System.out.println("asdsda"+Config.getInstance().get("programDir"));
    		Add_Window get = new Add_Window(null);
    		get.set();
    	}
    	else{*/
    		Util util = new Util();
        	try {
    			util.run("C:\\Users\\Saber\\Desktop\\数据库\\SYNGO_FV\\SYNGO_FV.EXE", (String)path);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	//}
    	
    	
	}

	protected void insertimage() {
		// TODO Auto-generated method stub
		
		int k = table.getSelectedRow();
		Object id = table.getValueAt(k, 0);
		Vector<Pair> pairs = new Vector();
		Pair idpair = new Pair("id",id);
		pairs.add(idpair);
		DatabaseResult result = new DatabaseResult();
		try {
			 result=data.search(panelname, pairs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = result.getData();
		
		
    	JFrame f  = new JFrame();
    	Object[] head = new Object[columnhead.size()-1];
    	for(int i = 0;i<columnhead.size();i++){
    		if(columnhead.get(i).key.equals("id")) continue;
    		head[i-1]=columnhead.get(i).key;
    	}
    	 
		
		String type =(String) JOptionPane.showInputDialog(null, "请选择在哪一列插入图片","选择表头",JOptionPane.INFORMATION_MESSAGE, null, head,head[0]);
    	
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    if(jfc.showOpenDialog(f)==JFileChooser.APPROVE_OPTION ){
	    	 System.out.println(jfc.getSelectedFile().getAbsolutePath());
	    	 attribute.get(0).put(type,jfc.getSelectedFile().getAbsolutePath());
	    	 try {
				data.editRecord(panelname, (int)id, attribute.get(0));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    refresh();
    	
	}

	protected void dataedit() {
		// TODO Auto-generated method stub
    	
    	int thisid = (int) table.getValueAt(table.getSelectedRow(), 0);
    	Map<String,Object> newdata = new HashMap();
		for(int i =0;i<columnhead.size();i++){
			
			if(columnhead.get(i).key.equals("id")){
				continue;
			}
			Object name = JOptionPane.showInputDialog("请输入"+columnhead.get(i).key+":");
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "请输入有效的文字！！！");
				i--;
				continue;
			}
			newdata.put(columnhead.get(i).key, name);
		}
		
		try {
			data.editRecord(panelname, thisid, newdata);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refresh();
	}

	private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {  
    	  
        mouseRightButtonClick(evt);  
    } 
    
    private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {  
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键  
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {  
            //通过点击位置找到点击为表格中的行  
            int focusedRowIndex = table.rowAtPoint(evt.getPoint());  
            if (focusedRowIndex == -1) {  
                return;  
            }  
            //将表格所选项设为当前右键点击的行  
            table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);  
           
            //弹出菜单  
            mymenu.show(table, evt.getX(), evt.getY());  
        }  
   
    } 



}
