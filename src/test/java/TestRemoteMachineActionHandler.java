import java.util.List;

import org.uitnet.testing.smartfwk.remote_machine.RemoteMachineConfig;
import org.uitnet.testing.smartfwk.remote_machine.SmartRemoteMachineActionHandler;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

public class TestRemoteMachineActionHandler {

	
	public static void main(String[] args) {
		try {
			RemoteMachineConfig userProfile = new RemoteMachineConfig();
			userProfile.setHostNameOrIpAddress("192.168.0.117");
			userProfile.setPort(22);
			userProfile.setUserName("testuser");
			// userProfile.setPassword("Test-user#34$");
			userProfile.getSshConfigs().put("StrictHostKeyChecking", "no");
			//userProfile.getSshConfigs().put("HashKnownHosts", "no");
			userProfile.getSshConfigs().put("PreferredAuthentications", "publickey,password");
			
			System.out.println(Locations.getProjectRootDir());
			
			userProfile.setPrivateKeyPath("src/test/java/ssh_host_rsa_key2");
			//userProfile.setPublicKeyPath("src/test/java/ssh_host_rsa_key2.pub");
//			userProfile.setPrivateKeyPassphrase("Test-user#34$");
			
			SmartRemoteMachineActionHandler handler = new SmartRemoteMachineActionHandler("TestServer", "TestApp", 36000, userProfile);
			handler.connect();
			
			//handler.validateFileExists("/home/testuser", TextMatchMechanism.containsExpectedValue, "bb");
			//handler.validateFileExists("/home/testuser", TextMatchMechanism.startsWithExpectedValue, "bb");
			
			//System.out.println(handler.getFolderList("/home/testuser"));
			
			//handler.validateFolderExists("/home/testuser", TextMatchMechanism.containsExpectedValue, "Videos");
			//handler.validateFolderExists("/home/testuser", TextMatchMechanism.startsWithExpectedValue, "Docu");
			
			//handler.deleteFiles("/home/testuser", TextMatchMechanism.startsWithExpectedValue, "ab");
			//handler.downloadFile("/home/testuser", "abc.txt", Locations.getProjectRootDir());
			//List<String> downloadedFiles =  handler.downloadFiles("/home/testuser", TextMatchMechanism.startsWithExpectedValue, "ab", Locations.getProjectRootDir());
			
			//String remoteFile = handler.uploadFile(Locations.getProjectRootDir(), "abc2.txt", "/home/testuser");
			List<String> uploadedFiles = handler.uploadFiles(Locations.getProjectRootDir(), TextMatchMechanism.startsWithExpectedValue, "ab", "/home/testuser");
			System.out.println("Files: " + uploadedFiles);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
