package edu.buaa.databaseManager.database;

import com.sun.rowset.CachedRowSetImpl;
import javax.sql.RowSet;
import java.sql.*;
;


/**
 * database操作的相关函数
 */
public class DBConnection {
    static private final String driver  = "com.mysql.jdbc.Driver";
    static private final String preUrl = "jdbc:mysql://localhost:3306/";
    static private final String postURL = "?characterEncoding=utf8";
    static private final String user = "root";
    static private final String password = "123456";
    static private final String databaseName = "hospital";
    /*初始化*/ 
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(preUrl+postURL,user,password);
            Statement stmt = connection.createStatement();
			String sql = "create database if not exists "+databaseName+" CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';";
			stmt.execute(sql);
			stmt.close();
			connection.close();
        }
        catch (Exception e){
        	e.printStackTrace();
            System.out.println("error when load jdbc driver: "+e.getMessage());
        }
    }
    
    private static String getUrl(){
    	return preUrl+databaseName+postURL;
    }

    /**
     * 获取数据库连接
     * @return connection
     */
    public static Connection getConnection() throws SQLException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(getUrl(),user,password);

//			connection = DriverManager.getConnection(url,user,password);
//			stmt = connection.createStatement();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw e;
        }
        return connection;
    }

    /**
     * 获取 statement
     * @return statement
     *//*
    public static Statement getStament(){
        Connection connection = getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        }
        catch (Exception e){
            String errMes = "can not create stament"+e.getMessage();

            System.out.println(errMes);
        }
        return  statement;
    }
*/
    /**
     * 执行查询语句
     * @param sql
     * @return ResultSet 查询结果
     */
    public static RowSet executeQuery(String sql){
        Statement statement;
        ResultSet resultSet = null;
        CachedRowSetImpl rowset = null;
        try {
            Connection connection = DriverManager.getConnection(getUrl(),user,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            rowset = new CachedRowSetImpl();
            rowset.populate(resultSet);
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception e){
            String errMes = "can not execute sql "+sql+" : "+e.getMessage();
            System.out.println(errMes);
        }
        return rowset;
    }

    /**
     * 执行插入语句
     * @param sql
     * @return Boolen 若插入成功 返回true
     */
    public static boolean executeInsert(String sql){
        Statement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(getUrl(),user,password);
            statement = connection.createStatement();
        }
        catch (Exception e){
            String errMes = "can not create stament"+e.getMessage();

            System.out.println(errMes);
        }
        boolean result = false;
        try {
            statement.execute(sql);
            result = true;
            //resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e){
            String errMes = "can not execute sql: "+sql+" : "+e.getMessage();
            System.out.println(errMes);

        }
        return result;
    }
 
    /**
     * 执行数据更新语句
     * @param sql
     * @return Boolen 若更新成功 返回true
     */
    public static boolean execute(String sql){
        boolean result = false;
        Statement statement = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(getUrl(),user,password);
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            result = true;
            statement.close();
            connection.close();
        }
        catch (SQLException e){
            String errMes = "can not execute sql: "+sql+" : "+e.getMessage();
            System.out.println(errMes);

        }
        return result;
    }

    /**
     * 是否可以查询到数据
     * @param sql
     * @return 若查询成功，返回true
     */
    public static boolean querySuccess(String sql){
        RowSet resultSet = executeQuery(sql);
        try {
            if(resultSet.next()){
                return true;
            }
            return false;
        }
        catch (SQLException e){
            String errStr = "query failed!+sql: "+sql;
            return false;
        }
    }

    /**
     * 是否可以查询到数据
     * @param resultSet
     * @return 若查询成功，返回true
     */
    public static boolean querySuccess(RowSet resultSet){
        try {
            if(resultSet.next()){
                return true;
            }
            return false;
        }
        catch (SQLException e){
            String errStr = "query failed!+sql ";
            return false;
        }
    }
}
