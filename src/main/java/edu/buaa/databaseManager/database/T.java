package edu.buaa.databaseManager.database;

import java.sql.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class T {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Connection connection = DBConnection.getConnection();
		Logger logger = LoggerFactory.getLogger(T.class);
		logger.debug("a");
		logger.warn("b");
	}

}
