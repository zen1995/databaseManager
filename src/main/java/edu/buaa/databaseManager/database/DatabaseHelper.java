package edu.buaa.databaseManager.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.buaa.databaseManager.util.Util;

public class DatabaseHelper {
	private static Logger logger = LoggerFactory.getLogger(DatabaseHelper.class);

	public static void createCMRTable(String tableName) throws SQLException {
		if (hasTable(tableName)) {
			SQLException exception = new SQLException("table:" + tableName + " already exist!");
			throw exception;
		}
		boolean r = true;
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		String s = "create TABLE " + tableName + " (";
		s += " `id` INT NOT NULL AUTO_INCREMENT,\n";
		s += " patientID int ,\n";
		s += " patientName varchar(255),\n";
		s += " age int ,";
		s += " sex varchar(255),\n";
		s += " weight int ,\n";
		s += " height int ,\n";
		s += " MBI int ,\n";
		s += " BSA TEXT ,\n";
		s += " contact TEXT ,\n";
		s += " PRIMARY KEY (`id`)";
		s += ") ENGINE = InnoDB;";
		statement.execute(s);
		// statement.execute("alter table "+tableName+" add primary key(id)");
		statement.close();
		connection.close();
		return;// r;
	}

	public static void createTable(String tableName) throws SQLException {
		if (hasTable(tableName)) {
			SQLException exception = new SQLException("table:" + tableName + " already exist!");
			throw exception;
		}
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		String s = "create TABLE " + tableName + " (";
		s += " `id` INT NOT NULL AUTO_INCREMENT,\n";
		s += " PRIMARY KEY (`id`)";
		s += ") ENGINE = InnoDB;";
		statement.execute(s);
		statement.close();
		connection.close();
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

	public static void deleteTable(String tableName) throws SQLException {
		if (!hasTable(tableName)) {
			SQLException exception = new SQLException("table not found!" + tableName);
			throw exception;
		}
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		statement.executeUpdate("DROP TABLE `" + tableName + "`");
		statement.close();
		connection.close();
	}

	public static void clearTable(String tableName) throws SQLException {
		assertHasTable(tableName);
		DBConnection.execute("TRUNCATE table " + tableName);
	}

	public static List<String> getTableNames() throws SQLException {
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		// ResultSet result = statement.executeQuery("SELECT TABLE_NAME as
		// tableName FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA =
		// '"+DBConnection.databaseName+"'");
		ResultSet result = statement
				.executeQuery("SELECT TABLE_NAME as tableName  FROM INFORMATION_SCHEMA.TABLES   WHERE TABLE_SCHEMA = '"
						+ DBConnection.databaseName + "' ");

		DatabaseResult databaseResult = new DatabaseResult(result);
		result.close();
		statement.close();
		connection.close();
		List<Map<String, Object>> list = databaseResult.getData();
		List<String> returnList = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			returnList.add((String) map.get("tableName"));
			// System.out.println(map);
		}
		return returnList;
	}

	public static List<Pair> getColumns(String tableName) throws SQLException {
		if (!hasTable(tableName)) {
			SQLException exception = new SQLException("table not found!" + tableName);
			throw exception;
		}

		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();

		ResultSet result = statement.executeQuery("select * from " + tableName + " limit 0");
		List<Pair> columns = DatabaseResult.columns(result);
		result.close();
		statement.close();
		connection.close();
		return columns;
	}

	private static void assertHasTable(String tableName) throws SQLException {
		if (!hasTable(tableName)) {
			SQLException exception = new SQLException("table not found!" + tableName);
			throw exception;
		}
	}

	private static void assertHasColumn(String tableName, String columnName) throws SQLException {
		if (!hasColumn(tableName, columnName)) {
			SQLException exception = new SQLException("column not found!" + tableName+"-"+tableName);
			throw exception;
		}
	}

	public static boolean hasColumn(String tableName, String columnName) throws SQLException {
		assertHasTable(tableName);
		List<Pair> list = getColumns(tableName);
		for (Pair pair : list) {
			if (pair.key.equals(columnName))
				return true;
		}
		return false;
	}

	public static void insertColumn(String tableName, String columnName, ColumnType type, String afterColumn)
			throws SQLException {
		assertHasTable(tableName);
		assertHasColumn(tableName, afterColumn);

		if(type.equals(ColumnType.intNumber)){
			DBConnection.execute("ALTER TABLE `" + tableName + "` ADD `" + columnName + "` " + type.getTypeString()
			+ " DEFAULT 0 " + " AFTER `" + afterColumn + "`; ");
		}
		else DBConnection.execute("ALTER TABLE `" + tableName + "` ADD `" + columnName + "` " + type.getTypeString()
				+ " NULL DEFAULT NULL " + " AFTER `" + afterColumn + "`; ");
		
		return;

	}

	public static void deleteColumn(String tableName, String columnName) throws SQLException {
		if (columnName.equals("id")) {
			SQLException exception = new SQLException("please do NOT try po remove column 'id'!");
			throw exception;
		}

		assertHasColumn(tableName, columnName);
		DBConnection.execute("ALTER TABLE " + tableName + " DROP `" + columnName + "`;");
	}

