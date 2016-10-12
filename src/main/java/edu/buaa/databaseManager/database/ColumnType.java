package edu.buaa.databaseManager.database;

public enum ColumnType {
	intNumber("int"),doubleNumber("double"),text("text");
	private String typeString = null;
	private ColumnType(String columnType) {
		typeString = columnType;
	}
	public String getTypeString() {
		return typeString;
	}
	
}
