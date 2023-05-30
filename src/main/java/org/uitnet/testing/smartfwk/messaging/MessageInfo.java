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
package org.uitnet.testing.smartfwk.messaging;

import java.util.Calendar;

import org.uitnet.testing.smartfwk.api.core.support.MediaType;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class MessageInfo {
	private long logTime;
	private String hostNameOrIpAddress;
	private int port;
	private MediaType contentType;
	private String fileName;
	private Object message;
	
	public MessageInfo() {
		logTime = Calendar.getInstance().getTimeInMillis();
	}

	public long getLogTime() {
		return logTime;
	}

	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}

	public String getHostNameOrIpAddress() {
		return hostNameOrIpAddress;
	}

	public void setHostNameOrIpAddress(String hostNameOrIpAddress) {
		this.hostNameOrIpAddress = hostNameOrIpAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public MediaType getContentType() {
		return contentType;
	}

	public void setContentType(MediaType contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
}
