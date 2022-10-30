package com.way2automation.base;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestLogs {//Selenium 3.0 onwards only application logs i.e. user defined logs will work.
	
	public static Logger log = Logger.getLogger(TestLogs.class);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PropertyConfigurator.configure(".\\src\\test\\resources\\properties\\log4j.properties");
		log.info("Logs started");

	}

}
