package edu.buaa.databaseManager.excel;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.apache.poi;
public class ExcelFile extends File {

	private Workbook workbook = null;

	public ExcelFile(String pathname, char methord) throws FileNotFoundException, IOException {
		super(pathname);
		if (methord == 'w') {
			String suffix = getFileSuffix();
			if (suffix.equals("xls")) {
				workbook = new HSSFWorkbook();
			} else if (suffix.equals("xlsx")) {
				workbook = new SXSSFWorkbook();
			} else {
				throw new FileNotFoundException("undefined suffix:" + pathname);
			}
		}
		if (methord == 'r') {
			String suffix = getFileSuffix();
			if (suffix.equals("xls")) {
				// POIFSFileSystem fs = new POIFSFileSystem(new
				// FileInputStream(pathname));
				workbook = new HSSFWorkbook(new FileInputStream(pathname));
			} else if (suffix.equals("xlsx")) {
				workbook = new XSSFWorkbook(new FileInputStream(pathname));
			} else {
				throw new FileNotFoundException("undefined suffix:" + pathname);
			}
		}

	}

	public ExcelSheet getSheet(String name) {
		Sheet sheet = workbook.getSheet(name);
		if (sheet == null)
			sheet = workbook.createSheet(name);
		ExcelSheet s = new ExcelSheet(sheet);
		return s;
	}
	public ExcelSheet getSheet(int index) {
		Sheet sheet = workbook.getSheetAt(index);
		if (sheet == null)
			sheet = workbook.createSheet("sheet"+(index+1));
		ExcelSheet s = new ExcelSheet(sheet);
		return s;
	}
	public boolean hasSheet(String name) {
		Sheet sheet = workbook.getSheet(name);
		if (sheet == null)
			return false;
		return true;
	}
	
	public boolean hasSheet(int index){
		Sheet sheet = workbook.getSheetAt(index);
		if (sheet == null)
			return false;
		return true;
	}
	
	public ExcelExporter exporter() throws FileNotFoundException, IOException {
		return new ExcelExporter(getAbsolutePath());
	}

	public ExcelReader reader() throws FileNotFoundException, IOException{
		return new ExcelReader(getAbsolutePath());
	}
	
	
	public boolean writeToFile() throws FileNotFoundException, IOException {
		FileOutputStream os = new FileOutputStream(getAbsolutePath());
		workbook.write(os);
		os.close();
		return true;
	}

	// public boolean

	private static void testWrite() throws FileNotFoundException, IOException {
		ExcelFile excel = new ExcelFile("w.xls", 'w');
		ExcelSheet mySheet = excel.getSheet("sheet1");
		mySheet.writeCell(0, 0, "aa");
		mySheet.writeCell(1, 1, 1, 2, "bbb");
		try {
			excel.writeToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testRead() throws FileNotFoundException, IOException {
		ExcelFile excel = new ExcelFile("w.xls", 'r');
		Sheet sheet = excel.workbook.getSheetAt(0);
		Row row = sheet.getRow(1);
		Cell cell = row.getCell(1);
		System.out.println(cell.getStringCellValue());
		int rows = sheet.getPhysicalNumberOfRows();
		System.out.println(rows);
		System.out.println(excel.getSheet("sheet1").getLastRowIndex());
	}

	public static void main(String[] args) throws Exception {

		// testWrite();
		testRead();
		if (true)
			return;

		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");

		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);
		sheet.autoSizeColumn(2, true);
		// 创建字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("Verdana");
		font.setBoldweight((short) 100);
		font.setFontHeight((short) 300);
		font.setColor(HSSFColor.BLUE.index);

		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// 设置边框
		style.setBottomBorderColor(HSSFColor.RED.index);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);

		style.setFont(font);// 设置字体

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);// 设定行的高度
		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 合并单元格(startRow，endRow，startColumn，endColumn)
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

		// 给Excel的单元格设置样式和赋值
		cell.setCellStyle(style);
		cell.setCellValue("hello world");

		// 设置单元格内容格式
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("h:mm:ss"));

		style1.setWrapText(true);// 自动换行

		row = sheet.createRow(1);

		// 设置单元格的样式格式

		cell = row.createCell(0);
		cell.setCellStyle(style1);

		// 创建超链接
		HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
		link.setAddress("http://www.baidu.com");
		cell = row.createCell(1);
		cell.setCellValue("百度asdsssssssssssssssssssss");
		// cell.setHyperlink(link);// 设定单元格的链接

		FileOutputStream os = new FileOutputStream("w.xls");
		wb.write(os);
		os.close();

	}

}
