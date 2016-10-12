package edu.buaa.databaseManager.Frame;


import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Add_Window extends JPanel{
	private TextField name_in = new TextField();
	private TextField item_in = new TextField();
	private Message message;
	
	private int columnnum = 0;
	
	public Add_Window(Message messages){
		this.message = messages;
		/**************说明***********/
		JLabel readme =new JLabel();
		readme.setText("说明");
		
		
		/**********文本*****************/
		JLabel name  = new JLabel();
		name.setText("表格名称");
		JLabel item = new JLabel();
		item.setText("表格列数");
		
		/************文本输入�?************/
		
		name_in.setColumns(30);
		name_in.setText("");
		item_in.setColumns(30);
		item_in.setText("");

		/***************按钮*************/
		JButton ok = new JButton();
		ok.setText("确定");
		JButton cancel = new JButton();
		cancel.setText("取消");
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String str1 = name_in.getText();
				String str2 = item_in.getText();
				name_in.setText("");
				item_in.setText("");
			}});
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				confirm();
			}});
		
		/***********布局************/
		setLayout(new java.awt.BorderLayout());
		JPanel first = new JPanel();
		JPanel second = new JPanel();
		JPanel third = new JPanel();
		
		first.add(readme);
		second.add(name);
		second.add(name_in);
		second.add(item);
		second.add(item_in);
		third.add(ok);
		third.add(cancel);
		
		this.add("North",first);
		this.add("Center",second);
		this.add("South",third);
	}
	
	
	public void confirm(){
		String name = name_in.getText();
		String item = item_in.getText();
		
		
		
		//System.out.println(name.equals(""));
		//System.out.println(item.equals(""));
		if((!name.equals(""))&&(!item.equals(""))){
			name_in.setText("");
			item_in.setText("");
			
			columnnum = Integer.parseInt(item);
			
		//	System.out.println("adsssssssssssss"+columnnum);
			
			if((columnnum>0)){
				
				message.setvalid(true);
				message.setlistName(name);
				message.setNum(columnnum);
			}
			else{
				JOptionPane.showMessageDialog(null, "无效的列数！！");
			}
			
			
			
			}
		else{
			JOptionPane.showMessageDialog(null, "请输入完整信息");
		}
	}
	
}
