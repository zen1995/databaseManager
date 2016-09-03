package edu.buaa.databaseManager;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.buaa.databaseManager.database.ColumnType;
import edu.buaa.databaseManager.database.DBConnection;
import edu.buaa.databaseManager.database.DatabaseHelper;
import junit.framework.TestCase;

public class TestDatabase extends TestCase {
	private String testTableName = "testTable";
	
	
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
}
