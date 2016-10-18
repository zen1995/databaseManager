package edu.buaa.databaseManager.util;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public static Map<String,Object> removeNullKey(Map<String,Object> map){
		List<String> removeKeys = new ArrayList<>();
		Iterator<Entry<String,Object>> iterator = map.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String,Object> entry = iterator.next();
			if(entry.getValue() == null){
				String key = entry.getKey();
				removeKeys.add(key);
			}
		}
		for(String key : removeKeys){
			map.remove(key);
		}
		return map;
	}
}
