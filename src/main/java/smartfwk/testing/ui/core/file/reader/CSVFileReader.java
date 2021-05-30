/*
 * SmartTestAutoFramework
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
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.Locations;
import smartfwk.testing.ui.core.file.reader.support.Table;

public class CSVFileReader {

	private CSVFileReader() {
	}

	public static Table getData(String filePath) {
		return getData(filePath, ',', '"', true);
	}
	
	public static Table getData(String filePath, char delimiter, char quoteChar, boolean trimCellData) {
		Assert.assertNotNull(filePath, "CSV file name cannot be empty.");

		filePath = Locations.getProjectRootDir() + File.separator + filePath;
		Table table = new Table(extractFileName(filePath));
		List<String> row;
		try (Reader fileReader = new FileReader(filePath)) {
			Iterable<CSVRecord> records = CSVFormat.DEFAULT
					.withAllowDuplicateHeaderNames(true)
					.withQuote(quoteChar)
					.withDelimiter(delimiter)
					.withIgnoreEmptyLines(false)
					.withIgnoreSurroundingSpaces()
					.parse(fileReader);
			int rowCounter = 0;
			String colValue;
			for (CSVRecord record : records) {
				row = new ArrayList<>();
				
				if(record.size() == 1) {
					if((table.getColumnNames().size() < 1 && "".equals(record.get(0).trim()))
							|| (table.getColumnNames().size() > 0)) {
						rowCounter++;
						continue;
					}	
					
					
				}
				
				for (int i = 0; i < record.size(); i++) {
					colValue = record.get(i);
					row.add(colValue);
				}

				if (table.getColumnNames().size() == 0) {
					List<String> dupCols = findDuplicates(row);
					Assert.assertEquals(dupCols.size(), 0 , "Duplicate columns " + dupCols + " found in CSV file '" + filePath + "'. Please remove invalid one.");
					int emptyColIndex = row.indexOf("");
					Assert.assertFalse(emptyColIndex >= 0, "Empty column name found at column number " + (emptyColIndex + 1) 
							+" in CSV file '" + filePath + "'. Please remove empty column name or give a valid name.");
					table.addColumnNames(row);
				} else {
					Assert.assertEquals(row.size(), table.getColumnNames().size(),
							"Column count " + table.getColumnNames().size() + " and record column count " + row.size()
									+ " does not match on row " + (rowCounter+1) + " within CSV file '" + filePath + "'.");
					table.addRow(row);
				}

				rowCounter++;
			}

		} catch (Exception ex) {
			Assert.fail("Failed to read CSV file '" + filePath + "' data.", ex);
		}

		return table;
	}

	private static String extractFileName(String filePath) {
		String[] arr = new File(filePath).getName().split("[.]");
		return arr[0].trim();
	}
	
	private static List<String> findDuplicates(List<String> list) {
		return list.stream().distinct().filter(entry -> Collections.frequency(list, entry) > 1).collect(Collectors.toList());
	}

//	public static void main(String[] args) {
//		Table table = CSVFileReader.getData("test-data/usa-states.csv");
//
//		System.out.println(table);
//		
//		List<TableRow> filteredRows = table.getRows(new RowFilter()
//				.condition(new Condition("State Name", Operator.eq, "Arizona"))
//				.or()
//				.condition(new Condition("State Name", Operator.contains, "zo"))
//				.and()
//				.condition(new Condition("State Abbr", Operator.contains, "KK"))
//				);
//		
//		System.out.println("\n\n" + filteredRows);
//	}
}
