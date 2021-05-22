/*
 * SmartTestAutoFwk
 * Copyright 2021 and beyond
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package smartfwk.testing.ui.core.file.reader;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.Locations;
import smartfwk.testing.ui.core.file.reader.support.Table;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ExcelFileReader {

	public static Table getSheetData(String fileName, String sheetName) {
		Assert.assertNotNull(fileName, "Excel file name cannot be empty.");
		Assert.assertNotNull(sheetName, "Sheet name in Excel file '" + fileName + "' cannot be empty.");

		fileName = Locations.getProjectRootDir() + File.separator + fileName;

		return loadExcelSheet(fileName, sheetName);
	}

	private static Sheet getSheetByName(Workbook workbook, String sheetName) {
		int numSheets = workbook.getNumberOfSheets();
		
		for(int i = 0; i < numSheets; i++) {
			if(sheetName.equals(workbook.getSheetAt(i).getSheetName())) {
				return workbook.getSheetAt(i);
			}
		}
		
		return null;
	}
	
	private static Table loadExcelSheet(String fileName, String sheetName) {
		Table table = new Table(sheetName);
		boolean isXlsExt = fileName.toLowerCase(Locale.ENGLISH).endsWith(".xls");

		try (FileInputStream file = new FileInputStream(new File(fileName));
				Workbook workbook = isXlsExt ? new HSSFWorkbook() : new XSSFWorkbook(file);) {
			Sheet sheet = getSheetByName(workbook, sheetName);
			Assert.assertNotNull(sheet, "Sheet '" + sheetName + "' does not exist in excel file '" + fileName + "'.");

			String cellValue;
			int rowCounter = 0, columnCounter;
			boolean isEmptyRow;
			List<String> cellValues;
			for (Row row : sheet) {
				columnCounter = 0;
				isEmptyRow = false;
				cellValues = new ArrayList<>();
				for (Cell cell : row) {
					switch (cell.getCellType()) {
					case STRING:
					case BLANK:
						cellValue = "" + cell.getStringCellValue();
						break;
					case NUMERIC:
						cellValue = "" + cell.getNumericCellValue();
						break;
					case BOOLEAN:
						cellValue = "" + cell.getBooleanCellValue();
						break;
					case FORMULA:
						try {
							cellValue = "" + cell.getStringCellValue();
						} catch (Exception e) {
							try {
								cellValue = "" + cell.getNumericCellValue();
							} catch (Exception e2) {
								cellValue = "" + cell.getCellFormula();
							}
						}
						break;
					default:
						cellValue = "" + cell.getStringCellValue();
						break;
					}

					if (rowCounter == 0 && columnCounter == 0) {
						if (cell.getColumnIndex() != columnCounter || "".equals(cellValue.trim())) {
							// Skipping empty rows before header column row based on first cell value.
							isEmptyRow = true;
							break;
						}
					}

					if(rowCounter == 0) {
						cellValues.add(cellValue.trim());
					} else {
						cellValues.add(cellValue);
					}
					

					columnCounter++;
				}

				if (isEmptyRow) {
					continue;
				}

				// 0th row is the header row.
				if (rowCounter == 0) {
					table.addColumnNames(cellValues);
				} else {
					table.addRow(cellValues);
				}

				rowCounter++;
			}
		} catch (Exception ex) {
			Assert.fail("Failed to load data from excel file '" + fileName + "'.", ex);
		}

		return table;
	}
	
	/* public static void main(String[] args) {
		Table table = ExcelFileReader.getSheetData("text.xlsx", "State Info");
		
		System.out.println(table);
		System.out.println(table.getRow(1).getCulumnIntValue("BB"));
	} */
}
