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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.config.MessageHandlerTargetConfig;

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AbstractMessagingHandler implements MessageSender, MessageReceiver, MessagingConnectionProvider {
	// Key: bucketName, Value: messages in bucket
	protected Map<String, List<DocumentContext>> bucketMessagesMap;
	protected MessageHandlerTargetConfig messageHandlerTargetConfig;
	
	public AbstractMessagingHandler(MessageHandlerTargetConfig messageHandlerTargetConfig) {
		this.messageHandlerTargetConfig = messageHandlerTargetConfig;
		this.bucketMessagesMap = new ConcurrentHashMap<>();
	}
	
	public void start() {
		try {
			connectToSender(messageHandlerTargetConfig);
			startReceiver(messageHandlerTargetConfig);
		} catch(Exception ex) {
			Assert.fail("Failed to start '" + messageHandlerTargetConfig.getName() + "' messaging handler.", ex);
		}
	}
	
	public void startMessageRecorder(String bucketName) {
		List<DocumentContext> messages = new LinkedList<DocumentContext>();
		bucketMessagesMap.put(bucketName, messages);
	}
	
	public void stopMessageRecorder(String bucketName) {
		bucketMessagesMap.remove(bucketName);
	}
	
	public List<DocumentContext> getRecordedMessages(String bucketName) {
		return bucketMessagesMap.get(bucketName);
	}
	
	@Override
	public void notifyMessageReceived(MessageInfo message) {
		if(message == null) {
			return;
		}
		
		Set<String> keySet = bucketMessagesMap.keySet();
		if(keySet.size() > 0) {
			JsonDocumentReader jsonDocReader = new JsonDocumentReader();
			DocumentContext msgAsJson = jsonDocReader.prepareDocumentContext(message);
			List<DocumentContext> messageList = null;
			for(String key : keySet) {
				messageList = bucketMessagesMap.get(key);
				if(messageList == null) {
					continue;
				}
				messageList.add(msgAsJson);
			}
		}
	}
	
	public void close() {
		try {
			disconnect();
		} catch(Exception ex) {
			Assert.fail("Failed to disconnect '" + messageHandlerTargetConfig.getName() + "' message reciever.", ex);
		}
	}
}
