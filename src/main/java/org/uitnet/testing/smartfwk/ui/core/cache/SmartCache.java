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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.SubmissionPublisher;

import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;

import io.cucumber.java.Scenario;

/**
 * Abstract SmartCache class that can be used to store the data and can be retrieved at any time.
 * Also you can use events to inform subscriber for the change of cached data.
 * NOTE: To use this cache, create a singleton class that extends this cache or you can use the 
 * default implementation DefaultSmartCache.
 * 
 * @author Madhav Krishna
 *
 */
public abstract class SmartCache {
	private Map<String, Object> cache;
	private AbstractAppConnector appConnector;
	private SmartAppDriver appDriver;
	private Scenario runningScenario;
	private SubmissionPublisher<SmartCache> publisher;
	
	public SmartCache() {
		cache = new HashMap<>();
		publisher = new SubmissionPublisher<>();
	}
	
	public <V> void add(String key, V value) {
		cache.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public <V> V get(String key) {
		return (V) cache.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <V> V removeAndget(String key) {
		return (V) cache.remove(key);
	}
	
	public Set<String> getKeysStartsWith(String startsWithStr) {
		Set<String> keys = new HashSet<>();
		for(String k : cache.keySet()) {
			if(k.startsWith(startsWithStr)) {
				keys.add(k);
			}
		}
		return keys;
	}
	
	public Set<String> getKeysEndsWith(String endsWithStr) {
		Set<String> keys = new HashSet<>();
		for(String k : cache.keySet()) {
			if(k.endsWith(endsWithStr)) {
				keys.add(k);
			}
		}
		return keys;
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
	
	public AbstractAppConnector getAppConnector() {
		return appConnector;
	}

	public void setAppConnector(AbstractAppConnector appConnector) {
		this.appConnector = appConnector;
	}

	public SmartAppDriver getAppDriver() {
		return appDriver;
	}

	public void setAppDriver(SmartAppDriver appDriver) {
		this.appDriver = appDriver;
	}

	public Scenario getRunningScenario() {
		return runningScenario;
	}

	public void setRunningScenario(Scenario runningScenario) {
		this.runningScenario = runningScenario;
	}
}
