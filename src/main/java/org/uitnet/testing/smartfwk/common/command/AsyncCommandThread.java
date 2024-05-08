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
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author Madhav Krishna
 */
public class AsyncCommandThread extends Thread {
	private Process process;
	private Integer timeoutInSeconds;
	
	public AsyncCommandThread(Process process, Integer timeoutInSeconds) {
		this.process = process;
		this.timeoutInSeconds = timeoutInSeconds;
	}
	
	@Override
	public void run() {
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		
		try {			
			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String s;
			
			// Read the output from the command and print
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}
			
			String y;
			// read any errors from the attempted command			
			while ((y = stdError.readLine()) != null) {
				System.out.println(y);
			}
			
			boolean status = timeoutInSeconds.intValue() == 0 ? process.waitFor() >= 0
					: process.waitFor(timeoutInSeconds.intValue(), TimeUnit.SECONDS);
			if(status == false) {
				try {
					process.destroyForcibly();
				} catch(Exception ex2) { 
					// do nothing
				}
			}
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
	}
	
	@Override
	public void interrupt() {
		if(this.process != null) {
			process.destroyForcibly();
		}
		super.interrupt();
	}
}
