package edu.buaa.databaseManager.database;

public class Pair {
	public String key = null;
	public Object value = null;
	
	public Pair(String key,Object value) {
		this.key = key;
		this.value = value;
	}
	@Override
	public String toString(){
		return "("+key+","+value+")";
	}
}
