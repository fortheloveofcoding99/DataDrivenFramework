package com.way2automation.misc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		System.out.println(System.getProperty("user.dir"));//path to the project
		
		Properties config = new Properties();// creating a properties object to read the config file eg. url, browser
		
		Properties or = new Properties();// creating a properties object to read the Objectrepo file eg.loactors
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties");// location of the config file being passed to the constructor of FileInputStream
		config.load(fis);//calling the load method to access the key-values in the config file
		
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\ObjectRepo.properties");// location of the object repo  file being passed to the constructor of FileInputStream
		or.load(fis);//calling the load method to access the key-values in the object repo file
		
		System.out.println(config.getProperty("browser"));// getting the browser value from config file
		
		System.out.println(or.getProperty("bmlButton"));// getting the browser value from config file
		//driver.findElement(By.cssSelector(OR.getProperty("bmlButton").click()
	}

}
