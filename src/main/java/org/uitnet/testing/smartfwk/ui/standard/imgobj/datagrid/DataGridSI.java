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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.sikuli.script.Region;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.LocatorType;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.HorizontalScrollbar;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.VerticalScrollbar;

/**
 * This DataGrid does not work 1. If the whole table is not visible including
 * horizontal and vertical scrollbars of the tables. 2. If there is no row
 * marker to identify the row break. 3. Header columns are not fixed 4. If all
 * the primary key columns are not visible always. 5. Primary key column value
 * is not text
 * 
 * @author Madhav Krishna
 *
 */
public class DataGridSI {
	protected LocatorType locatorType;
	protected UIObjectType uiObjectType;
	protected String displayName;

	protected List<HeaderColumnSI> keyHeaderColumns;
	protected List<HeaderColumnSI> additionalHeaderColumns;
	protected List<String> rowMarkerImages;
	protected int numLeftFrozenColumns;
	protected int numRightFrozenColumns;
	protected VerticalScrollbar vScrollbar;
	protected HorizontalScrollbar hScrollbar;
	protected int width;
	protected int height;

	public DataGridSI(String displayName, int widthInPx, int heightInPx) {
		this.locatorType = LocatorType.IMAGE;
		this.uiObjectType = UIObjectType.table;
		this.displayName = displayName;

		keyHeaderColumns = new LinkedList<HeaderColumnSI>();
		additionalHeaderColumns = new LinkedList<HeaderColumnSI>();
		rowMarkerImages = new LinkedList<String>();
		vScrollbar = null;
		hScrollbar = null;
		width = widthInPx;
		height = heightInPx;
	}

	public LocatorType getLocatorType() {
		return locatorType;
	}

	public UIObjectType getUiObjectType() {
		return uiObjectType;
	}

	public String getDisplayName() {
		return displayName;
	}

	/**
	 * It should contain the whole image to cover key header column height and
	 * width.
	 * 
	 * @param objects
	 * @return
	 */
	public DataGridSI addKeyHeaderColumns(HeaderColumnSI... objects) {
		if (objects != null && objects.length > 0) {
			keyHeaderColumns.addAll(Arrays.asList(objects));
		}
		return this;
	}

	public List<HeaderColumnSI> getKeyHeaderColumns() {
		return keyHeaderColumns;
	}

	public HeaderColumnSI getKeyHeaderColumn(String columnDisplayName) {
		for (HeaderColumnSI colObj : keyHeaderColumns) {
			if (columnDisplayName.equals(colObj.getDisplayName())) {
				return colObj;
			}
		}
		return null;
	}

	/**
	 * It should contain the whole image to cover additional header column height
	 * and width.
	 * 
	 * @param objects
	 * @return
	 */
	public DataGridSI addAdditionalHeaderColumns(HeaderColumnSI... objects) {
		if (objects != null && objects.length > 0) {
			additionalHeaderColumns.addAll(Arrays.asList(objects));
		}
		return this;
	}

	public List<HeaderColumnSI> getAdditionalHeaderColumns() {
		return additionalHeaderColumns;
	}

	public HeaderColumnSI getAdditionalHeaderColumn(String columnDisplayName) {
		for (HeaderColumnSI colObj : additionalHeaderColumns) {
			if (columnDisplayName.equals(colObj.getDisplayName())) {
				return colObj;
			}
		}
		return null;
	}

	/**
	 * you can just specify the first column left border + row border combination as
	 * row marker.
	 * 
	 * @param images
	 * @return
	 */
	public DataGridSI addRowMarkerImages(String... images) {
		if (images != null && images.length > 0) {
			rowMarkerImages.addAll(Arrays.asList(images));
		}
		return this;
	}

	public List<String> getRowMarkerImages() {
		return rowMarkerImages;
	}

	public int getNumLeftFrozenColumns() {
		return numLeftFrozenColumns;
	}

	public DataGridSI setNumLeftFrozenColumns(int numLeftFrozenColumns) {
		this.numLeftFrozenColumns = numLeftFrozenColumns;
		return this;
	}

	public int getNumRightFrozenColumns() {
		return numRightFrozenColumns;
	}

	public DataGridSI setNumRightFrozenColumns(int numRightFrozenColumns) {
		this.numRightFrozenColumns = numRightFrozenColumns;
		return this;
	}

	public VerticalScrollbar getVScrollbar() {
		return vScrollbar;
	}

	public DataGridSI setVScrollbar(VerticalScrollbar vScrollbar) {
		this.vScrollbar = vScrollbar;
		return this;
	}

	public HorizontalScrollbar getHScrollbar() {
		return hScrollbar;
	}

	public DataGridSI setHScrollbar(HorizontalScrollbar hScrollbar) {
		this.hScrollbar = hScrollbar;
		return this;
	}

	public int getWidth() {
		return width;
	}

	public DataGridSI setWidth(int width) {
		this.width = width;
		return this;
	}

	public int getHeight() {
		return height;
	}

	public DataGridSI setHeight(int height) {
		this.height = height;
		return this;
	}

	public DataGridValidatorSI getValidator(SmartAppDriver appDriver, Region region) {
		return new DataGridValidatorSI(appDriver, this, region);
	}

	public DataGridValidatorSI getValidator(SmartCucumberScenarioContext scenarioContext, Region region) {
		return getValidator(scenarioContext.getActiveAppDriver(), region);
	}

}
