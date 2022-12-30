import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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
			methodInfo.setClassName("java.lang.Math");
			methodInfo.setMethodName("sqrt");
			
			List<Class<?>> argsType = new LinkedList<>();
			argsType.add(double.class);
			methodInfo.setArgsType(argsType);
			
			List<Object> argsValues = new LinkedList<>();
			argsValues.add(25);
			
			methodInfo.setArgsValue(argsValues);
			methodInfo.setIsStatic(true);
			
			
			Class<?> clazz = Class.forName(methodInfo.getClassName());
			Constructor<?> constructor = methodInfo.getIsStatic() ? null : ObjectUtil.findClassConstructor(clazz, null);
			Object object = methodInfo.getIsStatic() ? null : (constructor == null ? null : constructor.newInstance());
			
			Method m = null;
							
			if(methodInfo.getArgsType() == null || methodInfo.getArgsType().size() == 0) {
				m = ObjectUtil.findClassMethod(clazz, methodInfo.getMethodName(), methodInfo.getArgsValue().size());
			} else {
				m = ObjectUtil.findClassMethod(clazz, methodInfo.getMethodName(), 
						methodInfo.getArgsType().toArray(new Class<?>[methodInfo.getArgsType().size()]));
			}
			
			if(StringUtil.isEmptyAfterTrim(variableName)) {
				if(methodInfo.getArgsValue().size() > 0) {
					m.invoke(object, methodInfo.getArgsValue().toArray(new Object[methodInfo.getArgsValue().size()]));
				} else {
					m.invoke(object);
				}
			} else {
				Object returnV = methodInfo.getArgsValue().size() > 0 ? 
						m.invoke(object, methodInfo.getArgsValue().toArray(new Object[methodInfo.getArgsValue().size()])) : m.invoke(object);
				System.out.println("OUTPUT: " + returnV);
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
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
