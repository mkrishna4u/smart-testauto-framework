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
package org.uitnet.testing.smartfwk.ui.core.commons;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @author Madhav Krishna
 *
 * @param <T>
 */
public class ItemSet<T> {
	private Set<T> list;

	public ItemSet() {
		list = new LinkedHashSet<T>();
	}

	public ItemSet<T> add(T item) {
		list.add(item);
		return this;
	}

	public ItemSet<T> remove(T item) {
		list.remove(item);
		return this;
	}

	public int size() {
		return list.size();
	}

	public Set<T> getItems() {
		return list;
	}

	@Override
	public String toString() {
		return list.toString();
	}
}