	public static void insertRecord(String tableName, Map<String, Object> values) throws SQLException {
		values = Util.removeNullKey(values);
		Connection connection = DBConnection.getConnection();
		Iterator<Entry<String, Object>> iterator = values.entrySet().iterator();
		String s = "insert into " + tableName + "  (";

		int size = values.size();
		int i = 0;
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			s += " `" + entry.getKey() + "` ";
			s += " ,";
			i++;
		}
		s = s.substring(0, s.length() - 1);
		s += " )";

		s += " values (";
		for (i = 0; i < size; i++) {
			s += " ? ";
			s += " ,";

		}
		s = s.substring(0, s.length() - 1);

		s += ")";
		// System.out.println(s);
		PreparedStatement statement = connection.prepareStatement(s);
		iterator = values.entrySet().iterator();
		i = 0;
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			statement.setObject(i + 1, entry.getValue());
			i++;
		}
		statement.executeUpdate();

		statement.close();
		connection.close();
	}

	public static void insertRecord(String tableName, DatabaseResult result) throws SQLException {
		Connection connection = DBConnection.getConnection();
		List<Map<String, Object>> data = result.getData();
		List<Pair> tableColumns = getColumns(tableName);
		List<Pair> dataColumns = (List<Pair>) ((ArrayList<Pair>) result.getColumns()).clone();
		dataColumns.removeAll(tableColumns);
		List<Pair> importColumns = result.getColumns();
		importColumns.removeAll(dataColumns);
		int size = importColumns.size();
		// System.out.println(importColumns);
		SQLException exception = null;
		for (Map<String, Object> values : data) {

			Iterator<Entry<String, Object>> iterator = values.entrySet().iterator();
			String s = "insert into " + tableName + "  (";
			int insertColmnCount = 0;
			for (int i = 0; i < size; i++) {
				if (values.get(importColumns.get(i).key) != null) {
					s += " `" + importColumns.get(i).key + "` ";
					s += " ,";
					insertColmnCount++;
				}

			}
			s = s.substring(0, s.length() - 1);
			s += " )";
			s += " values (";
			for (int i = 0; i < insertColmnCount; i++) {
				s += " ? ";
				s += " ,";

			}
			s = s.substring(0, s.length() - 1);
			s += ")";
			PreparedStatement statement = connection.prepareStatement(s);
			iterator = values.entrySet().iterator();
			for (int i = 0, j = 0; i < size; i++) {
				String key = importColumns.get(i).key;
				if (values.get(key) != null) {
					statement.setObject(j + 1, values.get(key));
					j++;
				}
			}
			try {
				statement.executeUpdate();
			} catch (SQLException e) {
				logger.warn("unable to insert record!", e);
				if (exception == null)
					exception = e;
			}

			statement.close();
		}
		connection.close();

		if (exception != null)
			throw exception;
	}

	public static void deleteRecord(String tableName, List<Integer> idList) throws SQLException {
		Connection connection = DBConnection.getConnection();
		for (int id : idList) {
			PreparedStatement statement = connection.prepareStatement("delete from " + tableName + " where `id` = ?");
			// statement.setString(1, tableName);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();

		}
		connection.close();
	}

	public static void editRecord(String tableNmae, int id, Map<String, Object> values) throws SQLException {
		Connection connection = DBConnection.getConnection();
		String sql = "update `" + tableNmae + "` set ";
		Iterator<Entry<String, Object>> iterator = values.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Object> entry = iterator.next();
			sql += " `" + entry.getKey() + "`= ? ";
			sql += ",";
		}
		sql = sql.substring(0, sql.length() - 1);
		sql += " where id = ? ";
		PreparedStatement statement = connection.prepareStatement(sql);
		iterator = values.entrySet().iterator();
		int i = 1;
		while (iterator.hasNext()) {
			statement.setObject(i, iterator.next().getValue());
			i++;
		}
		statement.setInt(i, id);
		statement.executeUpdate();
		connection.close();

	}

	public static DatabaseResult search(String tableName) throws SQLException {
		Connection connection = DBConnection.getConnection();
		Statement statement = connection.createStatement();
		ResultSet set = statement.executeQuery("select * from " + tableName);

		DatabaseResult result = new DatabaseResult(set);
		set.close();
		statement.close();
		connection.close();
		return result;
	}

	public static DatabaseResult search(String tableName, List<Pair> pairs) throws SQLException {
		Connection connection = DBConnection.getConnection();
		String s = "select * from " + tableName + " where (";
		int size = pairs.size();
		for (int i = 0; i < size; i++) {
			s += " `" + pairs.get(i).key + "` = ?";
			if (i != size - 1) {
				s += " and ";
			}
		}
		s += " )";

		PreparedStatement statement = connection.prepareStatement(s);
		for (int i = 0; i < size; i++) {
			statement.setObject(i + 1, pairs.get(i).value);
		}
		ResultSet set = statement.executeQuery();

		DatabaseResult result = new DatabaseResult(set);
		// System.out.println(result);
		set.close();
		statement.close();
		connection.close();
		return result;
	}

	// public static insertColumn(String tabeName,String name,){
	//
	// }

	public static void main(String args[]) throws Exception {
		// hasTable("persons");
		// createCMRTable("cmr2")
		// List<Pair> list = new ArrayList<Pair>();
		// list.add(new Pair("id_P", 1));
		// list.add(new Pair("City", "北京"));
		// search("persons", list);

		List<String> list = DatabaseHelper.getTableNames();
		System.out.println(list);
	}
}
