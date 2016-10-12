package edu.buaa.databaseManager.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;

public class ExcelSheet {
	private CellStyle defautCellStyle;
	private Font defaultFont;
	private Sheet sheet;

	public ExcelSheet(Sheet sheet) {
		this.sheet = sheet;
		init();
	}

	private void init() {
		defaultFont = sheet.getWorkbook().createFont();
		defaultFont.setFontName("宋体");
		defaultFont.setFontHeightInPoints((short) 11);

		defautCellStyle = sheet.getWorkbook().createCellStyle();
		defautCellStyle.setWrapText(true);
		defautCellStyle.setFont(defaultFont);
	}

	private Cell getCell(int row, int column){
		Row  r = sheet.getRow(row);
		if(r == null) r = sheet.createRow(row);
		Cell cell = r.getCell(column);
		if( cell == null) cell = r.createCell(column);
		return cell;
	}
	
	public ExcelSheet writeCell(int row, int column,String s) {
		Cell cell = getCell(row, column);
		cell.setCellValue(s);
		cell.setCellStyle(defautCellStyle);
		return this;
	}
	
	public boolean isCellExist(int row,int column){
		Row  r = sheet.getRow(row);
		if(r == null){
			return false;
		}
		Cell cell = r.getCell(column);
		if(cell == null){
			return false;
		}
		return true;
	}
	
	public boolean isRowExist(int row){
		Row  r = sheet.getRow(row);
		if(r == null){
			return false;
		}
		return true;
	}
	
	public ExcelSheet writeCell(int row,int column,int width,int height,String s){
		if(width+ height > 2){
			sheet.addMergedRegion(new CellRangeAddress(row, row+width-1, column, column+height-1));
		}
		writeCell(row, column, s);
		return this;
	}
	
	public String readCell(int row,int column){
		if(!isCellExist(row, column)){
			return null;
		}
		Cell cell = getCell(row, column);
		return cell.getStringCellValue();
	}
	
	public int getLastRowIndex(){
		return sheet.getLastRowNum();
	}
	
	
//	public static void main(String[] args) {
//		Integer a =null;
//		System.out.println(String.valueOf(a));
//	}

}
