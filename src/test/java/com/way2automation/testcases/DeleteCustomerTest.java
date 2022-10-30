package com.way2automation.testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.way2automation.base.TestBase;
import com.way2automation.utilities.TestUtil;

public class DeleteCustomerTest extends TestBase{//to delete all the customers that were added to the DB

	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")//created a getData method below implemented dataprovider
	public void deleteCustomerTest(Hashtable<String,String> data) throws InterruptedException
	{
		
		click("homePage_CSS");
		click("bmlButton_CSS");
		click("customerdetail_CSS");
		type("searchCustomer_CSS",data.get("customer"));
		click("deleteCustomer_CSS");
		Thread.sleep(1000);
	}
	
}
