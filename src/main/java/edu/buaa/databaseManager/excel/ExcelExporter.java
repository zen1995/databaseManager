package edu.buaa.databaseManager.excel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import edu.buaa.databaseManager.database.DatabaseResult;
import edu.buaa.databaseManager.database.Pair;

public class ExcelExporter {
	private ExcelFile excelFile = null;
	private List<Pair> columns = null;
	private List<Map<String, Object>> data = null;
	private ExcelSheet sheet = null;

	private boolean hasWriteTitle = false;

	public ExcelExporter(String path) throws FileNotFoundException, IOException {
		excelFile = new ExcelFile(path, 'w');
	}

	public void export(DatabaseResult result) throws FileNotFoundException, IOException {
		columns = result.getColumns();
		data = result.getData();
		sheet = excelFile.getSheet("sheet1");
		writeTitle();
		writeData();
		excelFile.writeToFile();
	}

	private void writeTitle() {
		if (hasWriteTitle) {
			return;
		}
		hasWriteTitle = true;
		int size = columns.size();
		for (int i = 0; i < size; i++) {
			Pair pair = columns.get(i);
			sheet.writeCell(Config.columnTitleRow, i, pair.key);
		}
	}

	private void writeData() {
		int LastRow = sheet.getLastRowIndex();
		int size = data.size();
		int columnCount = columns.size();
		for (int i = 0; i < size; i++) {
			int row = LastRow + 1 + i;
			Map<String, Object> map = data.get(i);
			for (int col = 0; col < columnCount; col++){
				sheet.writeCell(row, col, String.valueOf(map.get(columns.get(col).key)));
				//System.out.println(String.valueOf(map.get(columns.get(col).key)));
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
