package edu.buaa.databaseManager.database;

import com.sun.rowset.CachedRowSetImpl;

import edu.buaa.databaseManager.util.NetUtil;

import javax.sql.RowSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;;
 
/**
 * database操作的相关函数
 */
public class DBConnection {
	static private final String driver = "com.mysql.jdbc.Driver";
	static private final String preUrl = "jdbc:mysql://localhost:3306/";
	static private final String postURL = "?useUnicode=true&characterEncoding=UTF-8&useOldAliasMetadataBehavior=true";
	static private final String user = "root";
	static private final String password = "123456";
	public static  final String databaseName = "hospital";
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	/* 初始化 */
	static {
		try {
			//java.lang.Thread.sleep(30000000);
			initDB();
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = null;
			do{
				connection = DriverManager.getConnection(preUrl + postURL, user, password);
				System.out.println("try connect");
			}while(connection == null);
			Statement stmt = connection.createStatement();
			String sql = "create database if not exists " + databaseName
					+ " CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';";
			stmt.execute(sql);
			stmt.close();
			connection.close();
		} catch (Exception e) {
			logger.error("unable to connection database!",e);
		}
	}

	private static String getUrl() {
		return preUrl + databaseName + postURL;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return connection
	 */
	public static Connection getConnection() throws SQLException {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(getUrl(), user, password);

			// connection = DriverManager.getConnection(url,user,password);
			// stmt = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("unable to connecte DB",e);
			throw e;
		}
		return connection;
	}

//	public static Connection getDBConnection() throws SQLException {
//		Connection connection = null;
//		try {
//			connection = DriverManager.getConnection(preUrl, user, password);
//
//			// connection = DriverManager.getConnection(url,user,password);
//			// stmt = connection.createStatement();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		}
//		return connection;
//	}
	
	private static void initDB()throws IOException{
		Runtime run = Runtime.getRuntime();
		if(!NetUtil.isLoclePortUsing(3306)){
			System.out.println("start mysql");
			Process p = run.exec(System.getProperty("user.dir")+"/mysql/bin/mysqld.exe");
			while(!NetUtil.isLoclePortUsing(3306)){}
		}
	}
	
	public static void execute(String sql) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		connection = getConnection();
		statement = connection.createStatement();
		statement.executeUpdate(sql);
		statement.close();
		connection.close();
	}

}
