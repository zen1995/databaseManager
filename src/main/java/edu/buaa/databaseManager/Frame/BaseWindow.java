package edu.buaa.databaseManager.Frame;


import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


import javax.swing.JFrame;

import javax.swing.JTabbedPane;



public class BaseWindow extends JFrame implements Runnable {
	
//	JFrame frame = new JFrame();
	JTabbedPane lable = new JTabbedPane();
	Message message = new Message();
	MyPane pane_1 = new MyPane("xuanxiangk");
	Add_Window add_window = new Add_Window(message);
	//JButton addpane = new JButton();
	
	public BaseWindow(){
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
	    setSize(800,700);  
	   
	    add(lable);
	    /*********************居中显示***********************/
	    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;  
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;  
        setLocation((screenWidth - 800)/2, (screenHeight-700)/2);  
	    	
	    
	    lable.add("+",add_window);
	    lable.add("选项卡1", pane_1);
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
				message.setvalid(false);
				MyPane newpane = new MyPane("xuanxiangk");
				this.lable.add(message.getlistName(),newpane);
			}
		}
	}
	
}
