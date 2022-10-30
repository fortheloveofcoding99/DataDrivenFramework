package com.way2automation.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.way2automation.base.TestBase;

public class TestUtil extends TestBase{
	
	public static String screenshotPath;
	public static String screenshotName;
	
	public static void captureScreenshot() throws IOException//declaring the method as static so can be called classname.methodname
	{
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);//typecasting takescreenshot interface with driver reference
		Date d = new Date();
		String screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";//generating jpg image with the date so it cant override the previous image saved
		FileUtils.copyFile(srcFile,new File(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\"+screenshotName));//copying the file to particular location 
	}
	@DataProvider(name="dp") // since we will be using this method everytime we are using excel in our test cases , hence added in the util file
	public Object [] [] getData(Method m)// 2D object array 
	{
		String sheetname = m.getName();
		int rows = excel.getRowCount(sheetname);//based on the method name the sheet name should be same as method name
		//System.out.println(rows);
		int cols = excel.getColumnCount(sheetname);
		//System.out.println(cols);
		Object[][] data =  new Object[rows-1][1];
		
		Hashtable<String,String> table = null; //For each row a table will be be created, For each row the key will be the 1st row in the sheet and the value will be the cell-values in the row
		
		for (int rowNum = 2; rowNum<=rows; rowNum++)
		{
			table = new Hashtable<String,String>();
			
			for (int colNum = 0; colNum<cols; colNum++)
			{	
				table.put(excel.getCellData(sheetname, colNum, 1), excel.getCellData(sheetname, colNum, rowNum));// the 1st row will be the key i.e. is static, and the value is the cellValue for the row nums - rowNum
				//data stored in the form 1st data- [0][0]
				data[rowNum-2][0]=table;
				
			}
		}
		return data;
		
		
	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel)//will check on the excel sheet if the test case are supposed to run 
	{
		String sheetName = "test_suite";
		
		int rows = excel.getRowCount(sheetName);
		
		for(int rNum=2;rNum<=rows;rNum++)
		{
			String testCase = excel.getCellData(sheetName, "TCID", rNum);
			
			if(testCase.equalsIgnoreCase(testName))
			{
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);//will check if the runmode will return Y 
			
				
				if(runmode.equalsIgnoreCase("Y"))
					return true;
				else
					return false;
				
			}	
		}
		
		return true;
		
	}

}
