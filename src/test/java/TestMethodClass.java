import java.util.ArrayList;
import java.util.Arrays;

public class TestMethodClass {
	
	public String returnHello(String name) {
		return "Hello " + name;
	}
	
	public String testArray(String[] names) {
		return "Hello " + Arrays.asList(names);
	}
	
	public String testList(ArrayList<String> names) {
		return "Hello " + names;
	}
	
	public String testList2(ArrayList<Integer> values) {
		return "Hello " + values;
	}
	
}
