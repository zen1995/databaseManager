package edu.buaa.databaseManager.Frame;


import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	public MyPane(String name , DatabaseHelper helper,DeleMessage delete) throws SQLException{
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
				try {
					search(data);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		
		
	
		
		/****************表格****************/
		
		
		for(int i = 0;i<columnhead.size();i++){
			columnnames.add(columnhead.get(i).key) ;
			
		}
		
		
		DatabaseResult dataresult = new DatabaseResult();
		
		dataresult = data.search(panelname);
	
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
       // System.out.print("sadasd"+table.getIntercellSpacing().getWidth());
       
        JPanel tablepane = new JPanel();
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setReorderingAllowed(false);
        FitTableColumns(table);
       for(int i=0;i<columnhead.size();i++){
    	   table.getColumnModel().getColumn(i).setPreferredWidth(100);
       }
        
        /******************多选****************/
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		
		center.setLayout(new java.awt.BorderLayout());
		scrollPane.setPreferredSize(new Dimension(800, 500));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		center.add(scrollPane);
		
		
		scrollPane.setViewportView(table);
		
		/**************右键菜单******************/
		createPopupMenu();
		
        
		/**************按钮******************/
		JButton allselect = new JButton();
		JButton add  = new JButton();
		JButton dele  = new JButton ();
		JButton out = new JButton();
		JButton alldele = new JButton();
		JButton cleartable = new JButton();
		JButton insert = new JButton();
		JButton insertcolumn = new JButton();
		JButton delecolumn  = new JButton();
		
		insertcolumn.setText("插入列");
		delecolumn.setText("删除列");
		allselect.setText("全选");
		cleartable.setText("清空表格数据");
		add.setText("导入");
		dele.setText("删除所选数据");
		insert.setText("插入数据");
		out.setText("导出");
		alldele.setText("删除表格");
		
		insert.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					insert();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "插入失败！");
					e.printStackTrace();
					
				}
			}});
		delecolumn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					delecolumn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "删除失败！");
					e.printStackTrace();
					
				}
			}});
		insertcolumn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					insertcolumn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "插入失败！");
					e.printStackTrace();
					
				}
			}});
		
		cleartable.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					clear();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "清空失败！");
					return;
				}
			}});
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
					try {
						add();
					} catch (IOException | SQLException e) {
						// TODO Auto-generated catch block
						
						JOptionPane.showMessageDialog(null, "添加数据失败！");
						return;
					}
				
			}});
		
		out.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
					try {
						out();
					} catch (SQLException | IOException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "导出失败！");
						return;
					}
				
			}});
		
		allselect.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				allselect();
			}});
		
		dele.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					dele();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "删除失败！");
					return;
				}
			}});
		alldele.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					alldele(data);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "删除表格失败！");
					return;
				}
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

		south.add(insert);
		south.add(dele);
		south.add(insertcolumn);
		south.add(delecolumn);
		south.add(add);
		south.add(out);
	
		south.add(cleartable);
		south.add(alldele);
	}
	
	public void add() throws FileNotFoundException, IOException, SQLException{
		JFrame f  = new JFrame();
		JFileChooser jfc = new JFileChooser();
		
	     if(jfc.showOpenDialog(f)==JFileChooser.APPROVE_OPTION ){
	    	 
	    	 /*******************写入*****************/
	    	 
	    	 if(!((jfc.getSelectedFile().getAbsolutePath().endsWith(".xls"))||(jfc.getSelectedFile().getAbsolutePath().endsWith(".xlsx")))){
				JOptionPane.showMessageDialog(null, "请选择excel文件！", "错误", JOptionPane.ERROR_MESSAGE);
	    		 return;
	    	 }
	    	
	 		ExcelFile excelFile = null;
			
			excelFile = new ExcelFile(jfc.getSelectedFile().getAbsolutePath(), 'r');
			
	 		ExcelReader reader = null;
			
			reader = excelFile.reader();
			
	 		
	 		DatabaseResult result = reader.read();

	 		
			DatabaseHelper.insertRecord(panelname,result);
			
	 	
	     }
	     refresh();
	}
	
	public void out() throws SQLException, FileNotFoundException, IOException{
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
			
			
			
			/***************************导出**************************/
			DatabaseResult results = null;
			
			results = data.search(panelname);
			
			ExcelExporter excelExporter = null;
			
			excelExporter = new ExcelExporter(file.getPath());
			
			
			excelExporter.export(results);
			
		}
	}
	
	public void allselect(){
		
	}
	
	public void dele() throws SQLException{
		
		int select[] = table.getSelectedRows();
		//table.getValueAt(select[0], 0);
		List<Integer> id = new ArrayList<>();;
		
		for(int i = 0;i<select.length;i++){
			id.add((Integer) table.getValueAt(select[i], 0));
		}
		
		data.deleteRecord(panelname,id);
		
		refresh();
		
		
	}
	
	public void alldele(DatabaseHelper helper) throws SQLException{
	    int n = JOptionPane.showConfirmDialog(null, "确认将数据库全部删除吗?删除后数据将无法恢复！！", "确认删除框", JOptionPane.YES_NO_OPTION);  
    
	    if (n == JOptionPane.YES_OPTION) {  
           
				helper.deleteTable(this.panelname);
				delet.setIsdele(true);
				delet.setPanelname(this);
			
        } else if (n == JOptionPane.NO_OPTION) {  
           return;
        }  
	}
	
	public void initial() throws SQLException{
		
		
		columnhead = data.getColumns(this.panelname);
		
		
		
		
	}
	
	public void search(DatabaseHelper helper) throws SQLException{
		
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
		
		
		newdata =	data.search(panelname, check);
			
		
		int num  = table.getRowCount();
		for(int i = 0;i<num;i++){
			tableModel.removeRow(0);	
		}
		
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = newdata.getData();
		
		
		for(int i=0;i<attribute.size();i++){
			Vector<Object> changes = new Vector();
			for(int j = 0;j<columnhead.size();j++){
				
				changes.add(attribute.get(i).get(columnhead.get(j).key));
			}
			tableModel.addRow(changes);
		}
		
		textfiled.setText("");
		
	}
	
	public void refresh() throws SQLException{
		
		
		int num  = table.getRowCount();
		for(int i = 0;i<num;i++){
			tableModel.removeRow(0);	
		}
		
		
		DatabaseResult dataresult = new DatabaseResult();
		
		dataresult = data.search(panelname);
		
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

	public void clear() throws SQLException{
		
		
		int n = JOptionPane.showConfirmDialog(null, "确认将数据库全部删除吗?删除后数据将无法恢复！！", "确认删除框", JOptionPane.YES_NO_OPTION);  
        if (n == JOptionPane.YES_OPTION) {  
           
            	data.clearTable(panelname);

			
        } else if (n == JOptionPane.NO_OPTION) {  
           return;
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
		         int minwidth = 800/columnhead.size();
		         if(width<minwidth){
		        	 width = minwidth;
		         }
		         column.setPreferredWidth(width+myTable.getIntercellSpacing().width+1);
		       //  System.out.println("asdasdasd"+width+column.getHeaderValue());
		     }
	}
	
	public void insert() throws SQLException{
		
		Map<String,Object> newdata = new HashMap();
		for(int i =0;i<columnhead.size();i++){
			
			if(columnhead.get(i).key.equals("id")){
				continue;
			}
			Object name = JOptionPane.showInputDialog("请输入"+columnhead.get(i).key+":");
			if(name==null){
				return;
			}
			newdata.put(columnhead.get(i).key, name);
		}
		
		
		data.insertRecord(panelname, newdata);
		
		refresh();
	}
	
	
    private void createPopupMenu() {  
    	
          
        JMenuItem delMenItem = new JMenuItem();  
        delMenItem.setText("修改此行数据    ");  
        JMenuItem insertImage = new JMenuItem();  
        insertImage.setText("插入图片文件夹      ");  
        JMenuItem openimage = new JMenuItem();  
        openimage.setText("打开图片文件      ");  
        JMenuItem insertcolumn = new JMenuItem();  
        insertcolumn.setText("插入列      ");  
        JMenuItem delecolumn = new JMenuItem();  
        delecolumn.setText("删除列      ");  
        
        
        delMenItem.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	try {
					dataedit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "修改失败！");
					return;
					
				}
            }  
        });  
        delecolumn.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	try {
					delecolumn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "删除失败！");
					return;
					
				}
            }  
        });  
        insertcolumn.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	try {
					insertcolumn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "插入失败！");
					return;
					
				}
            }  
        });  
        
        openimage.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	
					try {
						openimage();
					} catch (SQLException | IOException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "打开图片失败！");
						return;
					}
				
            }  
        }); 
        
        insertImage.addActionListener(new java.awt.event.ActionListener() {  
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
                //该操作需要做的事 
            	try {
					insertimage();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "插入图片失败！");
					return;
				}
            }  
        });  
        
        mymenu.add(delMenItem);  
      //  mymenu.add(insertcolumn);
      //  mymenu.add(delecolumn);
        mymenu.add(insertImage);
        mymenu.add(openimage);
       
    }  
    
    protected void delecolumn() throws SQLException {
		// TODO Auto-generated method stub
    	
		Vector<Pair> pairs = new Vector();
		
	
    	Object[] head = new Object[columnhead.size()-1];
    	for(int i = 0;i<columnhead.size();i++){
    		if(columnhead.get(i).key.equals("id")) continue;
    		head[i-1]=columnhead.get(i).key;
    	}
    	String type =(String) JOptionPane.showInputDialog(null, "请选择删除哪一列","选择表头",JOptionPane.INFORMATION_MESSAGE, null, head,head[0]);
    	if(type==null){
			return;
		}
    	TableColumn tc = new TableColumn();
	
		for(int i = 0;i<columnhead.size();i++){
			if(columnhead.get(i).key.equals(type)){
				tc = table.getColumnModel().getColumn(i);
				box.removeItemAt(i-1);
			//	System.out.println(i+"ceshi "+ tc);
				break;
			}
		}
		//System.out.println("1"+columnhead.size());
    	data.deleteColumn(panelname, type);
		//JOptionPane.showMessageDialog(null, "重启后生效 ", "删除成功", JOptionPane.ERROR_MESSAGE);
		
    	
    	
		/*************************xiu'zheng********************************/
		
		
		//refresh();
		
	
		initial();
	//	System.out.println("2"+columnhead.size());
		table.getColumnModel().removeColumn(tc);
		table.repaint();
		
    
    }

	protected void insertcolumn() throws SQLException {
		// TODO Auto-generated method stub
    	ColumnAttribute demo  = new ColumnAttribute();
		String name = JOptionPane.showInputDialog("请输入要插入列的内容");
		if(name==null){
			return;
		}
		if(name.equals("")){
			JOptionPane.showMessageDialog(null, "请输入有效的文本 ", "错误", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		name = name.replaceAll("\\s*", "");
		demo.cname = name;
		
		String[] possibleValues = { "文字","数字" }; // 用户的选择项目       
		
		String type =(String) JOptionPane.showInputDialog(null, "请选择这一列的数据类型","选择属性",JOptionPane.INFORMATION_MESSAGE, null, possibleValues,possibleValues[0]);
		if(type==null){
			return;
		}
		demo.getca(type);
		
		data.insertColumn(panelname, demo.cname, demo.cattribute, columnhead.get(columnhead.size()-1).key);
		
		//JOptionPane.showMessageDialog(null, "重启后生效！", "完成", JOptionPane.ERROR_MESSAGE);

		
		/*************************xiu'zheng********************************/
		initial();
		refresh();
		box.addItem(name);
		tableModel.addColumn(name);
		table.repaint();
		
		
		
	}

	protected void openimage() throws SQLException, IOException {
		// TODO Auto-generated method stub
		
    	int k = table.getSelectedRow();
		Object id = table.getValueAt(k, 0);
		Vector<Pair> pairs = new Vector();
		Pair idpair = new Pair("id",id);
		pairs.add(idpair);
		DatabaseResult result = new DatabaseResult();
		
		result=data.search(panelname, pairs);
		
		
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = result.getData();
    	
    	Object[] head = new Object[columnhead.size()-1];
    	for(int i = 0;i<columnhead.size();i++){
    		if(columnhead.get(i).key.equals("id")) continue;
    		head[i-1]=columnhead.get(i).key;
    	}
    	String type =(String) JOptionPane.showInputDialog(null, "请选择查看哪一列插入图片","选择表头",JOptionPane.INFORMATION_MESSAGE, null, head,head[0]);
    	
    	if(type==null){
			return;
		}
    	Object path  = attribute.get(0).get(type);
    	
    	File file=new File((String)path);    


    	if ((!new File(Config.getInstance().get("programeDir")).exists())){
    		Add_Window get = new Add_Window(null);
    		get.set();
    	}
    	
    	else{
    		Util util = new Util();
        	
    		util.run(Config.getInstance().get("programeDir"), (String)path);
    		
    	}
    	
    	
	}

	protected void insertimage() throws SQLException {
		// TODO Auto-generated method stub
		
		int k = table.getSelectedRow();
		Object id = table.getValueAt(k, 0);
		Vector<Pair> pairs = new Vector();
		Pair idpair = new Pair("id",id);
		pairs.add(idpair);
		DatabaseResult result = new DatabaseResult();
	
		result=data.search(panelname, pairs);
		
		
		List<Map<String, Object>> attribute = new ArrayList();
		attribute = result.getData();
		
		
    	JFrame f  = new JFrame();
    	Object[] head = new Object[columnhead.size()-1];
    	for(int i = 0;i<columnhead.size();i++){
    		if(columnhead.get(i).key.equals("id")) continue;
    		head[i-1]=columnhead.get(i).key;
    	}
    	 
		
		String type =(String) JOptionPane.showInputDialog(null, "请选择在哪一列插入图片","选择表头",JOptionPane.INFORMATION_MESSAGE, null, head,head[0]);
    	
		if(type==null){
			return;
		}
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    if(jfc.showOpenDialog(f)==JFileChooser.APPROVE_OPTION ){
	    	
	    	 attribute.get(0).put(type,jfc.getSelectedFile().getAbsolutePath());
	    	// System.out.println(type+"asdad"+jfc.getSelectedFile().getAbsolutePath());
			data.editRecord(panelname, (int)id, attribute.get(0));
			
	    }
	    
	    refresh();
    	
	}

	protected void dataedit() throws SQLException {
		// TODO Auto-generated method stub
    	
    	int thisid = (int) table.getValueAt(table.getSelectedRow(), 0);
    	Map<String,Object> newdata = new HashMap();
    	
    	
    	Object[] head = new Object[columnhead.size()-1];
    	for(int i = 0;i<columnhead.size();i++){
    		if(columnhead.get(i).key.equals("id")) continue;
    		head[i-1]=columnhead.get(i).key;
    	}
    	String type =(String) JOptionPane.showInputDialog(null, "请选择修改哪一列的数据","选择表头",JOptionPane.INFORMATION_MESSAGE, null, head,head[0]);
    	
		if(type==null){
			return;
		}
		
		String name = JOptionPane.showInputDialog("请输入内容");
		if(name==null){
			return;
		}
    	
		DatabaseResult datas = new DatabaseResult();
		Pair pair = new Pair("id",thisid);
		Vector<Pair> pairs = new Vector<Pair>();
		pairs.add(pair);
		datas = data.search(panelname, pairs);
		List<Map<String, Object>> attribute = new ArrayList();
		
		attribute = datas.getData();
		
		
		newdata = attribute.get(0);
		newdata.put(type, name);
		
		data.editRecord(panelname, thisid, newdata);
		
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
