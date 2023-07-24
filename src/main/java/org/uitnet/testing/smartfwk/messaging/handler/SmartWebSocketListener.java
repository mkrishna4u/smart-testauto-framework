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
package org.uitnet.testing.smartfwk.messaging.handler;

import java.util.Calendar;

import org.uitnet.testing.smartfwk.messaging.MessageContentType;
import org.uitnet.testing.smartfwk.messaging.MessageInfo;
import org.uitnet.testing.smartfwk.ui.core.config.MessageHandlerTargetConfig;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartWebSocketListener extends WebSocketListener {
    protected static final int NORMAL_CLOSURE_STATUS = 1000;
    protected SmartWebSocketMessageHandler messageHandler;
    
    public SmartWebSocketListener(SmartWebSocketMessageHandler messageHandler) {
    	this.messageHandler = messageHandler;
    }
    
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Websocket connection established for '" + messageHandler.getMessageHandlerTargetConfig().getName() + "' message handler.");
    }
 
    @Override
    public void onMessage(WebSocket webSocket, String text) {
    	MessageHandlerTargetConfig config = messageHandler.getMessageHandlerTargetConfig();
    	MessageInfo messageInfo = new MessageInfo();
    	messageInfo.setMessage(text);
    	messageInfo.setContentType(config.getReceiverTargetSettings().getContentType());
    	messageInfo.setOtherContentType(config.getReceiverTargetSettings().getOtherContentType());
    	messageInfo.setLogTime(Calendar.getInstance().getTimeInMillis());
    	
    	messageHandler.notifyMessageReceived(messageInfo);
    }
 
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
    	MessageHandlerTargetConfig config = messageHandler.getMessageHandlerTargetConfig();
    	MessageInfo messageInfo = new MessageInfo();
    	if(bytes != null) {
    		messageInfo.setMessage(bytes.toByteArray());
    	}
    	messageInfo.setContentType(config.getReceiverTargetSettings().getContentType());
    	messageInfo.setOtherContentType(config.getReceiverTargetSettings().getOtherContentType());
    	messageInfo.setLogTime(Calendar.getInstance().getTimeInMillis());
    	
    	messageHandler.notifyMessageReceived(messageInfo);
    }
 
    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        System.out.println("Websocket connection closed for '" + messageHandler.getMessageHandlerTargetConfig().getName() + "' message handler.");
    }
 
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }
 
}
