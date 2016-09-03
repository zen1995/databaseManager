package edu.buaa.databaseManager.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseResult {
	private List<Pair> columns = null;//new ArrayList<Pair>();
	private List<Map<String, Object>> data = new ArrayList();

	public DatabaseResult(ResultSet resultSet) throws SQLException {
		ResultSetMetaData m = resultSet.getMetaData();
		int colmunCount = m.getColumnCount();
		// System.out.println(m.getColumnType(1));
		//System.out.println(m.getColumnTypeName(2));
		// System.out.println(m.getColumnDisplaySize(1));
		// System.out.println(m.getCatalogName(1));
		// System.out.println(m.getColumnName(1));
		// System.out.println(m.getColumnClassName(1));
		
//		for (int i = 1; i <= colmunCount; i++) {
//			Class c = null;
//			try {
//				c = Class.forName(m.getColumnClassName(i));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			columns.add(new Pair(m.getColumnName(i), c));
//		}
		columns = columns(resultSet);
		while(resultSet.next()){
			Map<String, Object> row = new HashMap<String, Object>();
			for(int i=0;i < colmunCount;i++){
				row.put(columns.get(i).key, resultSet.getObject(columns.get(i).key));

			}
			data.add(row);
		}
	}

	public static List<Pair> columns(ResultSet resultSet)throws SQLException{
		ResultSetMetaData m = resultSet.getMetaData();
		List<Pair> columns = new ArrayList<Pair>();
		int colmunCount = m.getColumnCount();
		for (int i = 1; i <= colmunCount; i++) {
			Class c = null;
			try {
				c = Class.forName(m.getColumnClassName(i));
			} catch (Exception e) {
				e.printStackTrace();
			}

			columns.add(new Pair(m.getColumnName(i), c));
		}
		return columns;
	}
	
	
	
	public List<Pair> getColumns() {
		return columns;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}
	
	@Override
	public String toString(){
		String s  = "[dataResult: \n"+columns+"\n"+data+"]";
		return s;
	}
}
