import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class TestNumbers {
	public static void main(String[] args) {
		try {
			DecimalFormat df = new DecimalFormat("$###,###.###"); // or pattern "###,###.##$"
			System.out.println(df.format(12345.678));
			
			System.out.println(new DecimalFormat("#.##").format(44354355.5678));
			
			NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
			System.out.println("nf:" + nf.format(4234.45435));
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
