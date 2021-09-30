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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 * @param <K, V>
 */
public class ItemMap<K, V> {
	private LinkedHashMap<K, V> map;

	public ItemMap() {
		map = new LinkedHashMap<K, V>();
	}

	public ItemMap(LinkedHashMap<K, V> items) {
		map = new LinkedHashMap<K, V>();
		map.putAll(items);
	}

	public ItemMap<K, V> put(K key, V value) {
		map.put(key, value);
		return this;
	}

	public ItemMap<K, V> remove(K item) {
		map.remove(item);
		return this;
	}

	public ItemMap<K, V> removeAll(Map<K, V> items) {
		for (K key : items.keySet()) {
			remove(key);
		}
		return this;
	}

	public int size() {
		return map.size();
	}

	public Map<K, V> getItems() {
		return map;
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
