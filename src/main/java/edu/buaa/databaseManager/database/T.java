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
		
		for(int i=0;i < Math.pow(10, 4);i++){
			logger.warn(String.valueOf(i));
		}
		
//		InputStream in = new FileInputStream("t1.properties");		
//		Properties p = new Properties();
//		p.setProperty("b","bbb");
//		p.load(in);
//		in.close();
//		String s = p.getProperty("a");
//		System.out.println(s);
//		p.setProperty("a", "sss");
//		
//		
//		FileOutputStream outputFile = new FileOutputStream("t1.properties");
//		p.store(outputFile,"sdsd");
//		outputFile.close();
		
		ExcelFile excelFile = new ExcelFile("E:\\study\\lab\\hospital\\databaseManager\\新建 Microsoft Excel 工作表.xlsx", 'r');
		ExcelReader reader = excelFile.reader();
		DatabaseResult result = reader.read();
		System.out.println(result);
	}

}
