package com.way2automation.testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.way2automation.base.TestBase;
import com.way2automation.utilities.TestUtil;

public class CustomerLoginTest extends TestBase{//checking the login feature for the customers
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")//created a getData method below implemented dataprovider
	public void customerLoginTest(Hashtable<String,String> data) throws InterruptedException //passing hashtable as argument to retrieve the values from excel
	{
		click("homePage_CSS");
		click("cusLogin_CSS");
		select("yourName_CSS",data.get("customer") );
		click("login_CSS");
		Thread.sleep(1000);
		click("logout_CSS");
	}
}
