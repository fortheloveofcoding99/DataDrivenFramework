package com.way2automation.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.way2automation.base.TestBase;

public class LogInTest extends TestBase {//extending testbase to inherit all setup and teardown conditions declared in testbase oops concept used inheritance
	
	@Test
	public void logInTest() throws InterruptedException, IOException
	{
		log.info("Inside login test");
		String ActualSiteURL = driver.getCurrentUrl();
		verifyEquals((config.getProperty("testSiteUrl")), ActualSiteURL);//verify method to check the title of the page
		click("bmlButton_CSS");//on site home page clicking the bank manager login button
		log.info("Login executed sucessfully");
		Assert.assertTrue(isElementPresent(By.cssSelector(or.getProperty("addCust_CSS"))), "Login Failed");//testng assertion using a wrapper method -> isElementPresent checking if a webelement is present
		
		
	}

}
