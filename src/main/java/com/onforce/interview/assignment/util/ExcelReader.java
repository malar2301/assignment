package com.onforce.interview.assignment.util;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public String path;
	FileInputStream fis;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;

	public ExcelReader(String path) {
		this.path = path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCellData(String sheetName, int rowNum, String colName) {
		try {
			int colNum = 0;
			int sheetIndex = workbook.getSheetIndex(sheetName);
			sheet = workbook.getSheetAt(sheetIndex);
			// Get the number of column in the input sheet
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().equals(colName)) {
					colNum = i;
				}
			}
			// get the row number
			row = sheet.getRow(rowNum - 1);
			// get the value of the cell
			cell = row.getCell(colNum);
			// return the cell value based on the datatype
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			} else return "";

		} catch (Exception e) {
			return "";
		}
		//return "";
	}

	// Getting row count
	public int getRowCount(String sheetName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				return 0;
			} else {
				sheet = workbook.getSheetAt(index);
				int rowCount = sheet.getLastRowNum() + 1;
				return rowCount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// get column count
	public int getColumnCount(String sheetName) {
		try {
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) {
				return 0;
			} else {
				sheet = workbook.getSheetAt(index);
				row = sheet.getRow(0);
				int columnCount = row.getLastCellNum();
				return columnCount;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
