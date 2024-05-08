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
public class SyncCommandResult {
	private String cmdName = "";
	private Process process = null;
	private boolean exitStatus = false; // false, means command either failed or still running.
	private String outputData = "";
	private String errorData = "";
	private List<String> exceptions;
	
	public SyncCommandResult() {
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

	public boolean getExitStatus() {
		return exitStatus;
	}

	public void setExitStatus(boolean exitStatus) {
		this.exitStatus = exitStatus;
	}

	public String getOutputData() {
		return outputData;
	}

	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}

	public String getErrorData() {
		return errorData;
	}

	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}

	public List<String> getExceptions() {
		return exceptions;
	}

	public void addException(Throwable exception) {
		this.exceptions.add(exception.getMessage());
	}
	
	public void print() {
		System.out.println("Command Name: " + cmdName);
		System.out.println("Command Status: " + exitStatus);
		System.out.println("Output Data: " + outputData);
		System.out.println("Error Data: " + errorData);
		System.out.println("Exceptions: \n");
		int counter = 1;
		for(String e : exceptions) {
			System.out.println("\t Exception-" + counter + ": " + e + "\n");
		}
	}
}
