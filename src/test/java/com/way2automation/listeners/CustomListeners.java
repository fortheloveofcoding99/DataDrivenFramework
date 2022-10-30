package com.way2automation.listeners;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.way2automation.base.TestBase;
import com.way2automation.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener{
	
	static Date d = new Date();
	static String filename = "Extent_"+d.toString().replace(":", "_").replace(" ", "_")+".html";//giving a file name appended with date dynamically
	private static ExtentReports extent = ExtentManager.createInstance(System.getProperty("user.dir")+"\\test-output\\html\\"+filename);//save location for extent report
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();//incase of parallel execution the executiion should be on seperate threads
	
	public void onTestStart(ITestResult result) {//test name we receive it from the result argument
		// TODO Auto-generated method stub
		ExtentTest test = extent.createTest(result.getTestClass().getName()+"     @TestCase : "+result.getMethod().getMethodName());
        testReport.set(test);//get the test class name and test method name and set the test with the same name
        if (!(TestUtil.isTestRunnable(result.getName(), excel)))//setting up the runmode for the test cases retrieving the value from excel
         {
            throw new SkipException("Skipping the " + result.getName().toUpperCase() + " as the RunMode is NO");
         }
		
	}

	public void onTestSuccess(ITestResult result) {//creating different feature on the extent report on test pass
		// TODO Auto-generated method stub
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
		
		
	}

	public void onTestFailure(ITestResult result) {//creating different feature on the extent report on test fail
		//extent reports
		String excepionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get().fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
				+ "</font>" + "</b >" + "</summary>" +excepionMessage.replaceAll(",", "<br>")+"</details>"+" \n");
		
		try {

			ExtentManager.captureScreenshot();
			testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.screenshotname)
							.build());
		} catch (IOException e) {

		}
		
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);
		
		//test-ng reports
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("Capturing screenshot");
		Reporter.log("<a target=\"_blank\"href="+TestUtil.screenshotName+">Screenshot</a>");//creating a link on the testng report for the screenshot
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\"href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=100 width=100></img></a>");//storing screenshot at the same location where the report is being generated,
																																				//we are creating the screenshot from the same page so it will directly pick it up
		
		
		
	}

	public void onTestSkipped(ITestResult result) {//creating different feature on the extent report on test skip
		
		String methodName=result.getMethod().getMethodName();
		String logText="Test Case:- "+ methodName+ " Skipped";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		CustomListeners.testReport.get().log(Status.SKIP, "Method skipped: "+ m);
		
	}

	
	public void onFinish(ITestContext context) {
		// flushing extent after test case executuion is done
		if (extent != null) {

			extent.flush();
		}
		
	}

}
