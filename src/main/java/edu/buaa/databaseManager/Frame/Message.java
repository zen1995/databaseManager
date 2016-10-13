package edu.buaa.databaseManager.Frame;

import java.util.ArrayList;



public class Message {
	private String listName;
	private ArrayList<String> itemName;
	private boolean valid =false;
	private int num = 0;
	
	public  void setlistName(String name){
		this.listName = name;
	}
	public void additemName(String item){
		this.itemName.add(item);
	}
	
	public String getlistName(){
		return this.listName;
	}
	
	public int getitemlen(){
		return this.itemName.size();
	}
	
	public String getitemName(int i){
		return this.itemName.get(i);
	}
	
	public boolean getvalid(){
		return this.valid;
	}
	public void setvalid(boolean i){
		 this.valid = i;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
