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

import java.util.Map;

import org.uitnet.testing.smartfwk.messaging.MessageContentType;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MessageReceiverTargetSettings {
	private Integer listenerPort;
	private String topicName;
	private String queueName;
	private MessageContentType contentType = MessageContentType.STRING;
	private String otherContentType;
	private Map<String, Object> additionalProps;
	
	public MessageReceiverTargetSettings() {
		
	}

	public Integer getListenerPort() {
		return listenerPort;
	}

	public void setListenerPort(Integer listenerPort) {
		this.listenerPort = listenerPort;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public MessageContentType getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		if(StringUtil.isEmptyAfterTrim(contentType)) { return; }
		this.contentType = MessageContentType.valueOf2(contentType);
	}

	public String getOtherContentType() {
		return otherContentType;
	}

	public void setOtherContentType(String otherContentType) {
		this.otherContentType = otherContentType;
	}

	public Map<String, Object> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		this.additionalProps = additionalProps;
	}
}
