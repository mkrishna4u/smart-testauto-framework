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
package org.uitnet.testing.smartfwk.toolkit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.local_machine.LocalMachineFileSystem;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;

/**
 * This is a command line utility using that we can do the following operations:
 * 
 * @author Madhav Krishna
 *
 */
public class SmartToolkit {
	private static StringArgumentValidator appValidator = new StringArgumentValidator(2, 32, "[A-Za-z0-9_]+");
	private static StringArgumentValidator classNameValidator = new StringArgumentValidator(2, 64, "[A-Za-z0-9_]+");
	private static EnumArgumentValidator<PlatformType> platformTypeValidator = new EnumArgumentValidator<PlatformType>(true, PlatformType.class);
	private static EnumArgumentValidator<WebBrowserType> webBrowserTypeValidator = new EnumArgumentValidator<WebBrowserType>(true, WebBrowserType.class);
	
	private static Map<String, SmartToolkitCommand> toolkitCommands = new LinkedHashMap<>();

	public static void main(String[] args) {
		try {
			initCommands();
			
			List<CommandInfo> userCommands = prepareUserCommands(args);
			processUserCommands(userCommands);
			
			//printHelp();
			//printHelp("--init");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static List<CommandInfo> prepareUserCommands(String[] args) {
		int i = 0;
		List<CommandInfo> commands = new LinkedList<>();
		CommandInfo command = null;
		List<String> commandArgs = null;
		SmartToolkitCommand stCommand = null;
		for(String arg : args) {
			if(arg.startsWith("--")) {
				stCommand = toolkitCommands.get(arg);
				if(stCommand == null) {
					Assert.fail("Command option '" + arg + "' is not valid.\nHelp: " + getHelp());
				}
				
				if(command != null) {
					if(stCommand.getArgs() != null && (stCommand.getArgs().length !=  command.getArgs().size())) {
						printHelp(stCommand.getOption());
						Assert.fail("Expected '" + stCommand.getArgs().length + "' arguments for command option '" + stCommand.getOption() + "'.");
					}
					commands.add(command);
					command = null;
				}
				command = new CommandInfo(arg);
				commandArgs = new LinkedList<>();
				command.setArgs(commandArgs);
			} else {
				CommandArgument[] argArr = stCommand.getArgs();
				if(argArr != null && argArr.length > 0) {
					for(CommandArgument ca : argArr) {
						try {
							CommandArgumentValidator validator = ca.getValidator();
							if(validator != null) {
								validator.validate(stCommand.getOption(), ca, arg);
							}
						} catch(Exception | Error e) {
							printHelp(stCommand.getOption());
							throw e;
						}
					}
					commandArgs.add(arg);
				}
			}
			
		}
		
		if(command != null) {
			if(stCommand.getArgs() != null && (stCommand.getArgs().length !=  command.getArgs().size())) {
				printHelp(stCommand.getOption());
				Assert.fail("Expected '" + stCommand.getArgs().length + "' arguments for command option '" + stCommand.getOption() + "'.");
			}
			commands.add(command);
		}
		
		return commands;
	}
	
	private static void processUserCommands(List<CommandInfo> commands) {
		SmartToolkitCommandExecuter executer = new SmartToolkitCommandExecuter();
		try {
			FileUtils.deleteDirectory(new File(Locations.getProjectRootDir() + File.separator + "temp"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			extractSmartTestAutoFwkJarFileIntoTempDir();

			for (CommandInfo command : commands) {
				switch (command.getName()) {
				case "--init":
					if (command.getArgs() == null || command.getArgs().size() == 0) {
						executer.initProject(null);
					} else {
						executer.initProject(command.getArgs().get(0));
					}
					break;
				}
			}
		} finally {
			try {
				//FileUtils.deleteDirectory(new File(Locations.getProjectRootDir() + File.separator + "temp"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void initCommands() {
		toolkitCommands.put("--init", new SmartToolkitCommand("--init",
				new CommandArgument[] { new CommandArgument("<app-name>", appValidator) },
				"Used to initialize the project with new application name. If application is not configured then it will configure the application also."));
		
		toolkitCommands.put("--add-app", new SmartToolkitCommand("--add-app",
				new CommandArgument[] { new CommandArgument("<app-name>", appValidator) },
				"Used to add new app when it does not exist."));
		
		toolkitCommands.put("--add-app-env", new SmartToolkitCommand("--add-app-env",
				new CommandArgument[] { 
					new CommandArgument("<app-name>", appValidator),
					new CommandArgument("<environment-name>", appValidator) 
				},
				"Used to add new environment for the already configured application."));
		
		toolkitCommands.put("--add-app-stepdef-class", new SmartToolkitCommand("--add-app-stepdef-class",
				new CommandArgument[] { 
					new CommandArgument("<app-name>", appValidator),
					new CommandArgument("<step-definitions-class-file-name>", classNameValidator)
				},
				"Used to create new step definition file for the already existing application."));
		
		toolkitCommands.put("--add-app-po", new SmartToolkitCommand("--add-app-po",
				new CommandArgument[] { 
					new CommandArgument("<app-name>", appValidator),
					new CommandArgument("<page-object-class-file-name>", classNameValidator)
				},
				"Used to create new page object class for UI page. In that tester can configure page elements."));
		
		toolkitCommands.put("--install-webdriver", new SmartToolkitCommand("--install-webdriver",
				new CommandArgument[] { 
					new CommandArgument("<platform-type>", platformTypeValidator),
					new CommandArgument("<web-browser-type>", webBrowserTypeValidator),
					new CommandArgument("<driver-version>", null)
				},
				"Used to install web driver to the specified version."));
		
		toolkitCommands.put("--add-e2e-feature", new SmartToolkitCommand("--add-e2e-feature",
				new CommandArgument[] { 
					new CommandArgument("<feature-file-name>", classNameValidator) 
				},
				"Used to create a e2e (End to end) feature file in e2e directory."));
		
		toolkitCommands.put("--add-app-api-feature", new SmartToolkitCommand("--add-app-api-feature",
				new CommandArgument[] { 
					new CommandArgument("<app-name>", appValidator),
					new CommandArgument("<feature-file-name>", classNameValidator)
				},
				"Used to create a api feature file for configured application."));
		
		toolkitCommands.put("--add-app-ui-feature", new SmartToolkitCommand("--add-app-ui-feature",
				new CommandArgument[] { 
					new CommandArgument("<app-name>", appValidator),
					new CommandArgument("<feature-file-name>", classNameValidator)
				},
				"Used to create a UI feature file for configured application."));
		
		toolkitCommands.put("--add-app-remote-machine", new SmartToolkitCommand("--add-app-remote-machine",
				new CommandArgument[] {
					new CommandArgument("<app-name>", appValidator),
					new CommandArgument("<remote-machine-name>", appValidator)
				},
				"Used to add remote machine in the existing remote-machine config file for the configured application."
				+ " If Remote machine file does not exist then it will configure the one."));
		
		toolkitCommands.put("--install-appium-server", new SmartToolkitCommand("--install-appium-server",
				new CommandArgument[] { },
				"Used to install appium server for mobile testing. Note installed nodejs directory path should be "
				+ "configured in PATH system variabled so that it can use npm or node command."));
		
		toolkitCommands.put("--start-appium-server", new SmartToolkitCommand("--start-appium-server",
				new CommandArgument[] { },
				"Used to start appium server on default host and port. Note installed nodejs directory path should be "
						+ "configured in PATH system variabled so that it can use npm or node command."));
		
	}

	private static void printHelp() {
		System.out.println(getHelp());
	}
	
	private static String getHelp() {
		String help = "smart-studio [option] [space-separated-arguments]\nOptions are:";
		for(Map.Entry<String, SmartToolkitCommand> entry: toolkitCommands.entrySet()) {
			help = help + "\n" + entry.getValue() + "\n";
		}
		
		return help;
	}
	
	private static void printHelp(String commandName) {
		String help = "smart-studio ";
		help = help + toolkitCommands.get(commandName);
		
		System.out.println(help);
	}
	
	private static void extractSmartTestAutoFwkJarFileIntoTempDir() {
		try {
			List<String> lines =  Files.readAllLines(Path.of(Locations.getProjectRootDir() + File.separator + "maven-project.jars"));
			String classPath = null;
			
			if(lines != null) {
				for(String line : lines) {
					if(line.contains("smart-testauto-framework-")) {
						classPath = line;
						break;
					}
				}
				
				String[] jars = null;
				if(classPath != null) {					
					if(OSDetectorUtil.getHostPlatform() == PlatformType.windows) {
						jars = classPath.split(";");
					} else {
						jars = classPath.split(":");
					}
				}
				
				String smartFwkJarLocation = null;
				if(jars != null) {
					for(String jar : jars) {
						if(jar.contains("smart-testauto-framework-")) {
							smartFwkJarLocation = jar;
							break;
						}
					}
				}
				
				String jarExtractPath = Locations.getProjectRootDir() + File.separator + "temp";
				if(smartFwkJarLocation != null) {
					LocalMachineFileSystem.createDirectoriesIfNotExist(jarExtractPath);
					try (JarFile jar = new JarFile(smartFwkJarLocation)) {
				        Enumeration<JarEntry> entries = jar.entries();
				        while (entries.hasMoreElements()) {
				            JarEntry ent = entries.nextElement();
				            File f = new File(jarExtractPath, ent.getName());
				            if (ent.isDirectory()) {
				                f.mkdir();
				                continue;
				            }
				            
				            try (InputStream is = jar.getInputStream(ent);) {
				            	FileUtils.copyInputStreamToFile(is, f);
				            }catch(Exception ex1) {
				            	Assert.fail("Failed to extract '" + smartFwkJarLocation + "' file at location '" + jarExtractPath + "'. Reason: " + ex1.getMessage(), ex1);
				            }
				        }
				    }
				}
				
			}
		}catch(Exception ex) {
			Assert.fail("Failed to extract <smart-testauto-framework-x.x.x.jar> file into temp directory.");
		}
	}
	
	private static class CommandInfo {
		private String name;
		private List<String> args;
		
		public CommandInfo(String name) {
			this.name = name;
			this.args = new LinkedList<>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<String> getArgs() {
			return args;
		}

		public void setArgs(List<String> args) {
			this.args = args;
		}
		
		public void addArg(String arg) {
			this.args.add(arg);
		}
		
		@Override
		public String toString() {
			return "Name: " + name + ", Arguments: " + args;
		}
	}
}

