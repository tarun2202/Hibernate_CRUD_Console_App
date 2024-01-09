package com.app.utils;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static SessionFactory factory;
	static {
		System.out.println("in static init block");
		factory=new Configuration() //creates EMPTY instance of hib config
				.configure() //config instance loaded with props from hib cfg xml
				.buildSessionFactory();//build singleton instance of SF
	}
	//getter 
	public static SessionFactory getFactory() {
		return factory;
	}
	
}
