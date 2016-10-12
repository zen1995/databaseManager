package edu.buaa.databaseManager.Frame;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JTable;

public class MyPane extends JPanel{
	
	public MyPane(String labname){
		setLayout(new java.awt.BorderLayout());
		
		/************文本输入框**************/
		TextField textfiled = new TextField();
		textfiled.setColumns(30);
		
		/***********查询按钮*************/
		JButton check = new JButton();
		check.setText("查询");
		
		JPanel center = new JPanel();
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		

		
		/*************下拉框********************/
		JComboBox box = new JComboBox();
		
		/****************表格****************/
		JTable table = new JTable();
		
		/**************按钮******************/
		JButton allselect = new JButton();
		JButton add  = new JButton();
		JButton dele  = new JButton ();
		JButton out = new JButton();
		
		allselect.setText("全选");
		add.setText("插入");
		dele.setText("删除");
		out.setText("导出");
		
		/***************布局*******************/
		this.add("Center",center);
		this.add("North",north);
		this.add("South",south);
		
		north.add(textfiled);
		north.add(box);
		north.add(check);
		
		center.add(table);	
		
		south.add(allselect);
		south.add(add);
		south.add(out);
		south.add(dele);

	}
	
	
	
	
	
}
