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
package smartfwk.testing.ui.core.file.reader.support;

import java.util.List;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TableRow {
	private Table table;
	private int rowIndex;

	public TableRow(Table table, int rowIndex) {
		this.table = table;
		this.rowIndex = rowIndex;
	}

	public String getColumnValue(String columnName) {
		int colIndex = table.getColumnIndex(columnName);
		List<String> row = table.getRawRows().get(rowIndex);
		return row.get(colIndex);
	}

	public String getCulumnNumericPart(String columnName) {
		String intValue = "";
		String colValue = getColumnValue(columnName);
		try {
			intValue = colValue.split("[.]")[0];
		} catch (Exception ex) {
			Assert.fail("Invalid integer value '" + colValue + "' in table '" + table.getTableName() + "', column '"
					+ columnName + "'.");
		}

		return intValue;
	}

	@Override
	public String toString() {
		return table.getRawRow(rowIndex).toString();
	}
}
