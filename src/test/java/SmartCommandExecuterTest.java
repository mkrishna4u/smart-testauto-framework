import org.uitnet.testing.smartfwk.common.command.AsyncCommandResult;
import org.uitnet.testing.smartfwk.common.command.SmartCommandExecuter;
import org.uitnet.testing.smartfwk.common.command.SyncCommandResult;

public class SmartCommandExecuterTest {

	public static void main(String[] args) {
		try {
			SyncCommandResult cmdResult = SmartCommandExecuter.executeSync(null, 0, "C:/projects/temp", "dir");
			cmdResult.print();
			
			AsyncCommandResult cmdResult2 = SmartCommandExecuter.executeAsync(null, 0, "C:/projects/temp", "dir");
			cmdResult2.join(0);
			cmdResult2.print();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
