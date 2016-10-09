package edu.buaa.databaseManager.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import edu.buaa.databaseManager.database.DatabaseResult;
import edu.buaa.databaseManager.database.Pair;

public class ExcelReader {
	private ExcelFile excelFile;
	private ExcelSheet sheet;
	private List<Map<String, Object>> data = null;
	private List<Pair> columns = null;
	public ExcelReader(String path) throws FileNotFoundException, IOException {
		excelFile = new ExcelFile(path,'r');
		sheet = excelFile.getSheet(0);
	}
	
	public DatabaseResult read(){
		readTitleColumn();
		readData();
		DatabaseResult result = new DatabaseResult();
		result.setColumns(columns);
		result.setData(data);
		
		return result;
	}
	
	private void readData(){
		data = new ArrayList<Map<String,Object>>();
		int lastIndex = sheet.getLastRowIndex();
		int columnCount = columns.size();
		for(int i = Config.columnTitleRow+1;i <= lastIndex;i++){
			Map<String,Object> rowData = new HashMap<String, Object>();
			for(int currentColumn = 0;currentColumn < columnCount;currentColumn++){
				String result = sheet.readCell(i, currentColumn);
				Object d = null;
				if(StringUtils.isNumeric(result)){
					d = Double.valueOf(result);
				}
				else if (result.equals("null")) {
					d = null;
				}
				else {
					d = result;
				}
				rowData.put(columns.get(currentColumn).key,d);
			}
			data.add(rowData);
		}
	}
	
	private void readTitleColumn(){
		columns = new ArrayList<Pair>();
		int columnCount = 0;
		for(;;columnCount++){
			String content = sheet.readCell(Config.columnTitleRow, columnCount);
			if(content == null || content.equals("")){
				break;
			}
			columns.add(new Pair(content,null));
		}
	}
	
	
	public static void main(String[] args) {
		int a = 1;
		
		System.out.println((Object)a instanceof Integer);
	}

}
