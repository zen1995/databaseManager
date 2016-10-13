package edu.buaa.databaseManager.Frame;

import edu.buaa.databaseManager.database.ColumnType;

public class ColumnAttribute {
	
	public String cname = null;
	public ColumnType cattribute = null;
	
	public void getca(String str){
		if(str.equals("文字")){
			cattribute = ColumnType.text;
		}
		else if (str.equals("短数字")){
			cattribute = ColumnType.intNumber;
		}
		else {
			cattribute = ColumnType.doubleNumber;
		}
	}
}
