package edu.buaa.databaseManager.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Privateclassaccess {
	public static void main(String[] args) throws Exception {
		Field field = Class.forName("edu.buaa.databaseManager.excel.Normal").getDeclaredField("ss");
		field.setAccessible(true);
		Normal n = new Normal();
		System.out.println(field.getType().toString());
		System.out.println(field.getName());
		System.out.println(field.getModifiers());
		Object s = field.get(n);
		System.out.println(s);
		String x = "omg";
		field.set(n, x);
		System.out.println(field.get(n));
		Method method = Class.forName("edu.buaa.databaseManager.excel.Normal").getDeclaredMethod("ga", new Class[] { int.class });
		method.setAccessible(true);
		method.invoke(n, 3);
	}
}

class Normal {
	private String ss = "ddd";

	private void ga(int i) {
		System.out.println("ga!!" + i);
	}
}