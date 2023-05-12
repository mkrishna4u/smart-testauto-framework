@Echo OFF
call mvn dependency:build-classpath > maven-project.jars
call mvn compile exec:java -Dexec.mainClass="org.uitnet.testing.smartfwk.toolkit.SmartToolkit" -Dexec.args="%*"
call del maven-project.jars
