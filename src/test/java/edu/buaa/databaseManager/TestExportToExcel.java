package edu.buaa.databaseManager;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buaa.databaseManager.database.DatabaseHelper;
import edu.buaa.databaseManager.database.DatabaseResult;
import edu.buaa.databaseManager.excel.ExcelExporter;
import edu.buaa.databaseManager.excel.ExcelFile;
import edu.buaa.databaseManager.excel.ExcelReader;

public class TestExportToExcel {
	private String testTableName = "testTable";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		if (DatabaseHelper.hasTable(testTableName))
			DatabaseHelper.deleteTable(testTableName);
		DatabaseHelper.createCMRTable(testTableName);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("age", 1);
		map.put("sex", "male");
		DatabaseHelper.insertRecord(testTableName, map);
		map.put("age", 2);
		map.put("sex", null);
		map.put("id", 9);
		DatabaseHelper.insertRecord(testTableName, map);
	}

	@After
	public void tearDown() throws Exception {
		DatabaseHelper.deleteTable(testTableName);
	}

	@Test
	public void testWrite() throws Exception{
		DatabaseResult result = DatabaseHelper.search(testTableName);
		ExcelExporter excelExporter = new ExcelExporter("testWrite.xls");
		excelExporter.export(result);
	}
	
	@Test
	public void testRead()throws Exception{
		ExcelFile excelFile = new ExcelFile("testWrite.xls", 'r');
		ExcelReader reader = excelFile.reader();
		DatabaseResult result = reader.read();
		System.out.println(result);
	}
	

}
