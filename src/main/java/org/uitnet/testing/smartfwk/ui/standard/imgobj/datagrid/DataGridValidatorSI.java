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
package org.uitnet.testing.smartfwk.ui.standard.imgobj.datagrid;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.objects.ImageObject;
import org.uitnet.testing.smartfwk.ui.core.utils.DataMatchUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.HorizontalScrollbarSI;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.VerticalScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DataGridValidatorSI {
	protected Logger logger = LoggerFactory.getLogger(DataGridValidatorSI.class);
	protected SmartAppDriver appDriver;
	protected DataGridSI dataGrid;
	protected Region region;

	int dataGridW, dataGridH;

	int dataGridHeaderX1, dataGridHeaderY1;
	int dataGridHeaderW, dataGridHeaderH;

	int dataGridRecordsAreaX1, dataGridRecordsAreaY1;
	int dataGridRecordsAreaW, dataGridRecordsAreaH;

	public DataGridValidatorSI(SmartAppDriver appDriver, DataGridSI locator, Region region) {
		this.appDriver = appDriver;
		if (appDriver != null) {
			this.region = (region == null)
					? Region.create(new Rectangle(0, 0,
							Double.valueOf(appDriver.getAppConfig().getBrowserWindowSize().getWidth()).intValue(),
							Double.valueOf(appDriver.getAppConfig().getBrowserWindowSize().getHeight()).intValue()))
					: region;
		}

		this.dataGrid = locator;
	}

	public DataGridSI getUIObject() {
		return dataGrid;
	}

	protected void initializeDataGridHeader(int maxIterationsToLocateElements) {
		Match firstColumnMatch = dataGrid.getKeyHeaderColumns().get(0).getValidator(appDriver, region)
				.findElement(maxIterationsToLocateElements);

		dataGridHeaderX1 = firstColumnMatch.getX();
		dataGridHeaderY1 = firstColumnMatch.getY();
		dataGridHeaderH = firstColumnMatch.getH();

		try {
			BufferedImage bimg;
			dataGridHeaderW = 0;
			for (HeaderColumnSI hcol : dataGrid.getKeyHeaderColumns()) {
				bimg = ImageIO.read(new File(hcol.getColumnImage()));
				dataGridHeaderW = dataGridHeaderW + bimg.getWidth();
			}
			for (HeaderColumnSI hcol : dataGrid.getAdditionalHeaderColumns()) {
				bimg = ImageIO.read(new File(hcol.getColumnImage()));
				dataGridHeaderW = dataGridHeaderW + bimg.getWidth();
			}
		} catch (Throwable th) {
			Assert.fail("Failed to calculate the width of the DataGrid header '" + dataGrid.getDisplayName(), th);
		}

		Screen screen = appDriver.getSikuliScreen();
		dataGridW = dataGrid.getWidth() > 0 ? dataGrid.getWidth() : screen.getX() + screen.getW() - dataGridHeaderX1;
		dataGridH = dataGrid.getHeight() > 0 ? dataGrid.getHeight() : screen.getY() + screen.getH() - dataGridHeaderX1;
	}

	/**
	 * It initializes the datagrid location on the screen. It also initializes the
	 * data grid record area.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public void initializeDataGrid(int maxIterationsToLocateElements) {
		initializeDataGridHeader(maxIterationsToLocateElements);
		dataGridRecordsAreaX1 = dataGridHeaderX1;
		dataGridRecordsAreaY1 = dataGridHeaderY1 + dataGridHeaderH;
		dataGridRecordsAreaW = dataGridHeaderW;
		dataGridRecordsAreaH = dataGrid.getHeight() - dataGridHeaderH;
	}

	protected boolean isHScrollingRequired() {
		if (dataGridHeaderW > dataGridW) {
			return true;
		}
		return false;
	}

	/**
	 * This method searches the record from top to bottom.
	 * 
	 * @param primaryKey       record to be searched in data grid using vertical
	 *                         scrolling.
	 * @param vScrollbar       vertical scrollbar used to navigate the records in
	 *                         the data grid.
	 * @param vScrollbarRegion region where the scrollbar present
	 * @param numHops          how many hops/clicks it should perform per
	 *                         numHopIters to start search for a primary key.
	 * @param numHopIters      number of hops iteration.
	 * @return row location where the primary key present. if null then primary key
	 *         is not found.
	 */
	public RowLocation isRecordPresent(ItemList<SearchCell> primaryKey, VerticalScrollbarSI vScrollbar,
			Region vScrollbarRegion, int numHops, int numHopIters) {
		RowLocation rowLocation = null;
		boolean scrollingEnd = false;

		boolean scrollOpEnabled = false;
		if (vScrollbar != null && !vScrollbar.isScrollbarDisabled(vScrollbarRegion)
				&& vScrollbar.isFullScrollbarVisible(vScrollbarRegion)) {
			scrollOpEnabled = true;
		}

		do {
			rowLocation = isRecordPresent(primaryKey);
			if (rowLocation != null) {
				break;
			}

			if (scrollOpEnabled && numHopIters > 0) {
				scrollingEnd = vScrollbar.clickBottomScrollImage(vScrollbarRegion, numHops);
				if (scrollingEnd) {
					rowLocation = isRecordPresent(primaryKey);
					break;
				}
			} else {
				break;
			}

			numHopIters--;
		} while (numHopIters >= 0);

		return rowLocation;
	}

	/**
	 * 
	 * @param primaryKey record to search.
	 * @return null only if record does not exist.
	 */
	public RowLocation isRecordPresent(ItemList<SearchCell> primaryKey) {
		System.out
				.println("Going to search record " + primaryKey + " in DataGrid '" + dataGrid.getDisplayName() + "'.");
		RowLocation rowLocation = null;
		Set<Integer> rowMarkers = findRowMarkerY1();

		// Find the header column dimensions
		Map<HeaderColumnSI, Rectangle> headerColsLocations = new LinkedHashMap<HeaderColumnSI, Rectangle>();
		Rectangle colRect = null;
		Region headerRegion = Region.create(dataGridHeaderX1, dataGridHeaderY1, dataGridHeaderW, dataGridHeaderH);
		for (SearchCell gridCell : primaryKey.getItems()) {
			colRect = gridCell.getColumn().getValidator(appDriver, headerRegion).findElement(0).getRect();
			headerColsLocations.put(gridCell.getColumn(), colRect);
		}

		// Verify the textValue in record cell.
		Region cellRegion = null;
		String valueToValidate = null;
		ImageObject imgObjToValidate = null;
		int lastProcessedRowEndY = Double.valueOf(colRect.getY()).intValue() + Double.valueOf(colRect.getHeight()).intValue();

		try {
			Iterator<Integer> rowMarkersItr = rowMarkers.iterator();
			for (int i = 0; i < rowMarkers.size(); i++) {
				int rowMarker = rowMarkersItr.next();
				try {
					for (SearchCell gridCell : primaryKey.getItems()) {
						colRect = headerColsLocations.get(gridCell.getColumn());
						cellRegion = new Region(Double.valueOf(colRect.getX()).intValue(), lastProcessedRowEndY,
								Double.valueOf(colRect.getWidth()).intValue(), rowMarker - lastProcessedRowEndY);
						cellRegion.setAutoWaitTimeout(1);

						if (gridCell.getValueType() == ValueType.text) {
							valueToValidate = (String) gridCell.getValue();
							String actualText = cellRegion.text();
							System.out
									.println("Row: " + (i + 1) + ", ColumnName:" + gridCell.getColumn().getDisplayName()
											+ ", Expected: " + valueToValidate + ", Actual: " + actualText);
							DataMatchUtil.validateTextValue(actualText, valueToValidate,
									gridCell.getTextValueValidationMechanism());
						} else if (gridCell.getValueType() == ValueType.imageAsStringPath) {
							valueToValidate = (String) gridCell.getValue();
							Match m = cellRegion.find(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + valueToValidate);
							Assert.assertNotNull(m);
						} else if (gridCell.getValueType() == ValueType.imageAsImageObject) {
							imgObjToValidate = (ImageObject) gridCell.getValue();
							Match m = cellRegion.find(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + imgObjToValidate.getImage(appDriver.getAppConfig().getTestPlatformType(),
									appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
							Assert.assertNotNull(m);
						} else {
							Assert.fail("ValueType '" + gridCell.getValueType().name() + "' is not supported.");
						}

					}
					rowLocation = new RowLocation(cellRegion.getY(), cellRegion.getY() + cellRegion.getH());
					break;
				} catch (Throwable th) {
					// do nothing
				}
				lastProcessedRowEndY = rowMarker;
				if (i == rowMarkers.size() - 1) {
					throw new FindFailed("Unable to find record");
				}
			}
		} catch (Throwable th) {
			// Assert.fail("Failed to find Record'"
			// + primaryKey.getItems() + "' in DataGrid '"
			// + dataGrid.getDisplayName() + "'", th);
		}

		return rowLocation;
	}

	/**
	 * It extracts the records from the datagrid only for the visible columns.
	 * Scrollbar is used to scroll through all the records in the datagrid. It will
	 * only return the unique records for the columns specified.
	 * 
	 * @param numRecords
	 * @param headerColumns
	 * @param vScrollbar
	 * @param vScrollbarRegion
	 * @param numHops
	 * @param numHopIters
	 * @return
	 */
	public List<List<String>> extractRecordsForVisibleColumns(int numRecords, ItemList<HeaderColumnSI> headerColumns,
			VerticalScrollbarSI vScrollbar, Region vScrollbarRegion, int numHops, int numHopIters) {
		List<List<String>> records = new LinkedList<List<String>>();
		boolean scrollingEnd = false;

		boolean scrollOpEnabled = false;
		if (vScrollbar != null && !vScrollbar.isScrollbarDisabled(vScrollbarRegion)
				&& vScrollbar.isFullScrollbarVisible(vScrollbarRegion)) {
			scrollOpEnabled = true;
		}

		List<List<String>> visibleRecords = null;
		int prevRecordSize;
		List<String> row;
		do {
			prevRecordSize = records.size();

			visibleRecords = extractVisibleRecordsForVisibleColumns(headerColumns);
			if (visibleRecords == null || visibleRecords.size() == 0) {
				break;
			}
			for (int i = prevRecordSize; ((numRecords < 0) || (numRecords >= 0 && i < numRecords))
					&& (i - prevRecordSize) < visibleRecords.size(); i++) {
				row = visibleRecords.get((i - prevRecordSize));
				if (!records.toString().equals(row.toString())) {
					records.add(row);
				}
			}
			if (records.size() >= numRecords) {
				break;
			}

			if (scrollOpEnabled && numHopIters > 0) {
				scrollingEnd = vScrollbar.clickBottomScrollImage(vScrollbarRegion, numHops);
				if (scrollingEnd) {
					visibleRecords = extractVisibleRecordsForVisibleColumns(headerColumns);
					if (visibleRecords == null || visibleRecords.size() == 0) {
						break;
					}
					for (int i = prevRecordSize; ((numRecords < 0) || (numRecords >= 0 && i < numRecords))
							&& (i - prevRecordSize) < visibleRecords.size(); i++) {
						records.add(visibleRecords.get((i - prevRecordSize)));
					}
					break;
				}
			} else {
				break;
			}

			numHopIters--;
		} while (numHopIters >= 0);

		return records;
	}

	/**
	 * It extracts the visible records data only for the visible header columns on
	 * the screen.
	 * 
	 * @param headerColumns
	 * @return
	 */
	public List<List<String>> extractVisibleRecordsForVisibleColumns(ItemList<HeaderColumnSI> headerColumns) {
		List<List<String>> records = new LinkedList<List<String>>();

		// Find the header column dimensions
		Map<HeaderColumnSI, Rectangle> headerColsLocations = new LinkedHashMap<HeaderColumnSI, Rectangle>();
		Rectangle colRect = null;
		Region headerRegion = Region.create(dataGridHeaderX1, dataGridHeaderY1, dataGridHeaderW, dataGridHeaderH);
		for (HeaderColumnSI headerColumn : headerColumns.getItems()) {
			colRect = headerColumn.getValidator(appDriver, headerRegion).findElement(0).getRect();
			headerColsLocations.put(headerColumn, colRect);
		}

		String emptyRow = "[";
		for (int i = 1; i < headerColumns.size(); i++) {
			emptyRow = emptyRow + ", ";
		}
		emptyRow = emptyRow + "]";

		// Get the textValue in record cell.
		Region cellRegion = null;
		int lastProcessedRowEndY = Double.valueOf(colRect.getY()).intValue() + Double.valueOf(colRect.getHeight()).intValue();
		Set<Integer> rowMarkers = findRowMarkerY1();
		List<String> row;
		String cellText;
		try {
			Iterator<Integer> rowMarkersItr = rowMarkers.iterator();
			for (int i = 0; i < rowMarkers.size(); i++) {
				int rowMarker = rowMarkersItr.next();
				try {
					row = new LinkedList<String>();
					for (HeaderColumnSI gridCell : headerColumns.getItems()) {
						colRect = headerColsLocations.get(gridCell);
						cellRegion = new Region(Double.valueOf(colRect.getX()).intValue(), lastProcessedRowEndY,
								Double.valueOf(colRect.getWidth()).intValue(), rowMarker - lastProcessedRowEndY);
						cellRegion.setAutoWaitTimeout(1);
						cellText = cellRegion.text();
						if (cellText == null) {
							cellText = "";
						}
						cellText = cellText.trim();
						row.add(cellText);
					}
					if (!emptyRow.equals(row.toString())) {
						records.add(row);
					}
				} catch (Throwable th) {
					// do nothing
				}
				lastProcessedRowEndY = rowMarker;
			}
		} catch (Throwable th) {
			// Assert.fail("Failed to find Record'"
			// + primaryKey.getItems() + "' in DataGrid '"
			// + dataGrid.getDisplayName() + "'", th);
		}
		return records;
	}

	/**
	 * 
	 * @param primaryKey it contains columnName and TextValue to match
	 */
	public void validateRecordPresent(ItemList<SearchCell> primaryKey) {
		Assert.assertNotNull(isRecordPresent(primaryKey),
				"Failed to find Record'" + primaryKey.getItems() + "' in DataGrid '" + dataGrid.getDisplayName() + "'");
	}

	/**
	 * Cell is identified by header column location in row location. It returns the
	 * region of the cell. That can be further used to recognize UI objects inside
	 * this region.
	 * 
	 * @param headerColumn
	 * @param rowLocation
	 * @param hScrollbar
	 * @param scrollStepsToLookup it is the number of clicks to perform to search
	 *                            for the record.
	 * @return the cell region
	 */
	public Region getCellRegion(HeaderColumnSI headerColumn, RowLocation rowLocation, HorizontalScrollbarSI hScrollbar,
			int scrollStepsToLookup) {
		Region headerRegion = Region.create(dataGridHeaderX1, dataGridHeaderY1, dataGridHeaderW, dataGridHeaderH);

		Rectangle colRect = null;
		if (hScrollbar == null) {
			try {
				colRect = headerColumn.getValidator(appDriver, headerRegion).findElement(0).getRect();
			} catch (Throwable th) {

			}
		} else {
			Region hScrollRegion = Region.create(dataGridHeaderX1, dataGridHeaderY1 + dataGridH - 100, dataGridW,
					dataGridH);
			hScrollbar.scrollThumbGripToExtremeLeft(hScrollRegion);

			boolean scrollEnded = false;
			while (!scrollEnded) {
				try {
					colRect = headerColumn.getValidator(appDriver, headerRegion).findElement(0).getRect();
					break;
				} catch (Throwable th) {
					scrollEnded = hScrollbar.clickRightScrollImage(hScrollRegion, scrollStepsToLookup);
				}
			}
		}

		Assert.assertNotNull(colRect, "Failed to find HeaderColumn '" + headerColumn.getDisplayName()
				+ "' in DataGrid '" + dataGrid.getDisplayName() + "'.");

		Region cellRegion = Region.create(Double.valueOf(colRect.getX()).intValue(), rowLocation.getY1(),
				Double.valueOf(colRect.getWidth()).intValue(), rowLocation.getRowHeight());
		cellRegion.setAutoWaitTimeout(1);

		return cellRegion;
	}

	public void validateCellValuePresent(SearchCell cellValue, RowLocation rowLocation, HorizontalScrollbarSI hScrollbar,
			int scrollStepsToLookup) {
		Region headerRegion = Region.create(dataGridHeaderX1, dataGridHeaderY1, dataGridHeaderW, dataGridHeaderH);

		Rectangle colRect = null;
		if (hScrollbar == null) {
			try {
				colRect = cellValue.getColumn().getValidator(appDriver, headerRegion).findElement(0).getRect();
			} catch (Throwable th) {

			}
		} else {
			Region hScrollRegion = Region.create(dataGridHeaderX1, dataGridHeaderY1 + dataGridH - 100, dataGridW,
					dataGridH);
			hScrollbar.scrollThumbGripToExtremeLeft(hScrollRegion);

			boolean scrollEnded = false;
			while (!scrollEnded) {
				try {
					colRect = cellValue.getColumn().getValidator(appDriver, headerRegion).findElement(0).getRect();
					break;
				} catch (Throwable th) {
					scrollEnded = hScrollbar.clickRightScrollImage(hScrollRegion, scrollStepsToLookup);
				}
			}
		}

		Assert.assertNotNull(colRect, "Failed to find HeaderColumn '" + cellValue.getColumn().getDisplayName()
				+ "' in DataGrid '" + dataGrid.getDisplayName() + "'.");

		Region cellRegion = Region.create(Double.valueOf(colRect.getX()).intValue(), rowLocation.getY1(),
				Double.valueOf(colRect.getWidth()).intValue(), rowLocation.getRowHeight());
		cellRegion.setAutoWaitTimeout(1);

		String valueToValidate = null;
		ImageObject imgObjToValidate = null;
		try {
			if (cellValue.getValueType() == ValueType.text) {
				valueToValidate = (String) cellValue.getValue();
				String actualText = cellRegion.text();
				System.out.println("ColumnName:" + cellValue.getColumn().getDisplayName() + ", Expected: "
						+ valueToValidate + ", Actual: " + actualText);
				DataMatchUtil.validateTextValue(actualText, valueToValidate,
						cellValue.getTextValueValidationMechanism());
			} else if (cellValue.getValueType() == ValueType.imageAsStringPath) {
				valueToValidate = (String) cellValue.getValue();
				Match m = cellRegion.find(valueToValidate);
				Assert.assertNotNull(m);
			} else if (cellValue.getValueType() == ValueType.imageAsImageObject) {
				imgObjToValidate = (ImageObject) cellValue.getValue();
				Match m = cellRegion.find(imgObjToValidate.getImage(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(m);
			}
		} catch (Throwable th) {
			Assert.fail(
					"Failed to match cell value '" + cellValue + "' in DataGrid '" + dataGrid.getDisplayName() + "'.",
					th);
		}
	}

	protected Set<Integer> findRowMarkerY1() {
		Set<Integer> rowMarkerY1 = new TreeSet<Integer>();
		ImageObject rowMarkerImgObj;
		List<Match> matches;
		for (String rowMarkerImg : dataGrid.getRowMarkerImages()) {
			try {
				rowMarkerImgObj = new ImageObject(UIObjectType.image, dataGrid.getDisplayName() + "-RowMarker",
						rowMarkerImg);
				matches = rowMarkerImgObj.getValidator(appDriver, new Region(dataGridRecordsAreaX1 - 8,
						dataGridRecordsAreaY1 + 5, dataGridRecordsAreaX1 + 50, dataGridRecordsAreaH)).findElements(0);
				if (matches != null) {
					for (Match m : matches) {
						rowMarkerY1.add(m.getY());
					}
				}
			} catch (Throwable th) {
				// th.printStackTrace();
				continue;
			}
		}

		// normalize points with distance 10
		Set<Integer> rowMarkerNormY1 = new TreeSet<Integer>();
		List<Integer> conPoints = new LinkedList<Integer>();
		int counter = rowMarkerY1.size();
		for (int p : rowMarkerY1) {
			counter--;
			if ((conPoints.size() == 0) || (p - conPoints.get(conPoints.size() - 1) < 10)) {
				conPoints.add(p);
			} else if (p - conPoints.get(conPoints.size() - 1) >= 10) {
				int sum = 0;
				for (int cp1 : conPoints) {
					sum = sum + cp1;
				}
				rowMarkerNormY1.add(sum / conPoints.size());
				conPoints.clear();
			}

			if (counter == 0 && conPoints.size() > 0) {
				int sum = 0;
				for (int cp1 : conPoints) {
					sum = sum + cp1;
				}
				rowMarkerNormY1.add(sum / conPoints.size());
				conPoints.clear();
			}
		}

		return rowMarkerNormY1;
	}

	// TODO - Have to implement
	public boolean isPresent(int maxIterationsToLocateElements) {
		return false;
	}

}
