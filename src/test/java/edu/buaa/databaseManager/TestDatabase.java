package edu.buaa.databaseManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buaa.databaseManager.database.ColumnType;
import edu.buaa.databaseManager.database.DBConnection;
import edu.buaa.databaseManager.database.DatabaseHelper;
import edu.buaa.databaseManager.database.DatabaseResult;
import edu.buaa.databaseManager.database.Pair;
import edu.buaa.databaseManager.excel.ExcelFile;
import junit.framework.TestCase;

public class TestDatabase extends TestCase {
	private String testTableName = "testTable".toLowerCase();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		try {
			if(DatabaseHelper.hasTable(testTableName)){
				DatabaseHelper.deleteTable(testTableName);
			}
			DatabaseHelper.createCMRTable(testTableName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@After
	public void tearDown() throws Exception {
		DatabaseHelper.deleteTable(testTableName);
	}
	
	
	
	@Test
	public void testGetColumns()throws Exception{
		DatabaseHelper.getColumns(testTableName);
		boolean r= true;
		assertTrue(r);
		
	}
	
	@Test
	public void testCreateTable()throws Exception{
		String s = "testc"+(int)(Math.random()*1000);
		boolean r = DatabaseHelper.hasTable(s);
		assertEquals(false,r);
		DatabaseHelper.createTable(s);
		r = DatabaseHelper.hasTable(s);
		assertEquals(true,r);
		List<Pair> list = DatabaseHelper.getColumns(s);
		assertEquals(1, list.size());
		assertEquals("id", list.get(0).key);
		DatabaseHelper.deleteTable(s);
		r = DatabaseHelper.hasTable(s);
		assertEquals(false,r);
	}
	
	@Test
	public void testGetTableName()throws Exception{
		List<String> list = DatabaseHelper.getTableNames();
		//System.out.println(list);
		//assertEquals(1,list.size());
		//assertEquals(testTableName,list.get(0));
		//System.out.println(testTableName);
		//System.out.println(list.contains(testTableName));
		assertTrue(list.contains(testTableName.toLowerCase()));
	}
	@Test
	public void testHasColumn()throws Exception{
		boolean r = DatabaseHelper.hasColumn(testTableName, "id");
		
		assertEquals(true,r);
		
		r = DatabaseHelper.hasColumn(testTableName, "sfafasdafa");
		assertEquals(false, r);
	}
	
	@Test
	public void testInsertColumn()throws Exception{
		DatabaseHelper.insertColumn(testTableName, "sadasda", ColumnType.text,"id");
		boolean r = DatabaseHelper.hasColumn(testTableName, "sadasda");
		assertTrue(r);
	}
	
	@Test 
	public void testDeleteColumn()throws Exception{
		DatabaseHelper.insertColumn(testTableName, "aasadasda", ColumnType.text,"id");
		boolean r = DatabaseHelper.hasColumn(testTableName, "aasadasda");
		assertTrue(r);
		DatabaseHelper.deleteColumn(testTableName, "aasadasda");
		r = DatabaseHelper.hasColumn(testTableName, "aasadasda");
		assertTrue(!r);
	}
	
	@Test
	public void testInsertRecord()throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("age", 1);
		map.put("sex", "male");
		DatabaseHelper.insertRecord(testTableName, map);
	}
	
	@Test
	public void testClearRecord()throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("age", 1);
		map.put("sex", "male");
		DatabaseHelper.insertRecord(testTableName, map);
		DatabaseHelper.clearTable(testTableName);
		
	}
	@Test
	public void testDeleteRecord()throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("sex", "male");
		DatabaseHelper.insertRecord(testTableName, map);
		map.put("id", 2);
		DatabaseHelper.insertRecord(testTableName, map);
		assertEquals(2,DatabaseHelper.search(testTableName).getData().size());
		List<Integer> deleteList = new ArrayList<>();
		deleteList.add(1);
		DatabaseHelper.deleteRecord(testTableName, deleteList);
		assertEquals(1,DatabaseHelper.search(testTableName).getData().size());

	}
	
	@Test
	public void testEdit()throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", 1);
		map.put("sex", "male");
		DatabaseHelper.insertRecord(testTableName, map);
		List<Map<String, Object>> data = DatabaseHelper.search(testTableName).getData();
		assertEquals(1,data.size());
		assertEquals("male",data.get(0).get("sex"));
		map.put("sex","female");
		DatabaseHelper.editRecord(testTableName,1,map);
		data = DatabaseHelper.search(testTableName).getData();
		assertEquals("female",data.get(0).get("sex"));
	}
	
	@Test
	public void test()throws Exception{
		DatabaseResult result = new ExcelFile("test/CMR.xlsx",'r').reader().read();
		
		String tableName = "tests";
		if(DatabaseHelper.hasTable(tableName)){
			DatabaseHelper.deleteTable(tableName);
		}
		DatabaseHelper.createTable(tableName);
		DatabaseHelper.insertColumn(tableName,"病人ID",ColumnType.doubleNumber,"id");
		DatabaseHelper.insertColumn(tableName,"姓名", ColumnType.text,"病人ID");
		DatabaseHelper.insertColumn(tableName,"性别",ColumnType.text, "姓名");
		DatabaseHelper.insertColumn(tableName,"体重",ColumnType.doubleNumber, "性别");
		DatabaseHelper.insertRecord(tableName,result);
		System.out.println(DatabaseHelper.search(tableName).getData().size());
		DatabaseHelper.deleteTable(tableName);
	}
}
