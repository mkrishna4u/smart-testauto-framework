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
package org.uitnet.testing.smartfwk.remote_machine;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.collections.Lists;
import org.uitnet.testing.smartfwk.local_machine.LocalMachineFileSystem;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AbstractRemoteMachineActionHandler implements RemoteMachineConnectionProvider {
	protected RemoteMachineManager remoteMachineManager;
	protected String appName;
	protected String remoteMachineName;
	protected RemoteMachineConfig remoteMachineConfig;
	protected int sessionExpiryDurationInSeconds;
	protected RemoteMachineConnection connection;
	protected long lastRequestAccessTimeInMs;

	public AbstractRemoteMachineActionHandler(String remoteMachineName, String appName,
			int sessionExpiryDurationInSeconds, RemoteMachineConfig remoteMachineConfig) {
		this.remoteMachineName = remoteMachineName;
		this.appName = appName;
		this.sessionExpiryDurationInSeconds = sessionExpiryDurationInSeconds;
		this.remoteMachineConfig = remoteMachineConfig;
	}

	public void setRemoteMachineManager(RemoteMachineManager remoteMachineManager) {
		this.remoteMachineManager = remoteMachineManager;
	}

	public void connect() {
		connection = connect(remoteMachineConfig);
	}

	private synchronized void verifyConnection() {
		if (connection == null || isSessionExpired()) {
			disconnect();
			connection = connect(remoteMachineConfig);
		}

		lastRequestAccessTimeInMs = Calendar.getInstance().getTimeInMillis();
	}

	protected boolean isSessionExpired() {
		long currTimeInMs = Calendar.getInstance().getTimeInMillis();
		long durationInSeconds = (currTimeInMs - lastRequestAccessTimeInMs) / 1000;
		if (durationInSeconds >= sessionExpiryDurationInSeconds) {
			return true;
		}
		return false;
	}

	public List<String> getDirectoryList(String absolutePath, TextMatchMechanism fileNameMatchMechanism, String text) {
		String cmd = "cd " + absolutePath + " && find . -maxdepth 1 -type d";
		String result = executeCommand(cmd);
		return prepareFileList(result);
	}

	public List<String> getFileList(String absolutePath) {
		String cmd = "cd " + absolutePath + " && find . -maxdepth 1 ! -type d";
		String result = executeCommand(cmd);
		return prepareFileList(result);
	}

	public List<String> getFolderList(String absolutePath) {
		String cmd = "cd " + absolutePath + " && find . -maxdepth 1 -type d";
		String result = executeCommand(cmd);
		return prepareFileList(result);
	}

	public AbstractRemoteMachineActionHandler validateFileExists(String absolutePath,
			TextMatchMechanism fileNameMatchMechanism, String expectedValue) {
		List<String> files = getFileList(absolutePath);
		for (String file : files) {
			if (StringUtil.isTextMatchedWithExpectedValue(file, expectedValue, fileNameMatchMechanism)) {
				return this;
			}
		}
		Assert.fail("No file found on '" + absolutePath + "' location using TextMatchMechanism: "
				+ fileNameMatchMechanism + " that matches expected value '" + expectedValue + "'." + " AppName: "
				+ appName + ", TargetServerName: " + remoteMachineName);
		return this;
	}

	public AbstractRemoteMachineActionHandler validateFilesExist(String absolutePath, List<String> files) {
		List<String> remoteFiles = getFileList(absolutePath);
		List<String> newList = Lists.newArrayList(files);
		newList.removeAll(remoteFiles);
		if (newList.size() > 0) {
			Assert.fail("Following files are not found on '" + absolutePath + "' location" + "'." + " AppName: "
					+ appName + ", TargetServerName: " + remoteMachineName + ". Not found files: " + newList);
		}
		return this;
	}

	public AbstractRemoteMachineActionHandler validateFolderExists(String absolutePath,
			TextMatchMechanism fileNameMatchMechanism, String expectedValue) {
		List<String> files = getFolderList(absolutePath);
		for (String file : files) {
			if (StringUtil.isTextMatchedWithExpectedValue(file, expectedValue, fileNameMatchMechanism)) {
				return this;
			}
		}
		Assert.fail("No folder found on '" + absolutePath + "' location using TextMatchMechanism: "
				+ fileNameMatchMechanism + " that matches expected value '" + expectedValue + "'." + " AppName: "
				+ appName + ", TargetServerName: " + remoteMachineName);
		return this;
	}

	public AbstractRemoteMachineActionHandler validateFolderExist(String absolutePath, List<String> folders) {
		List<String> remoteFiles = getFolderList(absolutePath);
		List<String> newList = Lists.newArrayList(folders);
		newList.removeAll(remoteFiles);
		if (newList.size() > 0) {
			Assert.fail("Following folders are not found on '" + absolutePath + "' location" + "'." + " AppName: "
					+ appName + ", TargetServerName: " + remoteMachineName + ". Not found folders: " + newList);
		}
		return this;
	}

	public AbstractRemoteMachineActionHandler deleteFiles(String absolutePath,
			TextMatchMechanism fileNameMatchMechanism, String expectedValue) {
		List<String> files = getFileList(absolutePath);
		for (String file : files) {
			if (StringUtil.isEmptyAfterTrim(file)) {
				continue;
			}
			if (StringUtil.isTextMatchedWithExpectedValue(file, expectedValue, fileNameMatchMechanism)) {
				executeCommand("cd " + absolutePath + " && rm " + file);
			}
		}
		return this;
	}

	public List<String> downloadFiles(String absoluteRemotePath, TextMatchMechanism fileNameMatchMechanism,
			String expectedValue, String absoluteLocalPath) {
		List<String> files = getFileList(absoluteRemotePath);
		List<String> dowloadedFiles = new LinkedList<>();
		for (String file : files) {
			if (StringUtil.isEmptyAfterTrim(file)) {
				continue;
			}
			if (StringUtil.isTextMatchedWithExpectedValue(file, expectedValue, fileNameMatchMechanism)) {
				String downloadedFileName = downloadFile(absoluteRemotePath, file, absoluteLocalPath);
				dowloadedFiles.add(downloadedFileName);
			}
		}
		return dowloadedFiles;
	}

	public List<String> uploadFiles(String absoluteLocalPath, TextMatchMechanism fileNameMatchMechanism,
			String expectedValue, String absoluteRemotePath) {
		List<String> files = LocalMachineFileSystem.listFiles(absoluteLocalPath, fileNameMatchMechanism, expectedValue);
		List<String> uploadedFiles = new LinkedList<>();
		for (String file : files) {
			if (StringUtil.isEmptyAfterTrim(file)) {
				continue;
			}
			String downloadedFileName = uploadFile(file, absoluteRemotePath);
			uploadedFiles.add(downloadedFileName);
		}
		return uploadedFiles;
	}

	public String executeCommand(String command) {
		verifyConnection();

		ChannelExec channel = null;
		try {
			// channel type: shell, exec, sftp
			channel = (ChannelExec) ((Session) connection.getConnection()).openChannel("exec");
			channel.setCommand(command);
			ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
			channel.setOutputStream(responseStream);

			// ((ChannelExec)channel).setErrStream(System.err);
			InputStream dataStream = channel.getInputStream();
			InputStream errorStream = channel.getErrStream();

			channel.connect(10000);

			String result = IOUtils.toString(dataStream, StandardCharsets.UTF_8);
			String error = null;
			if (channel.getExitStatus() != 0) {
				error = IOUtils.toString(errorStream, StandardCharsets.UTF_8);
				throw new RuntimeException("Remote machine command failed. Command: " + command + ". Error: " + error);
			}
			return result;
		} catch (Exception e) {
			Assert.fail("Remote machine command failed. Command: " + command + ". Reason: " + e.getMessage(), e);
		} finally {
			if (channel != null) {
				try {
					channel.disconnect();
				} catch (Exception e2) {
				}
			}
		}

		return null;
	}

	/**
	 * Return the local file path where the file is downloaded.
	 * 
	 * @param absoluteRemotePath
	 * @param remoteFileName
	 * @param absoluteLocalPath
	 * @return
	 */
	public String downloadFile(String absoluteRemotePath, String remoteFileName, String absoluteLocalPath) {
		verifyConnection();

		ChannelSftp channel = null;
		try {
			// channel type: shell, exec, sftp
			channel = (ChannelSftp) ((Session) connection.getConnection()).openChannel("sftp");
			channel.connect(10000);

			channel.get(absoluteRemotePath + "/" + remoteFileName, absoluteLocalPath + "/" + remoteFileName);
		} catch (Exception e) {
			Assert.fail("Failed to download '" + absoluteRemotePath + "/" + remoteFileName + "' file. Reason: "
					+ e.getMessage(), e);
		} finally {
			if (channel != null) {
				try {
					channel.disconnect();
				} catch (Exception e2) {
				}
			}
		}
		return (absoluteLocalPath + File.separator + remoteFileName);
	}

	/**
	 * Downloads the file from remote location. Returns the local file path where it
	 * got downloaded.
	 * 
	 * @param absoluteRemoteFilePath
	 * @param absoluteLocalPath
	 * @return
	 */
	public String downloadFile(String absoluteRemoteFilePath, String absoluteLocalPath) {
		verifyConnection();

		ChannelSftp channel = null;
		String fileName = null;
		try {
			fileName = LocalMachineFileSystem.extractFileName(absoluteRemoteFilePath);
			// channel type: shell, exec, sftp
			channel = (ChannelSftp) ((Session) connection.getConnection()).openChannel("sftp");
			channel.connect(10000);

			File localPath = new File(absoluteLocalPath);
			if (!localPath.exists()) {
				localPath.mkdirs();
			}

			channel.get(absoluteRemoteFilePath, absoluteLocalPath);
		} catch (Exception e) {
			Assert.fail("Failed to download '" + absoluteRemoteFilePath + "' file to local location '"
					+ absoluteLocalPath + "'. Reason: " + e.getMessage(), e);
		} finally {
			if (channel != null) {
				try {
					channel.disconnect();
				} catch (Exception e2) {
				}
			}
		}
		return (absoluteLocalPath + File.separator + fileName);
	}

	/**
	 * Upload the file from local path to remote path. Returns the remote file path
	 * where the file was uploaded.
	 * 
	 * @param absoluteLocalPath
	 * @param localFileName
	 * @param absoluteRemotePath
	 * @return
	 */
	public String uploadFile(String absoluteLocalPath, String localFileName, String absoluteRemotePath) {
		verifyConnection();

		ChannelSftp channel = null;
		try {
			// channel type: shell, exec, sftp
			channel = (ChannelSftp) ((Session) connection.getConnection()).openChannel("sftp");
			channel.connect(10000);

			channel.put(absoluteLocalPath + File.separator + localFileName, absoluteRemotePath);
		} catch (Exception e) {
			Assert.fail("Failed to upload '" + absoluteLocalPath + File.separator + localFileName
					+ "' file at location '" + absoluteRemotePath + "'. Reason: " + e.getMessage(), e);
		} finally {
			if (channel != null) {
				try {
					channel.disconnect();
				} catch (Exception e2) {
				}
			}
		}
		return (absoluteRemotePath + "/" + localFileName);
	}

	/**
	 * Upload the local file on remote location. Returns the remote file path where
	 * it got uploaded.
	 * 
	 * @param absoluteLocalFilePath
	 * @param absoluteRemotePath
	 * @return
	 */
	public String uploadFile(String absoluteLocalFilePath, String absoluteRemotePath) {
		verifyConnection();

		ChannelSftp channel = null;
		try {
			// channel type: shell, exec, sftp
			channel = (ChannelSftp) ((Session) connection.getConnection()).openChannel("sftp");
			channel.connect(10000);

			channel.put(absoluteLocalFilePath, absoluteRemotePath);
		} catch (Exception e) {
			Assert.fail("Failed to upload '" + absoluteLocalFilePath + "' file at location '" + absoluteRemotePath
					+ "'. Reason: " + e.getMessage(), e);
		} finally {
			if (channel != null) {
				try {
					channel.disconnect();
				} catch (Exception e2) {
				}
			}
		}

		return (absoluteRemotePath + "/" + new File(absoluteLocalFilePath).getName());
	}

	private List<String> prepareFileList(String cmdOutput) {
		List<String> files = new LinkedList<>();
		if (StringUtil.isEmptyAfterTrim(cmdOutput)) {
			return files;
		}

		try (StringReader sr = new StringReader(cmdOutput); BufferedReader br = new BufferedReader(sr)) {
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.length() > 2) {
					files.add(line.substring(2));
				}
			}
		} catch (Exception e) {

		}
		return files;
	}

	@Override
	public synchronized void disconnect() {
		if (connection != null) {
			disconnect(connection);
			connection = null;
		}
	}

	public AbstractRemoteMachineActionHandler clone() {
		try {
			AbstractRemoteMachineActionHandler obj = (AbstractRemoteMachineActionHandler) ObjectUtil
					.findClassConstructor(this.getClass(),
							new Class[] { String.class, String.class, int.class, RemoteMachineConfig.class })
					.newInstance(this.remoteMachineName, this.appName, this.sessionExpiryDurationInSeconds,
							this.remoteMachineConfig);
			return obj;
		} catch (Exception ex) {
			Assert.fail("Failed to clone '" + this.getClass().getName() + "' class object.", ex);
		}
		return null;
	}

	// declare abstract methods here
	protected abstract void disconnect(RemoteMachineConnection connection);

}
