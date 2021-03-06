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
	
//	JFrame frame = new JFrame();
	JTabbedPane lable = new JTabbedPane();
	public Message message = new Message();
	public DeleMessage delemessage = new DeleMessage();
	Add_Window add_window = new Add_Window(message);
	private DatabaseHelper myhelper = null ;
	List<String> table = new ArrayList<>();
	//JButton addpane = new JButton();
	
	public BaseWindow(DatabaseHelper helper){
		
		myhelper = helper;
		
		initial();
		
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
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
	    setSize(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height-100);  
	    
	    add(lable);
	    /*********************居中显示***********************/
	  /*  int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;  
        setLocation((screenWidth - 800)/2, (screenHeight-700)/2);  
	    	
	    */
	    lable.add("+",add_window);
	   
	   // MyPane pane = new MyPane("hihi", helper);
	  //  lable.add("hihi",pane);
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
				creatTable();
		
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

	public void initial(){
		try {
			table = myhelper.getTableNames();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i =0;i<table.size();i++){
			MyPane pane = new MyPane(table.get(i),myhelper,delemessage);
			lable.add(table.get(i), pane);
		}
	}
	
	public void creatTable(){
		
		ArrayList<ColumnAttribute> columns = new ArrayList<>();
		
		message.setvalid(false);
		String pname = message.getlistName();
		
		try {
			myhelper.createTable(message.getlistName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i =0 ;i<message.getNum();i++){
			
			ColumnAttribute demo  = new ColumnAttribute();
			String name = JOptionPane.showInputDialog("请输入第"+(i+1)+"列的内容");
			
		//	System.out.println(name);
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "请输入有效的文本 ", "错误", JOptionPane.ERROR_MESSAGE);
				i--;
				continue;
			}
			
			demo.cname = name;
			
			String[] possibleValues = { "文字", "短数字", "长数字" }; // 用户的选择项目       
			
			String type =(String) JOptionPane.showInputDialog(null, "请选择这一列的数据类型","选择属性",JOptionPane.INFORMATION_MESSAGE, null, possibleValues,possibleValues[0]);
//			System.out.println(type);
			
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
				try {
					myhelper.insertColumn(pname, columns.get(i).cname,columns.get(i).cattribute, "id");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				try {
					myhelper.insertColumn(pname, columns.get(i).cname,columns.get(i).cattribute,columns.get((i-1)).cname);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		MyPane newpane = new MyPane(message.getlistName(),myhelper,delemessage);
		this.lable.add(message.getlistName(),newpane);
		System.out.println(message.getlistName());
	}

	private boolean checkdata() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
