package projectpage;

import java.net.UnknownHostException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import util.Common;
import util.PropertyFileRead;

public class TestngAnnotations {

	@BeforeMethod(alwaysRun = true)
	public void Beforem()
	{
		System.out.println("TestCase Starts");
		try {
			Common.startBrowser(PropertyFileRead.FileRead("ProjectData.properties","BrowserType"), false);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Common.driver.manage().window().maximize();
	}

	@AfterMethod(alwaysRun = true)
	public void AfterTest() throws UnknownHostException
	{
	System.out.println("TestCase Ends");
		Common.driver.quit();
	}
}
