package edu.buaa.databaseManager.Frame;


import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import edu.buaa.databaseManager.database.ColumnType;
import edu.buaa.databaseManager.database.DatabaseHelper;



public class BaseWindow extends JFrame implements Runnable {
	

	JTabbedPane lable = new JTabbedPane();
	public Message message = new Message();
	public DeleMessage delemessage = new DeleMessage();
	Add_Window add_window = new Add_Window(message);
	private DatabaseHelper myhelper = null ;
	List<String> table = new ArrayList<>();

	
	public BaseWindow(DatabaseHelper helper) throws SQLException{
		
		myhelper = helper;
		
		initial();
		
		
		getContentPane().setLayout(new java.awt.BorderLayout());  
	
		addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {   
                System.exit(0);  
            }  
            public void windowActivated(WindowEvent e) {   
                setTitle("欢迎使用数据库");  
            }  
        }); 
		setResizable(true);  
	    setSize(800,700);  
	    
	    add(lable);
	    /*********************居中显示***********************/
	    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;  
        setLocation((screenWidth - 800)/2, (screenHeight-700)/2);  
	    	
	    
	    lable.add("+",add_window);
	   

	    lable.setSelectedIndex(0);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
			try {
				java.lang.Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(message.getvalid()){
				try {
					creatTable();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "创建表格失败！！");
				}
		
			}
			if(delemessage.isIsdele()){
				deleTable();
			}
		}
	}
	
	private void deleTable() {
		// TODO Auto-generated method stub
		
		this.lable.remove(delemessage.getPanelname());
	}

	public void initial() throws SQLException{
		
			table = myhelper.getTableNames();
		
		for(int i =0;i<table.size();i++){
			MyPane pane = new MyPane(table.get(i),myhelper,delemessage);
			lable.add(table.get(i), pane);
		}
	}
	
	public void creatTable() throws SQLException{
		
		ArrayList<ColumnAttribute> columns = new ArrayList<>();
		
		message.setvalid(false);
		String pname = message.getlistName();
		
	
		myhelper.createTable(message.getlistName());
		
		
		for(int i =0 ;i<message.getNum();i++){
			
			ColumnAttribute demo  = new ColumnAttribute();
			String name = JOptionPane.showInputDialog("请输入第"+(i+1)+"列的内容");
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "请输入有效的文本 ", "错误", JOptionPane.ERROR_MESSAGE);
				i--;
				continue;
			}
			
			demo.cname = name;
			
			String[] possibleValues = { "文字","数字" }; // 用户的选择项目       
			
			String type =(String) JOptionPane.showInputDialog(null, "请选择这一列的数据类型","选择属性",JOptionPane.INFORMATION_MESSAGE, null, possibleValues,possibleValues[0]);
			
			demo.getca(type);
			
			columns.add(demo);
		}
		
		/***************插入创建表头**************************/
		
		if(checkdata()){
			return;
		}
		
		for(int i = 0;i<columns.size();i++){
			System.out.println(columns.get(i).cname);
			if(i==0){
				
					myhelper.insertColumn(pname, columns.get(i).cname,columns.get(i).cattribute, "id");
				
			}
			else{
				
					myhelper.insertColumn(pname, columns.get(i).cname,columns.get(i).cattribute,columns.get((i-1)).cname);
				
			}
		}
		
		
		MyPane newpane = new MyPane(message.getlistName(),myhelper,delemessage);
		this.lable.add(message.getlistName(),newpane);
		
	}

	private boolean checkdata() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
