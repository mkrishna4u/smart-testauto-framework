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

	/**
	 * Execute platform commands.
	 * 
	 * @param directory - directory in which the command will be executed.
	 * @param cmdName
	 * @param args
	 * @return
	 */
	public int execute(String directory, String cmdName, String... args) {
		ProcessBuilder builder = new ProcessBuilder(args);
		if(StringUtil.isEmptyAfterTrim(cmdName)) {
			directory = Locations.getProjectRootDir();
		}
		
		List<String> cmd = new LinkedList<>();
		if (OSDetectorUtil.getHostPlatform() == PlatformType.windows) {
			directory.replace("/", "\\");
			cmd.add("cmd.exe");
			cmd.add("/c");
		} else {
			directory.replace("\\", "/");
			cmd.add("sh");
			cmd.add("-c");
		}

		cmd.add(cmdName);
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

			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String s;
			// Read the output from the command and print
			System.out.println("Command OUTPUT:\n\n");
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			String y;
			// read any errors from the attempted command
			System.out.println("Command ERRORS (If any):\n\n");
			while ((y = stdError.readLine()) != null) {
				System.out.println(y);
			}

			return process.waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
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
		return -1;
	}

}
