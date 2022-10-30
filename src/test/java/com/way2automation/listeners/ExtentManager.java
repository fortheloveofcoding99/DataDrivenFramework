package com.way2automation.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.way2automation.base.TestBase;


public class ExtentManager extends TestBase{//for working with extent reports
	
	
	
	public static ExtentReports extent;
	
	
	
	public static ExtentReports createInstance(String fileName)//generate the file report provided what filename is given
	{
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);//constructor that accepts the path for html reports
		
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setReportName("Automation Test Results");
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		
		extent.setSystemInfo("Automation tester", "Som");
		extent.setSystemInfo("Website", "Way2Automation");
		extent.setSystemInfo("Build#", "w2a999");
		return extent;
		
		
	}
	
	public static String screenshotpath;
	public static String screenshotname;
	
	public static void captureScreenshot() {

		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		screenshotname = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		try {
			FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir") + "\\test-output\\html\\" + screenshotname));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}
	
}
