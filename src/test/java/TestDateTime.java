import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestDateTime {
	public static void main(String[] args) {
		try {
			TimeZone tzone = TimeZone.getTimeZone("America/Los_Angeles");
			Date date = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss Z");
			sdf.setTimeZone(tzone);
			String formattedDate = sdf.format(date);
			System.out.println(formattedDate);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
