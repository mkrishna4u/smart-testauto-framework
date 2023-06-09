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
package org.uitnet.testing.smartfwk.messaging.handler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.uitnet.testing.smartfwk.messaging.AbstractMessageHandler;
import org.uitnet.testing.smartfwk.messaging.MessageContentType;
import org.uitnet.testing.smartfwk.ui.core.config.MessageHandlerTargetConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Used to send and receive messages on web sockets.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartWebSocketMessageHandler extends AbstractMessageHandler {
	protected static final int NORMAL_CLOSURE_STATUS = 1000;
	protected OkHttpClient httpClient;
	protected WebSocket webSocket;

	public SmartWebSocketMessageHandler(MessageHandlerTargetConfig messageHandlerTargetConfig) {
		super(messageHandlerTargetConfig);
	}

	@Override
	public void sendMessage(Object message, MessageContentType contentType, String otherContentType) throws Exception {
		if(contentType == MessageContentType.BYTE_ARRAY || contentType == MessageContentType.OTHER) {
			ByteString bs = new ByteString((byte[])message);
			webSocket.send(bs);
		} if(contentType == MessageContentType.FILE) {
			byte[] fbytes = Files.readAllBytes(Path.of("" + message));
			ByteString bs = new ByteString(fbytes);
			webSocket.send(bs);
		} else {
			webSocket.send("" + message);
		}
		
		System.out.println("Message sent using '" + messageHandlerTargetConfig.getName() + "' message handler.");
	}

	@Override
	public void connectToSender(MessageHandlerTargetConfig messageHandlerTargetConfig) throws Exception {
		httpClient = new OkHttpClient();
		SmartWebSocketListener listener = new SmartWebSocketListener(this);
		Request.Builder requestBuilder = new Request.Builder();
		requestBuilder.url(messageHandlerTargetConfig.getUrl());
			
		Map<String, Object> props = messageHandlerTargetConfig.getAdditionalProps();
		if("basic".equalsIgnoreCase("" + props.get("authenticateMechanism"))) {
			Request.Builder authRequestBuilder = new Request.Builder();
			
			
			Map<String, Object> authParams  = (Map<String, Object>) props.get("authenticationParams");
			String url = "" +  authParams.get("url");
			authRequestBuilder.url(url);
			
			String method = "" +  authParams.get("method");
			String body = "" +  authParams.get("body");
			
			
			authRequestBuilder.method(body, null);
			
			Map<String, String> headers = (Map<String, String>) authParams.get("headers");
			if(headers != null && headers.size() > 0) {
				for(Map.Entry<String, String> header : headers.entrySet()) {
					authRequestBuilder.addHeader(header.getKey(), "" + header.getValue());
				}
			}
			
			httpClient.newCall(authRequestBuilder.build());
			
		}
		
		webSocket = httpClient.newWebSocket(requestBuilder.build(), listener);
		
		System.out.println("'" + messageHandlerTargetConfig.getName() + "' message handler connection is established.");
	}

	@Override
	public void startReceiver(MessageHandlerTargetConfig messageHandlerTargetConfig) throws Exception {
		// Do not do anything.
	}

	@Override
	public void disconnect() {
		if(webSocket != null) {
			webSocket.close(NORMAL_CLOSURE_STATUS, "Closed");
			webSocket = null;
		}
		
	}

}
