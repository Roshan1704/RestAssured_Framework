package com.restAssuredFramework.testData;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataFromExcel {
	public static Sheet getsheet(String csv_path,String sheetName) {
		FileInputStream file;
		Workbook book;
		try {
			file = new FileInputStream(new File(csv_path));
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return book.getSheetAt(0);
	}

	public static Object[][] getData(String csv_path,String sheetName) 
	{
		Sheet sheet= getsheet(csv_path,sheetName);

		int rowCount = sheet.getPhysicalNumberOfRows();
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		Object[][] data = new Object[rowCount - 1][colCount];

		for (int i = 1; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < colCount; j++) {
				data[i - 1][j] = row.getCell(j).toString();
			}
		}
		return data;
	}
	
	public static ArrayList<String> getDataHeader(String csv_path,String sheetName) 
	{
		Sheet sheet= getsheet(csv_path,sheetName);
		int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
		Row row = sheet.getRow(0);
		ArrayList<String> arr= new ArrayList();
		for (int j = 0; j < colCount; j++) 
		{
			String header=row.getCell(j).toString();
			arr.add(header);
			
		}
		return arr;
			
		
	}
}






















