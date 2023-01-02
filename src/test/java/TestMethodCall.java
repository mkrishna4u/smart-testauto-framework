import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.core.validator.MethodInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

public class TestMethodCall {
	public static void test1() {
		try {
			String variableName =  "tt";
			
			MethodInfo methodInfo = new MethodInfo();
			methodInfo.setClassName("TestMethodClass");
			methodInfo.setMethodName("testArray");
			
			List<String> argsType = new LinkedList<>();
			argsType.add("String[]");
			methodInfo.setArgsType(argsType);
			
			List<Object> argsValues = new LinkedList<>();
			List<String> v1 = new ArrayList<>();
			v1.add("2");
			v1.add("3");
			argsValues.add(v1);
			
			methodInfo.setArgsValue(argsValues);
			methodInfo.setIsStatic(false);
			
			
			Class<?> clazz = Class.forName(methodInfo.getClassName());
			Constructor<?> constructor = methodInfo.getIsStatic() ? null : ObjectUtil.findClassConstructor(clazz, null);
			Object object = methodInfo.getIsStatic() ? null : (constructor == null ? null : constructor.newInstance());
			
			Method m = null;
							
			if(methodInfo.getArgsType() == null || methodInfo.getArgsType().size() == 0) {
				m = ObjectUtil.findClassMethod(clazz, methodInfo.getMethodName(), methodInfo.getArgsValue().size());
			} else {
				m = ObjectUtil.findClassMethod(clazz, methodInfo.getMethodName(), 
						methodInfo.getArgsType().toArray(new String[methodInfo.getArgsType().size()]));
			}
			
			if(StringUtil.isEmptyAfterTrim(variableName)) {
				if(methodInfo.getArgsValue().size() > 0) {
					Object[] valueArr = methodInfo.getArgsValue().toArray(new Object[methodInfo.getArgsValue().size()]);
					ObjectUtil.invokeMethod(object, m, valueArr);
				} else {
					m.invoke(object);
				}
			} else {
				Object[] valueArr = methodInfo.getArgsValue().toArray(new Object[methodInfo.getArgsValue().size()]);
					
				Object returnV = methodInfo.getArgsValue().size() > 0 ? 
						ObjectUtil.invokeMethod(object, m, valueArr) : m.invoke(object);
				System.out.println("returnV:" + returnV);
			}
			System.out.println("DONE");
		} catch(Exception e) {
			Assert.fail("Failed to execute method.", e);
		}
	}
	
	public static void main(String[] args) {
		try {
			TestMethodCall.test1();
			System.out.println("Hello");
			//System.out.println("AA: " +  new ArrayList<java.lang.String> ().getClass().get);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
