package com.leo.demo.engine;

import java.io.IOException;
import java.util.Properties;

public class BeanFactory {
	private static Properties properties;
	
	static{
		properties=new Properties();
		try {
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取UserEngine接口的实现类对象
	 * @return
	 */
	public static<T> T getImpl(Class<T> clazz)
	{
//		String key="UserEngine";
//		String key=clazz.getName();// com.ithm.lotteryhm36.engine.UserEngine
		String key=clazz.getSimpleName();
		String className=properties.getProperty(key);
		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
