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
package org.uitnet.testing.smartfwk.ui.core.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.SubmissionPublisher;

import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;

import com.google.common.collect.ImmutableMap;

/**
 * Abstract SmartCache class that can be used to store the data and can be
 * retrieved at any time. Also you can use events to inform subscriber for the
 * change of cached data. NOTE: To use this cache, create a singleton class that
 * extends this cache or you can use the default implementation
 * DefaultSmartCache.
 * 
 * @author Madhav Krishna
 *
 */
public abstract class SmartCache {
	private Map<String, Object> cache;
	private SubmissionPublisher<SmartCache> publisher;

	public SmartCache() {
		cache = new TreeMap<>(Collections.reverseOrder());
		publisher = new SubmissionPublisher<>();
	}

	public void add(String key, Object value) {
		cache.put(key, value);
	}

	/**
	 * This returns the key value.If key does not present then return null.
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		if(key == null) { return null; }
		return cache.get(key);
	}
	
	public ImmutableMap<String, Object> getCache() {
		return ImmutableMap.copyOf(cache);
	}
	
	/**
	 * This returns the key value. If key does not present or key value is null then it will return key as value.
	 * @param key
	 * @return
	 */
	public Object getNullAsKey(String key) {
		if(key == null) { return null; }
		Object v = cache.get(key);
		if(v == null) {
			return key;
		}
		return v;
	}

	public Object removeAndGet(String key) {
		if(key == null) { return null; }
		return cache.remove(key);
	}
	
	public Object removeAndGetNullAsKey(String key) {
		if(key == null) { return null; }
		Object v = cache.remove(key);
		if(v == null) {
			return key;
		}
		return v;
	}
	
	public String getValueAsString(String key) {
		if(key == null) { return null; }
		return ObjectUtil.valueAsString(cache.get(key));
	}
	
	public Integer getValueAsInteger(String key) {
		if(key == null) { return null; }
		return ObjectUtil.valueAsInteger(cache.get(key));
	}
	
	public Long getValueAsLong(String key) {
		if(key == null) { return null; }
		return ObjectUtil.valueAsLong(cache.get(key));
	}
	
	public Double getValueAsDouble(String key) {
		if(key == null) { return null; }
		return ObjectUtil.valueAsDouble(cache.get(key));
	}
	
	public Boolean getValueAsBoolean(String key) {
		if(key == null) { return null; }
		return ObjectUtil.valueAsBoolean(cache.get(key));
	}
	
	/**
	 * MultiValue params are like Array, List, Set
	 * @param key
	 * @param delimitter          - could be , or any string, if null then it will
	 *                            use default as ,
	 * @param valueEnclosingChars like ' or " or empty/null (denotes no enclosing)
	 * @return
	 */
	public String getMultiValueKeyValueAsString(String key, String delimitter, String valueEnclosingChars) {
		if(key == null) { return null; }
		return ObjectUtil.listSetArrayValueAsString(cache.get(key), delimitter, valueEnclosingChars);
	}

	public Map<String, Object> getAll() {
		return cache;
	}

	public void clear() {
		cache.clear();
	}

	public Set<String> getKeysStartsWith(String startsWithStr) {
		Set<String> keys = new HashSet<>();
		for (String k : cache.keySet()) {
			if (k.startsWith(startsWithStr)) {
				keys.add(k);
			}
		}
		return keys;
	}

	public Set<String> getKeysEndsWith(String endsWithStr) {
		Set<String> keys = new HashSet<>();
		for (String k : cache.keySet()) {
			if (k.endsWith(endsWithStr)) {
				keys.add(k);
			}
		}
		return keys;
	}
	
	public Map<String, Object> getEntriesForKeyesEndsWithText(String text) {
		Map<String, Object> fparams = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1 == null && o2 == null) { return 0; }
				if(o1 == null && o2 != null) { return o2.length(); }
				if(o1 != null && o2 == null) { return 0 - o1.length(); }
				return o2.length() - o1.length();
			}
		});
		
		for (Map.Entry<String, Object> k : cache.entrySet()) {
			if (k.getKey().endsWith(text)) {
				fparams.put(k.getKey(), k.getValue());
			}
		}
		return fparams;
	}
	
	public Map<String, Object> getEntriesForKeysStartsWithText(String text) {
		Map<String, Object> fparams = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if(o1 == null && o2 == null) { return 0; }
				if(o1 == null && o2 != null) { return o2.length(); }
				if(o1 != null && o2 == null) { return 0 - o1.length(); }
				return o2.length() - o1.length();
			}
		});
		
		for (Map.Entry<String, Object> k : cache.entrySet()) {
			if (k.getKey().startsWith(text)) {
				fparams.put(k.getKey(), k.getValue());
			}
		}
		return fparams;
	}

	public boolean isKeyPresent(String key) {
		return cache.get(key) != null;
	}

	public void subscribe(SmartCacheSubscriber subscriber) {
		publisher.subscribe(subscriber);
	}

	public void publish(SmartCache cache) {
		publisher.submit(cache);
	}
}
