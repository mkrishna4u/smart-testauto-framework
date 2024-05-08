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

import java.util.LinkedList;
import java.util.List;

/**
 * @author Madhav Krishna
 */
public class AsyncCommandResult {
	private String cmdName = "";
	private Process process = null;
	private AsyncCommandThread commandThread;
	private List<String> exceptions;
	
	public AsyncCommandResult() {
		exceptions = new LinkedList<String>();
	}
	
	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void addException(Throwable exception) {
		this.exceptions.add(exception.getMessage());
	}

	public AsyncCommandThread getCommandThread() {
		return commandThread;
	}

	public void setCommandThread(AsyncCommandThread commandThread) {
		this.commandThread = commandThread;
	}
	
	/**
	 * 
	 * @param waitForMillis - 0 means wait forever else wait for specified amount of time.
	 */
	public void join(int waitForMillis) {
		if(this.commandThread != null && this.commandThread.isAlive()) {
			try {
				this.commandThread.join(waitForMillis);
			} catch(Exception ex) {
				// do nothing.
			}
		}
	}
	
	public void print() {
		System.out.println("Command Name: " + cmdName);
		System.out.println("Process: " + process);
		System.out.println("Exceptions: \n");
		int counter = 1;
		for(String e : exceptions) {
			System.out.println("\t Exception-" + counter + ": " + e + "\n");
		}
	}

}
