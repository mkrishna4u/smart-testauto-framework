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
package org.uitnet.testing.smartfwk.common.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCommandExecuter {
	private SmartCommandExecuter() {
		// do nothing
	}

	/**
	 * Execute system commands on local platform in synchronous mode.
	 * 
	 * @param shellInfo - the shell information in which command to be executed.
	 * 					if not specified then it will determine shell information automatically.
	 * 					like for window we use ["cmd.exe", "/c"],
	 * 						 for linux we use ["sh", "-c"]
	 * @param timeoutInSeconds - command timeout in seconds. after this timeout the process will killed and control will return.
	 * 		when its value is null then it will use default 300 seconds. When zero (0) then it will wait indefinitely. 
	 * 		Else will wait for the specified value of timeout.
	 * @param directory - directory in which the command will be executed.
	 * @param cmdName - the name of the command
	 * @param args - arguments of the command
	 * @return the command result that contains process details, exit status, output data and error data.
	 */
	public static SyncCommandResult executeSync(List<String> shellInfo, Integer timeoutInSeconds, String directory, String cmdName, String... args) {
		ProcessBuilder builder = new ProcessBuilder(args);
		SyncCommandResult cmdResult = new SyncCommandResult();
		if(timeoutInSeconds == null) {
			timeoutInSeconds = 300;
		}
		
		if(StringUtil.isEmptyAfterTrim(cmdName)) {
			directory = Locations.getProjectRootDir();
		}
		
		List<String> cmd = new LinkedList<>();
		if(shellInfo != null && !shellInfo.isEmpty()) {
			cmd = shellInfo;
		} else {
			if (OSDetectorUtil.getHostPlatform() == PlatformType.windows) {
				directory.replace("/", "\\");
				cmd.add("cmd.exe");
				cmd.add("/c");
			} else {
				directory.replace("\\", "/");
				cmd.add("sh");
				cmd.add("-c");
			}
		}

		cmd.add(cmdName);
		cmdResult.setCmdName(cmdName);
		if (args != null) {
			for (String arg : args) {
				cmd.add(arg);
			}
		}
		
		builder.directory(new File(directory));
		builder.command(cmd);
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			Process process = builder.start();
			cmdResult.setProcess(process);
			
			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String s;
			
			try {
				// Read the output from the command and print
				while ((s = stdInput.readLine()) != null) {
					if(StringUtil.isEmptyNoTrim(cmdResult.getOutputData())) {
						cmdResult.setOutputData(s);
					} else {
						cmdResult.setOutputData(cmdResult.getOutputData() + "\n" + s);
					}		
				}
			} catch(Exception ex1) {
				cmdResult.addException(ex1);
			}
			
			String y;
			try {
				// read any errors from the attempted command			
				while ((y = stdError.readLine()) != null) {
					if(StringUtil.isEmptyNoTrim(cmdResult.getErrorData())) {
						cmdResult.setErrorData(y);
					} else {
						cmdResult.setErrorData(cmdResult.getErrorData() + "\n" + y);
					}
				}
			} catch(Exception ex1) {
				cmdResult.addException(ex1);
			}
			boolean status = process.waitFor(timeoutInSeconds.intValue(), TimeUnit.SECONDS);
			cmdResult.setExitStatus(status);
			if(status == false) {
				try {
					process.destroyForcibly();
				} catch(Exception ex2) { 
					// do nothing
				}
			}
		} catch (Exception ex) {
			cmdResult.addException(ex);
		} finally {
			if (stdInput != null) {
				try {
					stdInput.close();
				} catch (Exception e) {
				}
			}
			if (stdError != null) {
				try {
					stdError.close();
				} catch (Exception e) {
				}
			}
		}
		return cmdResult;
	}
	
	/**
	 * Execute system commands on local platform in asynchronous mode.
	 * 
	 * @param shellInfo - the shell information in which command to be executed.
	 * 					if not specified then it will determine shell information automatically.
	 * 					like for window we use ["cmd.exe", "/c"],
	 * 						 for linux we use ["sh", "-c"]
	 * @param timeoutInSeconds - command timeout in seconds. after this timeout the process will be killed and control will return.
	 * 		when its value is null then it will use default 300 seconds. When zero (0) then it will wait indefinitely. 
	 * 		Else will wait for the specified value of timeout.
	 * @param directory - directory in which the command will be executed.
	 * @param cmdName - the name of the command
	 * @param args - arguments of the command
	 * @return the command result that contains process details, exit status, output data and error data.
	 */
	public static AsyncCommandResult executeAsync(List<String> shellInfo, Integer timeoutInSeconds, String directory, String cmdName, String... args) {
		ProcessBuilder builder = new ProcessBuilder(args);
		AsyncCommandResult cmdResult = new AsyncCommandResult();
		if(timeoutInSeconds == null) {
			timeoutInSeconds = 300;
		}
		
		if(StringUtil.isEmptyAfterTrim(cmdName)) {
			directory = Locations.getProjectRootDir();
		}
		
		List<String> cmd = new LinkedList<>();
		if(shellInfo != null && !shellInfo.isEmpty()) {
			cmd = shellInfo;
		} else {
			if (OSDetectorUtil.getHostPlatform() == PlatformType.windows) {
				directory.replace("/", "\\");
				cmd.add("cmd.exe");
				cmd.add("/c");
			} else {
				directory.replace("\\", "/");
				cmd.add("sh");
				cmd.add("-c");
			}
		}

		cmd.add(cmdName);
		cmdResult.setCmdName(cmdName);
		if (args != null) {
			for (String arg : args) {
				cmd.add(arg);
			}
		}
		
		builder.directory(new File(directory));
		builder.command(cmd);
		
		try {
			Process process = builder.start();
			cmdResult.setProcess(process);
			AsyncCommandThread th = new AsyncCommandThread(process, timeoutInSeconds);
			th.start();
			cmdResult.setCommandThread(th);
			
		} catch (Exception ex) {
			cmdResult.addException(ex);
		}
		return cmdResult;
	}
}
