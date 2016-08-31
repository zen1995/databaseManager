package edu.buaa.databaseManager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

public class DatabaseHelper {
	
	public static void createCMRTable(String tableName) throws SQLException {
		if (hasTable(tableName)) {
			SQLException exception = new SQLException("table:" + tableName + " already exist!");
			throw exception;
		}
		boolean r = true;
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		String s = "create TABLE " + tableName + " (";
		s += " id int,\n";
		s += " patientID int ,\n";
		s += " patientName varchar(255),\n";
		s += " age int ,";
		s += " sex varchar(255),\n";
		s += " weight int ,\n";
		s += " height int ,\n";
		s += " MBI int ,\n";
		s += " BSA TEXT ,\n";
		s += " contact TEXT \n";
		s += ")";
		statement.execute(s);
		statement.execute("alter table cmr1 add primary key(id)");
		statement.close();
		connection.close();
		return;// r;
	}

	public static boolean hasTable(String tableName) throws SQLException {
		// SELECT * FROM INFORMATION_SCHEMA.TABLES
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("show tables like '" + tableName + "' ");
		ResultSetMetaData rsmd = resultSet.getMetaData();
		// System.out.println(rsmd.getColumnCount());
		// System.out.println(rsmd.getColumnName(1));
		boolean r = false;
		if (resultSet.next()) {
			r = true;
		} else {
			r = false;
		}
		resultSet.close();
		statement.close();
		connection.close();
		return r;
	}

	public static DatabaseResult search(String tableName) throws SQLException {
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		ResultSet set = statement.executeQuery("select * from "+tableName);
		
		DatabaseResult result = new DatabaseResult(set);
		set.close();
		statement.close();
		connection.close();
		return result;
	}
	
	public static DatabaseResult search(String tableName ,List<Pair> pairs)throws SQLException{
		Connection connection = DBConnection.getConnection();
		String s = "select * from "+tableName+" where (";
		int size = pairs.size();
		for(int i=0;i < size;i++){
			s += " "+pairs.get(i).key +" = ?";
			if(i != size-1){
				s += " and ";
			}
		}
		s += " )";
		
		PreparedStatement statement = connection.prepareStatement(s);
		for(int i=0;i < size;i++){
			statement.setObject(i+1,pairs.get(i).value);
		}
		ResultSet set = statement.executeQuery();
		
		DatabaseResult result = new DatabaseResult(set);
		//System.out.println(result);
		set.close();
		statement.close();
		connection.close();
		return null;
	}
	
//	public static insertColumn(String tabeName,String name,){
//		
//	}
	
	public static void main(String args[]) throws Exception {
		// hasTable("persons");
		// createCMRTable("cmr2")
		List<Pair> list = new ArrayList<Pair>();
		list.add(new Pair("id_P", 1));
		list.add(new Pair("City", "北京"));
		search("persons",list);
	}
}
