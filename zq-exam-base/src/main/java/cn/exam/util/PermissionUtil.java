package cn.exam.util;

import java.lang.reflect.Method;

/**
 * 权限表达式的拼装类
 */
public class PermissionUtil {
	public static String buildMethod(Method method){
		String className = method.getDeclaringClass().getName();//获取到全限定
		return className+":"+method.getName();//拼接全限定名和action方法名
	}
}
