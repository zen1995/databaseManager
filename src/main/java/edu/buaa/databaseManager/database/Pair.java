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
	
	@Override
	public boolean equals(Object object){
		if(!(object instanceof Pair)){
			return false;
		}
		Pair pair = (Pair) object;
		if(key.equals(pair.key)){
			return true;
		}
		return false;
	}
}
