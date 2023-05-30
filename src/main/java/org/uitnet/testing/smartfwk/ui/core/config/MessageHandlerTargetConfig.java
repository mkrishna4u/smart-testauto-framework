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

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MessageHandlerTargetConfig {
	private String name;
	private String hostOrIpAddress;
	private String actionHandlerClass;
	private MessageSenderTargetSettings senderTargetSettings;
	private MessageReceiverTargetSettings receiverTargetSettings;
	private Map<String, Object> additionalProps;
	
	public MessageHandlerTargetConfig() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostOrIpAddress() {
		return hostOrIpAddress;
	}

	public void setHostOrIpAddress(String hostOrIpAddress) {
		this.hostOrIpAddress = hostOrIpAddress;
	}

	public String getActionHandlerClass() {
		return actionHandlerClass;
	}

	public void setActionHandlerClass(String actionHandlerClass) {
		this.actionHandlerClass = actionHandlerClass;
	}

	public MessageSenderTargetSettings getSenderTargetSettings() {
		return senderTargetSettings;
	}

	public void setSenderTargetSettings(MessageSenderTargetSettings senderTargetSettings) {
		this.senderTargetSettings = senderTargetSettings;
	}

	public MessageReceiverTargetSettings getReceiverTargetSettings() {
		return receiverTargetSettings;
	}

	public void setReceiverTargetSettings(MessageReceiverTargetSettings receiverTargetSettings) {
		this.receiverTargetSettings = receiverTargetSettings;
	}

	public Map<String, Object> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		this.additionalProps = additionalProps;
	}
}
