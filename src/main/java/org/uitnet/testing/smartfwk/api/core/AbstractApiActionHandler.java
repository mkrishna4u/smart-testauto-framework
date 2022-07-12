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
package org.uitnet.testing.smartfwk.api.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.defaults.ApiTestManager;
import org.uitnet.testing.smartfwk.api.core.support.FileSequenceNumberGenerator;
import org.uitnet.testing.smartfwk.api.core.support.HttpMultipartRequest;
import org.uitnet.testing.smartfwk.api.core.support.HttpRequest;
import org.uitnet.testing.smartfwk.api.core.support.HttpResponse;
import org.uitnet.testing.smartfwk.api.core.support.HttpSession;
import org.uitnet.testing.smartfwk.api.core.support.MultipartData;
import org.uitnet.testing.smartfwk.api.core.support.PayloadType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.config.UserProfile;
import org.uitnet.testing.smartfwk.ui.core.utils.MimeTypeUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AbstractApiActionHandler implements ApiAuthenticationProvider {
	protected String appName;
	protected String baseURL;
	protected HttpSession session;
	protected String activeProfileName;
	protected UserProfile activeUserProfile;
	protected int sessionExpiryDurationInSeconds;
	protected long lastRequestAccessTimeInMs;
	protected boolean logoutRequest = false;
	protected ApiTestManager apiTestManager;
	protected String targetServerName;

	public AbstractApiActionHandler(String appName, int sessionExpiryDurationInSeconds, String targetServerName) {
		this.appName = appName;
		this.sessionExpiryDurationInSeconds = sessionExpiryDurationInSeconds;
		this.targetServerName = targetServerName;
		this.baseURL = TestConfigManager.getInstance().getAppConfig(appName).getApiConfig()
				.getTargetServer(targetServerName).getBaseURL();
	}

	public void setApiTestManager(ApiTestManager apiTestManager) {
		this.apiTestManager = apiTestManager;
	}

//	protected void setBaseURL() {	
//		if(baseURL != null) {
//			baseURL = TestConfigManager.getInstance().getAppConfig(appName).getApiConfig().getTargetServer(targetServerName)
//					.getBaseURL();
//		}
//	}

	public HttpSession setActiveProfileName(String profileName) {
		if (activeProfileName == null || "".equals(activeProfileName)) {
			authenticate(profileName);
			activeProfileName = profileName;
			activeUserProfile = TestConfigManager.getInstance().getUserProfile(appName, profileName);
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();

		} else if (!activeProfileName.equals(profileName)) {
			if (apiTestManager == null) {
				logout();
			}
			authenticate(profileName);
			activeProfileName = profileName;
			activeUserProfile = TestConfigManager.getInstance().getUserProfile(appName, profileName);
			lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
		}

		return session;
	}

	protected void authenticate(String profileName) {
		if (apiTestManager != null) {
			ApiAuthenticationProvider authProvider = apiTestManager.getAuthenticationProvider(appName, targetServerName,
					profileName);
			session = authProvider.login(TestConfigManager.getInstance().getAppConfig(appName).getApiConfig(),
					TestConfigManager.getInstance().getUserProfile(appName, profileName));
		} else {
			session = login(TestConfigManager.getInstance().getAppConfig(appName).getApiConfig(),
					TestConfigManager.getInstance().getUserProfile(appName, profileName));
		}
	}

	public String getActiveProfileName() {
		return activeProfileName;
	}

	public HttpResponse httpGet(String relativeUrl, String responseContentType, Integer connectTimeoutInSeconds,
			Integer readTimeoutInSeconds) {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(readTimeoutInSeconds == null ? 60 : readTimeoutInSeconds, TimeUnit.SECONDS)
				.connectTimeout(connectTimeoutInSeconds == null ? 30 : connectTimeoutInSeconds, TimeUnit.SECONDS)
				.build();

		String targetURL = baseURL + "/" + relativeUrl;
		okhttp3.Request.Builder requestBuilder = new Request.Builder().get().url(targetURL);

		// Add headers
		if (session != null && session.getParams() != null && session.getParams().size() > 0) {
			for (Map.Entry<String, String> kv : session.getParams().entrySet()) {
				requestBuilder.addHeader(kv.getKey(), kv.getValue());
			}
		}

		// Add cookies
		if (session != null && session.getCookies() != null && session.getCookies().size() > 0) {
			String cookie = null;
			for (Map.Entry<String, String> kv : session.getCookies().entrySet()) {
				if (cookie == null) {
					cookie = kv.getKey() + "=" + kv.getValue();
				} else {
					cookie = kv + ";" + kv.getKey() + "=" + kv.getValue();
				}
			}

			if (cookie != null) {
				requestBuilder.addHeader("Cookie", cookie);
			}
		}

		requestBuilder.removeHeader("Content-Type");
		if (responseContentType == null || "".equals(responseContentType.trim())) {
			requestBuilder.removeHeader("Accept");
		} else {
			requestBuilder.addHeader("Accept", responseContentType);
		}

		return prepareResponse(client, requestBuilder, responseContentType != null, targetURL);
	}

	public HttpResponse httpDelete(String relativeUrl, String responseContentType, Integer connectTimeoutInSeconds,
			Integer readTimeoutInSeconds) {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(readTimeoutInSeconds == null ? 60 : readTimeoutInSeconds, TimeUnit.SECONDS)
				.connectTimeout(connectTimeoutInSeconds == null ? 30 : connectTimeoutInSeconds, TimeUnit.SECONDS)
				.build();

		String targetURL = baseURL + "/" + relativeUrl;
		okhttp3.Request.Builder requestBuilder = new Request.Builder().delete().url(targetURL);

		// Add headers
		if (session != null && session.getParams() != null && session.getParams().size() > 0) {
			for (Map.Entry<String, String> kv : session.getParams().entrySet()) {
				requestBuilder.addHeader(kv.getKey(), kv.getValue());
			}
		}

		// Add cookies
		if (session != null && session.getCookies() != null && session.getCookies().size() > 0) {
			String cookie = null;
			for (Map.Entry<String, String> kv : session.getCookies().entrySet()) {
				if (cookie == null) {
					cookie = kv.getKey() + "=" + kv.getValue();
				} else {
					cookie = kv + ";" + kv.getKey() + "=" + kv.getValue();
				}
			}

			if (cookie != null) {
				requestBuilder.addHeader("Cookie", cookie);
			}
		}

		requestBuilder.removeHeader("Content-Type");
		if (responseContentType == null || "".equals(responseContentType.trim())) {
			requestBuilder.removeHeader("Accept");
		} else {
			requestBuilder.addHeader("Accept", responseContentType);
		}

		return prepareResponse(client, requestBuilder, responseContentType != null, targetURL);
	}

	public HttpResponse httpPost(String relativeUrl, HttpRequest request, Integer connectTimeoutInSeconds,
			Integer readTimeoutInSeconds) {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(readTimeoutInSeconds == null ? 60 : readTimeoutInSeconds, TimeUnit.SECONDS)
				.connectTimeout(connectTimeoutInSeconds == null ? 30 : connectTimeoutInSeconds, TimeUnit.SECONDS)
				.build();

		String targetURL = baseURL + "/" + relativeUrl;
		okhttp3.Request.Builder requestBuilder = new Request.Builder()
				.post(RequestBody.create(request.getPayload(), MediaType.parse(request.getPayloadType())))
				.url(targetURL);

		// Add headers
		if (session != null && session.getParams() != null && session.getParams().size() > 0) {
			for (Map.Entry<String, String> kv : session.getParams().entrySet()) {
				requestBuilder.addHeader(kv.getKey(), kv.getValue());
			}
		}

		// Add cookies
		if (session != null && session.getCookies() != null && session.getCookies().size() > 0) {
			String cookie = null;
			for (Map.Entry<String, String> kv : session.getCookies().entrySet()) {
				if (cookie == null) {
					cookie = kv.getKey() + "=" + kv.getValue();
				} else {
					cookie = kv + ";" + kv.getKey() + "=" + kv.getValue();
				}
			}

			if (cookie != null) {
				requestBuilder.addHeader("Cookie", cookie);
			}
		}

		if (request.getResponseContentType() == null || "".equals(request.getResponseContentType().trim())) {
			requestBuilder.removeHeader("Accept");
		} else {
			requestBuilder.addHeader("Accept", request.getResponseContentType());
		}

		return prepareResponse(client, requestBuilder, request.getResponseContentType() != null, targetURL);
	}

	public HttpResponse httpPut(String relativeUrl, HttpRequest request, Integer connectTimeoutInSeconds,
			Integer readTimeoutInSeconds) {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(readTimeoutInSeconds == null ? 60 : readTimeoutInSeconds, TimeUnit.SECONDS)
				.connectTimeout(connectTimeoutInSeconds == null ? 30 : connectTimeoutInSeconds, TimeUnit.SECONDS)
				.build();

		String targetURL = baseURL + "/" + relativeUrl;
		okhttp3.Request.Builder requestBuilder = new Request.Builder()
				.put(RequestBody.create(request.getPayload(), MediaType.parse(request.getPayloadType())))
				.url(targetURL);

		// Add headers
		if (session != null && session.getParams() != null && session.getParams().size() > 0) {
			for (Map.Entry<String, String> kv : session.getParams().entrySet()) {
				requestBuilder.addHeader(kv.getKey(), kv.getValue());
			}
		}

		// Add cookies
		if (session != null && session.getCookies() != null && session.getCookies().size() > 0) {
			String cookie = null;
			for (Map.Entry<String, String> kv : session.getCookies().entrySet()) {
				if (cookie == null) {
					cookie = kv.getKey() + "=" + kv.getValue();
				} else {
					cookie = kv + ";" + kv.getKey() + "=" + kv.getValue();
				}
			}

			if (cookie != null) {
				requestBuilder.addHeader("Cookie", cookie);
			}
		}

		if (request.getResponseContentType() == null || "".equals(request.getResponseContentType().trim())) {
			requestBuilder.removeHeader("Accept");
		} else {
			requestBuilder.addHeader("Accept", request.getResponseContentType());
		}

		return prepareResponse(client, requestBuilder, request.getResponseContentType() != null, targetURL);
	}

	public HttpResponse httpUploadFormFiles(String relativeUrl, HttpMultipartRequest request,
			Integer connectTimeoutInSeconds, Integer readTimeoutInSeconds) {
		OkHttpClient client = new OkHttpClient.Builder()
				.readTimeout(readTimeoutInSeconds == null ? 60 : readTimeoutInSeconds, TimeUnit.SECONDS)
				.connectTimeout(connectTimeoutInSeconds == null ? 30 : connectTimeoutInSeconds, TimeUnit.SECONDS)
				.build();

		okhttp3.MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

		for (MultipartData part : request.getParts()) {
			if (part.getContentType() == null) {
				multipartBodyBuilder.addFormDataPart(part.getName(), part.getFileName());
			} else {
				multipartBodyBuilder.addFormDataPart(part.getName(), part.getFileName(),
						RequestBody.create(new File(part.getFilePath()), MediaType.parse(part.getContentType())));
			}
		}

		String targetURL = baseURL + "/" + relativeUrl;
		okhttp3.Request.Builder requestBuilder = new Request.Builder().post(multipartBodyBuilder.build())
				.url(targetURL);

		// Add headers
		if (session != null && session.getParams() != null && session.getParams().size() > 0) {
			for (Map.Entry<String, String> kv : session.getParams().entrySet()) {
				requestBuilder.addHeader(kv.getKey(), kv.getValue());
			}
		}

		// Add cookies
		if (session != null && session.getCookies() != null && session.getCookies().size() > 0) {
			String cookie = null;
			for (Map.Entry<String, String> kv : session.getCookies().entrySet()) {
				if (cookie == null) {
					cookie = kv.getKey() + "=" + kv.getValue();
				} else {
					cookie = kv + ";" + kv.getKey() + "=" + kv.getValue();
				}
			}

			if (cookie != null) {
				requestBuilder.addHeader("Cookie", cookie);
			}
		}

		if (request.getResponseContentType() == null || "".equals(request.getResponseContentType().trim())) {
			requestBuilder.removeHeader("Accept");
		} else {
			requestBuilder.addHeader("Accept", request.getResponseContentType());
		}

		requestBuilder.addHeader("Content-Type", request.getContentType());

		return prepareResponse(client, requestBuilder, request.getResponseContentType() != null, targetURL);
	}

	protected HttpResponse prepareResponse(OkHttpClient client, okhttp3.Request.Builder requestBuilder,
			boolean expectResponseBody, String targetURL) {
		if (session != null && !logoutRequest) {
			if (isSessionExpired()) {
				logout();
				setActiveProfileName(activeProfileName);
			} else {
				lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
			}
		}

		HttpResponse httpResponse = new HttpResponse();
		try (Response response = client.newCall(requestBuilder.build()).execute()) {
			httpResponse.setCode(response.code());
			httpResponse.setMessage(response.message());

			Headers headers = response.headers();
			for (String key : headers.names()) {
				httpResponse.addHeader(key, headers.get(key));
			}

			String contentType = httpResponse.getHeader("Content-Type");
			String contentDisposition = httpResponse.getHeader("Content-Disposition");

			String binFileExtn = MimeTypeUtil.getBinaryFileExtension(contentType);

			if (contentDisposition != null && (contentDisposition.toLowerCase().contains("attachment")
					|| contentDisposition.toLowerCase().contains("inline"))) {
				handleContentDisposition(httpResponse, response);
			} else if (binFileExtn != null) {
				// handle binary data
				httpResponse.setPayLoadType(PayloadType.FILE);
				int nextSeq = FileSequenceNumberGenerator.getInstance().next();
				String filePath = TestConfigManager.getInstance().getDownloadLocation() + File.separator + "noname"
						+ nextSeq + binFileExtn;
				httpResponse.setPayload("noname" + binFileExtn);
				httpResponse.setFilePath(filePath);
				downloadFile(response.body(), filePath);
			} else {
				if (expectResponseBody) {
					ResponseBody body = response.body();
					httpResponse.setPayload(body.string());
				}
			}

		} catch (Exception ex) {
			httpResponse.setCode(400);
			httpResponse.setMessage("Bad Request");
			Assert.fail("Failed to make API call on target URL: " + targetURL, ex);
		} finally {
			if (logoutRequest) {
				logoutRequest = false;
				session = null;
			}
		}

		return httpResponse;
	}

	protected boolean isSessionExpired() {
		long currTimeInMs = Calendar.getInstance().getTimeInMillis();
		long durationInSeconds = (currTimeInMs - lastRequestAccessTimeInMs) / 1000;
		if (durationInSeconds >= sessionExpiryDurationInSeconds) {
			return true;
		}
		return false;
	}

	public UserProfile getActiveUserProfile() {
		return activeUserProfile;
	}

	public int getSessionExpiryDurationInSeconds() {
		return sessionExpiryDurationInSeconds;
	}

	public long getLastRequestAccessTimeInMs() {
		return lastRequestAccessTimeInMs;
	}

	protected void handleContentDisposition(HttpResponse httpResponse, Response rawResponse) throws IOException {
		String contentDisposition = httpResponse.getHeader("Content-Disposition");
		if (contentDisposition != null) {
			if (contentDisposition.toLowerCase().contains("attachment")
					|| contentDisposition.toLowerCase().contains("inline")) {
				httpResponse.setPayLoadType(PayloadType.FILE);
				String[] parts = contentDisposition.split(";");
				String fileToCreate = "noname";
				for (String part : parts) {
					if (part.trim().contains("filename")) {
						String[] fileParts = part.trim().split("=");
						if (fileParts.length == 2) {
							String fileName = fileParts[1].trim();
							fileToCreate = fileName.replace("\"", "").replace("'", "");
							break;
						}
					}
				}

				httpResponse.setPayload(fileToCreate);

				String filePath = TestConfigManager.getInstance().getDownloadLocation() + File.separator;

				int lastIndex = fileToCreate.lastIndexOf(".");
				int nextSeq = FileSequenceNumberGenerator.getInstance().next();
				if (lastIndex == 0) {
					filePath = filePath + nextSeq + fileToCreate;
				} else if (lastIndex > 0) {
					filePath = fileToCreate.substring(0, lastIndex) + "-" + nextSeq
							+ fileToCreate.substring(lastIndex, fileToCreate.length());
				} else {
					filePath = filePath + fileToCreate + nextSeq;
				}

				httpResponse.setFilePath(filePath);

				downloadFile(rawResponse.body(), filePath);
			}
		}
	}

	protected void downloadFile(ResponseBody body, String filePath) throws IOException {
		if (body != null) {
			Files.copy(body.byteStream(), new File(filePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
	}

	public AbstractApiActionHandler clone() {
		try {
			AbstractApiActionHandler obj = (AbstractApiActionHandler) ObjectUtil
					.findClassConstructor(this.getClass(), new Class[] { String.class, int.class, String.class })
					.newInstance(this.appName, this.sessionExpiryDurationInSeconds, this.targetServerName);
			return obj;
		} catch (Exception ex) {
			Assert.fail("Failed to clone '" + this.getClass().getName() + "' class object.", ex);
		}
		return null;
	}
}
