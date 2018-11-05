package testscripts;
import java.awt.AWTException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import projectpage.TestngAnnotations;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.Assert;

import util.*;

public class TC001QPLogin  extends TestngAnnotations {

	@BeforeMethod
	public void BeforeTest() throws UnknownHostException
	{
		System.out.println("TestCase Starts");
		Common.startBrowser(PropertyFileRead.FileRead("ProjectData.properties","BrowserType"), false);
		Common.driver.manage().window().maximize();
		Common.driver.manage().deleteAllCookies();
	}
	
	@AfterMethod
	public void AfterTest() throws UnknownHostException
	{
	System.out.println("TestCase Ends");
		Common.driver.quit();
	}
	
	@Test
	public void Login() throws InterruptedException{
		System.out.println("Start Test");
		//Navigate to QPlatform
		Common.GoToUrl(PropertyFileRead.FileRead("ProjectData.properties","TS01URL"));
		Common.WaitForLoad(2000);
		//Enter user credentials
		if(Common.isElementPresent(PropertyFileRead.FileRead("ProjectData.properties","txtUserName")))
		{
			Common.click(PropertyFileRead.FileRead("ProjectData.properties","txtUserName"));
			Common.enterText(PropertyFileRead.FileRead("ProjectData.properties","UsertName"),"srinip");
		}
		
		if(Common.isElementPresent(PropertyFileRead.FileRead("ProjectData.properties","txtUserPwd")))
		{		
			Common.click(PropertyFileRead.FileRead("ProjectData.properties","txtUserPwd"));
			Common.enterText(PropertyFileRead.FileRead("ProjectData.properties", "Password"), "Quad1admin");
			
		}
		//Click on 'Login' button
		if(Common.isElementPresent(PropertyFileRead.FileRead("ProjectData.properties","btnLogin")))
		{
		Common.click(PropertyFileRead.FileRead("ProjectData.properties","btnLogin"));
		}
	}
}
