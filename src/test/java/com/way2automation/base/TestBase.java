package com.way2automation.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.way2automation.listeners.CustomListeners;
import com.way2automation.listeners.ExtentManager;
import com.way2automation.utilities.ExcelReader;
import com.way2automation.utilities.TestUtil;

public class TestBase {
	/*This class will be used as a super class or parent class for all our test cases
	 * Used to initialize the following:
	 * WebDriver
	 * Properties
	 * Logs
	 * Extent Reports
	 * DB
	 * Excel
	 * Mail
	 * Reportng
	 * Extent Reports
	 * Jenkins
	 */
	
	public static WebDriver driver;//declaring driver variable as static making it class variable
	
	public static Properties config = new Properties();// creating a properties object to read the config file eg. url, browser also making it class variable declaring it as static
	
	public static Properties or = new Properties();// creating a properties object to read the Objectrepo file eg.loactors browser also making it class variable declaring it as static
	
	public static FileInputStream fis;
			
	public static Logger log = Logger.getLogger(TestLogs.class);//Selenium 3.0 onwards only application logs i.e. user defined logs will work. creating test logs
	
	public static ExcelReader excel = new ExcelReader(System.getProperty(".\\src\\test\\resources\\excel\\TestData.xlsx"));//to read the excel file present in resources
	
	public static WebDriverWait wait;//implementing Webdriver for explicit wait
	
	public static ExtentTest test;
	
	public static String browser;
	
	@BeforeSuite
	public void setup()
	
	{
		PropertyConfigurator.configure(".\\src\\test\\resources\\properties\\log4j.properties");// log output will be generated here:src/test/resources/logs/applog.txt , also tells where the log4j.property file is located
		log.info("Logs started");
		
		
		if (driver==null)//adding a try-catch loop to to read the config and objectRepo file
		{
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\config.properties"); // location of the config file being passed to the constructor of FileInputStream
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);//calling the load method to access the key-values in the config file
				log.info("config file loaded !");//logging into the applog text file
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\ObjectRepo.properties"); // location of the object repo  file being passed to the constructor of FileInputStream
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				or.load(fis);//calling the load method to access the key-values in the object repo file
				log.info("object repo file loaded !");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty())// if the browser is being picked from jenkins as parameterization
			{
				browser = System.getenv("browser");
			}
			else
			{
				browser = config.getProperty("browser");
			}
			config.setProperty("browser", browser);//setting the browser on the config page as per the choice
			
			if(config.getProperty("browser").equalsIgnoreCase("chrome")) //if preferred browser is chrome
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.info("chrome browser loaded !");
			}
			
			else if(config.getProperty("browser").equalsIgnoreCase("edge")) //if preferred browser is edge
			{
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\msedgedriver.exe");
				driver = new EdgeDriver();
				log.info("edge browser loaded !");
			}
			
			driver.get(config.getProperty("testSiteUrl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);//implementing explicit wait for 5 secs and gave the driver reference
		}
		
	}
	
	public void click(String locator)
	{
		if(locator.endsWith("_CSS"))//creating a click method with either Xpath or Css locator accepts locator as an argument
		{
		driver.findElement(By.cssSelector(or.getProperty(locator))).click();
		CustomListeners.testReport.get().log(Status.INFO,"clicking on: "+locator);
		}
		else if(locator.endsWith("_XPATH"))
		{
			driver.findElement(By.xpath(or.getProperty(locator))).click();
			CustomListeners.testReport.get().log(Status.INFO,"clicking on: "+locator);
		}
	}
	public void type(String locator, String value)//creating a type method with either Xpath or Css locator accepts locator and value as an argument
	{
		if(locator.endsWith("_CSS"))
		{
		driver.findElement(By.cssSelector(or.getProperty(locator))).sendKeys(value);
		CustomListeners.testReport.get().log(Status.INFO,"typing value : "+value);//logging on the extent report
		}
		else if(locator.endsWith("_XPATH"))
		{
			driver.findElement(By.xpath(or.getProperty(locator))).sendKeys(value);
			CustomListeners.testReport.get().log(Status.INFO,"typing value : "+value);//logging on the extent report
		}
		
	}
	
	public boolean isElementPresent(By by)//testng assertion using a wrapper method -> isElementPresent checking if a webelement is present
	{
		
		try {
			driver.findElement(by);
			log.info("Webelement found");
			return true;
		}catch(NoSuchElementException e) {
			
		
		return false;
		}
	}
	
	public static void verifyEquals(String expected,String actual) throws IOException//method to check title and doing a soft assert using try catch 
	{
		try
		{
			Assert.assertEquals(actual, expected);
		}
		catch(Throwable t)
		{
			TestUtil.captureScreenshot();
			//ReportNG
			Reporter.log("<br>"+"Verification failure: "+t.getMessage()+"<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=100 width=100></img></a>");
			Reporter.log("<br>");
			//Extentreports
				
			ExtentManager.captureScreenshot();
			
			String failureLogg="Verify failed";
			Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
			CustomListeners.testReport.get().log(Status.FAIL,failureLogg);
		}
	}
	
	static String textfield;
	
	public String text(String locator)
	{
		if(locator.endsWith("_CSS"))
		{
		textfield = driver.findElement(By.cssSelector(or.getProperty(locator))).getText();
		CustomListeners.testReport.get().log(Status.INFO,"Text is: "+textfield);
		}
		else if(locator.endsWith("_XPATH"))
		{
			textfield = driver.findElement(By.xpath(or.getProperty(locator))).getText();
			CustomListeners.testReport.get().log(Status.INFO,"Text is: "+textfield);
		}
		
		return textfield;
		
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value)
	{
		if(locator.endsWith("_CSS"))
		{
		dropdown = driver.findElement(By.cssSelector(or.getProperty(locator)));
		CustomListeners.testReport.get().log(Status.INFO,"typing value "+value);
		}
		else if(locator.endsWith("_XPATH"))
		{
			dropdown = driver.findElement(By.xpath(or.getProperty(locator)));
			CustomListeners.testReport.get().log(Status.INFO,"typing value "+value);
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		CustomListeners.testReport.get().log(Status.INFO,"Selecting from dropdown:"+locator+"entered value as :"+value);
		
	}
	
	@AfterSuite
	public void tearDown()
	{		
		if (driver!=null)
			{
			driver.quit();
			}
		log.info("Test execution ended");
	}

}
