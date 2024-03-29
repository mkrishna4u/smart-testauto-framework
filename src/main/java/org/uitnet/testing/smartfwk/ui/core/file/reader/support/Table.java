/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.ui.core.file.reader.support;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class Table {
	private String tableName;
	private List<String> columnNames;
	private List<List<String>> rows;

	public Table(String tableName) {
		this.tableName = tableName;
		columnNames = new ArrayList<>();
		rows = new ArrayList<>();
	}

	public String getTableName() {
		return tableName;
	}

	public void addColumnName(String columnName) {
		this.columnNames.add(columnName);
	}

	public void addColumnNames(List<String> columnNames) {
		this.columnNames.addAll(columnNames);
	}

	public void addRow(List<String> row) {
		Assert.assertFalse(columnNames.size() == 0,
				"Failed to add row as there is no column added in the table '" + tableName + "'.");
		Assert.assertFalse(row == null, "Row cannot be empty.");
		if (row.size() < columnNames.size()) {
			int diff = columnNames.size() - row.size();
			while (diff > 0) {
				row.add("");
				diff--;
			}
		} else if (row.size() > columnNames.size()) {
			int diff = row.size() - columnNames.size();
			while (diff > 0) {
				row.remove(diff);
				diff--;
			}
		}
		rows.add(row);
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public List<List<String>> getRawRows() {
		return rows;
	}

	public List<String> getRawRow(int index) {
		return rows.get(index);
	}

	public List<TableRow> getRows() {
		List<TableRow> tableRows = new ArrayList<>();
		for (int i = 0; i < rows.size(); i++) {
			tableRows.add(new TableRow(this, i));
		}

		return tableRows;
	}

	public List<String> getColumnData(String columnName) {
		int colIndex = columnNames.indexOf(columnName);
		Assert.assertTrue(colIndex >= 0,
				"Column '" + columnName + "' does not exist in the table '" + tableName + "'.");

		List<String> colData = new ArrayList<>();

		for (List<String> row : rows) {
			if (row.size() > colIndex) {
				colData.add(row.get(colIndex));
			} else {
				colData.add("");
			}
		}

		return colData;
	}

	public int getRowCount() {
		return rows.size();
	}

	public int getColumnIndex(String columnName) {
		int colIndex = columnNames.indexOf(columnName);
		Assert.assertTrue(colIndex >= 0,
				"Column '" + columnName + "' does not exist in the table '" + tableName + "'.");
		return colIndex;
	}

	/**
	 * Return the row for a particular row number.
	 * 
	 * @param rowNumber - values from 1 to n.
	 * @return
	 */
	public TableRow getRow(int rowNumber) {
		Assert.assertFalse(rowNumber < 1 || rowNumber > rows.size(),
				"Row number should be between 1 and " + rows.size() + ".");
		TableRow trow = new TableRow(this, rowNumber - 1);
		return trow;
	}

	public List<TableRow> getRows(RowFilter rowFilter) {
		List<TableRow> tableRows = new ArrayList<>();
		Assert.assertFalse(rowFilter == null || rowFilter.getFilter().size() == 0, "Row filter can not be empty.");
		List<Object> filter = rowFilter.getFilter();
		TableRow trow;
		String colValue;
		Join nextJoin = null;
		boolean condition1Matched, condition2Matched;
		for (int i = 0; i < rows.size(); i++) {
			trow = new TableRow(this, i);
			condition1Matched = false;
			nextJoin = null;
			for (Object st : filter) {
				if (st instanceof Condition) {
					if (nextJoin == null) {
						colValue = trow.getColumnValue(((Condition) st).getColumnName());
						condition1Matched = (colValue != null
								&& areValuesMatched(colValue, ((Condition) st).getOp(), ((Condition) st).getValue()));
					} else {
						colValue = trow.getColumnValue(((Condition) st).getColumnName());
						condition2Matched = (colValue != null
								&& areValuesMatched(colValue, ((Condition) st).getOp(), ((Condition) st).getValue()));
						if (nextJoin == Join.and) {
							condition1Matched = condition1Matched && condition2Matched;
						} else if (nextJoin == Join.or) {
							condition1Matched = condition1Matched || condition2Matched;
						}
					}
				} else if (st instanceof Join) {
					nextJoin = (Join) st;
					continue;
				}

				if (filter.size() == 1 && !condition1Matched) {
					break;
				}
			}

			if (condition1Matched) {
				tableRows.add(trow);
			}
		}

		return tableRows;
	}

	private boolean areValuesMatched(String value1, Operator op, String value2) {
		boolean matched = false;
		switch (op) {
		case eq:
			if (value1.equals("") && value2.equals("")) {
				matched = true;
			} else if (value1.equals("") || value2.equals("")) {
				matched = false;
			} else {
				try {
					matched = Double.parseDouble(value1) == Double.parseDouble(value2);
				} catch (Exception ex) {
					matched = value1.equals(value2);
				}
			}
			break;
		case ne:
			if (value1.equals("") && value2.equals("")) {
				matched = false;
			} else if (value1.equals("") || value2.equals("")) {
				matched = true;
			} else {
				try {
					matched = Double.parseDouble(value1) != Double.parseDouble(value2);
				} catch (Exception ex) {
					matched = !value1.equals(value2);
				}
			}
			break;
		case gt:
			if (value1.equals("") && value2.equals("")) {
				matched = true;
			} else if (value1.equals("") || value2.equals("")) {
				matched = false;
			} else {
				matched = Double.parseDouble(value1) > Double.parseDouble(value2);
			}
			break;
		case gte:
			if (value1.equals("") && value2.equals("")) {
				matched = true;
			} else if (value1.equals("") || value2.equals("")) {
				matched = false;
			} else {
				matched = Double.parseDouble(value1) >= Double.parseDouble(value2);
			}
			break;
		case lt:
			if (value1.equals("") && value2.equals("")) {
				matched = true;
			} else if (value1.equals("") || value2.equals("")) {
				matched = false;
			} else {
				matched = Double.parseDouble(value1) < Double.parseDouble(value2);
			}
			break;
		case lte:
			if (value1.equals("") && value2.equals("")) {
				matched = true;
			} else if (value1.equals("") || value2.equals("")) {
				matched = false;
			} else {
				matched = Double.parseDouble(value1) <= Double.parseDouble(value2);
			}
			break;
		case startsWith:
			matched = value1.startsWith(value2);
			break;
		case dontStartsWith:
			matched = !value1.startsWith(value2);
			break;
		case contains:
			matched = value1.contains(value2);
			break;
		case dontContains:
			matched = !value1.contains(value2);
			break;
		case endsWith:
			matched = value1.endsWith(value2);
			break;
		case dontEndsWith:
			matched = !value1.endsWith(value2);
			break;
		default:
			break;
		}

		return matched;
	}

	@Override
	public String toString() {
		String str = "Table Name: " + tableName + "\n" + columnNames + "\n";
		for (List<String> row : rows) {
			str = str + row + "\n";
		}
		return str;
	}
}
