package edu.buaa.databaseManager.database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.buaa.databaseManager.excel.ExcelFile;
import edu.buaa.databaseManager.excel.ExcelReader;

public class T {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
//		Connection connection = DBConnection.getConnection();
		Logger logger = LoggerFactory.getLogger(T.class);
		logger.debug("a");
		logger.warn("b");
		logger.warn("a",new Exception("sssssssssss"));;
		
//		for(int i=0;i < Math.pow(10, 4);i++){
//			logger.warn(String.valueOf(i));
//		}
		ExcelReader reader = new ExcelFile("1.xls",'r').reader();
		DatabaseResult result = reader.read();
		System.out.println(result);
		
	}

}
