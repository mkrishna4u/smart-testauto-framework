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
package org.uitnet.testing.smartfwk.messaging;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.config.MessageHandlerTargetConfig;
import org.uitnet.testing.smartfwk.ui.core.config.MessageHandlersConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * This class is used to register all message handlers.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartMessageHandlerManager implements MessageHandlerManager {
	private static SmartMessageHandlerManager instance;

	// Key: handlerName, Value: AbstractMessagingHandler
	private Map<String, AbstractMessageHandler> messageHandlers;

	// Key: handlerName, Value: MessageHandlerConnectionProvider
	private Map<String, MessageHandlerConnectionProvider> connectionProviders;

	private SmartMessageHandlerManager() {
		messageHandlers = new HashMap<>();
		connectionProviders = new HashMap<>();
		initMessageHandlers();
	}

	public static MessageHandlerManager getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (SmartMessageHandlerManager.class) {
			if (instance == null) {
				instance = new SmartMessageHandlerManager();
			}
		}

		return instance;
	}

	private void initMessageHandlers() {
		MessageHandlersConfig messageHandlersConfig = TestConfigManager.getInstance().getMessageHandlersConfig();
		if (messageHandlersConfig != null) {
			Collection<MessageHandlerTargetConfig> targets = messageHandlersConfig.getTargets();
			if(targets == null || targets.isEmpty()) {
				return;
			}
			
			for (MessageHandlerTargetConfig target : targets) {
				try {
					String clazzName = target.getMessageHandlerClass();
					if(StringUtil.isEmptyAfterTrim(clazzName)) {
						continue;
					}
					
					Class<?> clazz = Class.forName(clazzName);

					AbstractMessageHandler obj = (AbstractMessageHandler) ObjectUtil
							.findClassConstructor(clazz, new Class[] { MessageHandlerTargetConfig.class })
							.newInstance(target);
					obj.start();
					registerMessageHandler(target.getName(), obj);

				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}

			}
		}
	}

	/**
	 * Used to register new message handler.
	 * 
	 * @param appName
	 * @param remoteMachineName
	 * @param messageHandler
	 */
	private void registerMessageHandler(String handlerName, AbstractMessageHandler messageHandler) {
		messageHandler.setMessageHandlerManager(this);
		messageHandlers.put(prepareKey(handlerName), messageHandler);
	}

	@Override
	public AbstractMessageHandler getMessageHandler(String handlerName) {
		AbstractMessageHandler handler = getRegisteredMessageHandler(handlerName);
		return handler;
	}

	private AbstractMessageHandler getRegisteredMessageHandler(String handlerName) {
		AbstractMessageHandler messageHandler = messageHandlers.get(prepareKey(handlerName));
		Assert.assertNotNull(messageHandler,
				"No message handler registered with SmartMessageHandlerManager class with name = " + handlerName
						+ ". This must be registered in cucumber step definition method that is annotated with @BeforeAll.");

		return messageHandler;
	}

	@Override
	public MessageHandlerConnectionProvider getConnectionProvider(String handlerName) {
		String mapKey = prepareConnectionProviderMapKey(handlerName);
		MessageHandlerConnectionProvider connectionProvider = connectionProviders.get(mapKey);

		if (connectionProvider != null) {
			return connectionProvider;
		}

		synchronized (SmartMessageHandlerManager.class) {
			connectionProvider = connectionProviders.get(mapKey);
			if (connectionProvider != null) {
				return connectionProvider;
			}

			connectionProvider = getRegisteredMessageHandler(handlerName);
			connectionProviders.put(mapKey, connectionProvider);

			return connectionProvider;
		}
	}

	private String prepareConnectionProviderMapKey(String handlerName) {
		return handlerName;
	}

	private String prepareKey(String handlerName) {
		return handlerName;
	}

	@Override
	public synchronized void deregisterAll() {
		for (AbstractMessageHandler amh : messageHandlers.values()) {
			amh.close();
		}

		messageHandlers.clear();
		connectionProviders.clear();
	}
}
