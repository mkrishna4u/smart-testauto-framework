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
package org.uitnet.testing.smartfwk.ui.core.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MessageHandlersConfig {
	private List<MessageHandlerTargetConfig> targets;
	
	public MessageHandlersConfig() {
		// do nothing
	}

	@JsonIgnore
	public List<MessageHandlerTargetConfig> getTargets() {
		return targets;
	}

	public void setTargets(List<MessageHandlerTargetConfig> targets) {
		this.targets = targets;
	}
	
	public Map<String, MessageHandlerTargetConfig> getTargetsAsMap() {
		Map<String, MessageHandlerTargetConfig> targetsAsMap = new LinkedHashMap<>();
		if(targets == null || targets.size() == 0) {
			return targetsAsMap;
		}
		
		for(MessageHandlerTargetConfig target : targets) {
			if(StringUtil.isEmptyAfterTrim(target.getName())) {
				continue;
			}
			
			if(targetsAsMap.containsKey(target.getName())) {
				Assert.fail("Message handler name should be unique. Duplicate MessageHandler = " + target.getName() + ".");
			}
			targetsAsMap.put(target.getName(), target);
		}
		
		return targetsAsMap;
	}
}