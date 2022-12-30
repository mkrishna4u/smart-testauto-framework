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
package org.uitnet.testing.smartfwk.ui.core.commons;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Madhav Krishna
 *
 * @param <T>
 */
public class ItemList<T> {
	private List<T> list;

	public ItemList() {
		list = new LinkedList<T>();
	}

	public ItemList(List<T> items) {
		list = new ArrayList<T>();
		if(items != null) {
			list.addAll(items);
		}
	}

	public ItemList<T> add(T item) {
		list.add(item);
		return this;
	}

	public ItemList<T> remove(T item) {
		list.remove(item);
		return this;
	}

	public ItemList<T> removeAll(Collection<T> items) {
		list.removeAll(items);
		return this;
	}
	
	public T getItem(int index) {
		return list.get(index);
	}

	public int size() {
		return list.size();
	}

	public List<T> getItems() {
		return list;
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
