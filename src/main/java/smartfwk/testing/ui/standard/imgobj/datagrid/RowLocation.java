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
package smartfwk.testing.ui.standard.imgobj.datagrid;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RowLocation {
	protected Integer y1;
	protected Integer y2;
	
	public RowLocation(Integer y1, Integer y2) {
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public Integer getY1() {
		return y1;
	}

	public Integer getY2() {
		return y2;
	}
	
	public int getRowHeight() {
		if(y1 != null && y2 != null) {
			return (y2 - y1);
		}
		return 0;
	}
}
