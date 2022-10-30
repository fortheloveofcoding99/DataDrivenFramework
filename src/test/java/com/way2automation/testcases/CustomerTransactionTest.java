package com.way2automation.testcases;
import java.io.IOException;
import java.util.Hashtable;
import org.testng.annotations.Test;
import com.way2automation.base.TestBase;
import com.way2automation.utilities.TestUtil;
public class CustomerTransactionTest extends TestBase{//checking transactions and validating the messages 

	
		
		@Test(dataProviderClass=TestUtil.class,dataProvider="dp")//created a getData method below implemented dataprovider
		public void customerTransactionTest(Hashtable<String,String> data) throws InterruptedException , IOException //passing hashtable as argument to retrieve the values from excel
		{
			select("yourName_CSS",data.get("customer") );
			click("login_CSS");
			Thread.sleep(1000);
			verifyEquals(data.get("customer"), text("accountOwner_CSS"));//will verify if the account opener name is correct
			click("deposit_CSS");
			type("Amount_CSS", data.get("dep"));
			click("submit_CSS");
			Thread.sleep(1000);
			verifyEquals(data.get("DepositMessage"), text("message_CSS"));//will verify if deposit message is dispalyed
			Thread.sleep(1000);
			click("withdrawal_CSS");
			Thread.sleep(1000);
			type("Amount_CSS", data.get("wd"));
			Thread.sleep(1000);
			click("submit_CSS");
			Thread.sleep(1000);
			verifyEquals(data.get("WithdrawalMessage"), text("message_CSS"));//will verify if the withdrawal was successful
			Thread.sleep(1000);
//			System.out.println(text("finalBalance_CSS"));
//			String[] balance = data.get("balance").toString().split(".");
//			String finalBalance = balance[0];
//			verifyEquals(finalBalance, text("finalBalance_CSS"));
			click("logout_CSS");
			Thread.sleep(1000);
		}
	
}
