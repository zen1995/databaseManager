package edu.buaa.databaseManager.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	private static Logger logger = LoggerFactory.getLogger(Util.class);
	public static void run(String filePath,String args)throws IOException{
		File file = new File(filePath);
		if(file.exists()){
			FileNotFoundException exception = new FileNotFoundException("file "+filePath +" not found!");
			logger.warn("file not found!",exception);
			throw exception;
		}
		Runtime runtime = Runtime.getRuntime();
		runtime.exec(filePath+" "+args);
	}
}
