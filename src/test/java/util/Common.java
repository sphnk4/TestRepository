package util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class Common {
	public static WebElement webelement;
	public static List<WebElement> webelements = null;
	public static WebDriver driver = null;
	public static int defaultBrowserTimeOut = 30;
	public static List<String> windowHandlers;

	
	public static WebDriver startBrowser(String browserName, boolean FirefoxBrowserProxy)
			throws UnknownHostException {
	
		deleteTempFile();

		if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("iexplorer")) {
			System.setProperty("webdriver.ie.driver",
			System.getProperty("user.dir") + "\\IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities
					.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver(capabilities);
		} else if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\chromedriver.exe");
			driver = new ChromeDriver();			
		}

		driver.manage().timeouts()
				.implicitlyWait(defaultBrowserTimeOut, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		
		if (browserName.equalsIgnoreCase("iexplorer"))
			Common.SwitchAlert();

		if (windowHandlers == null)
			windowHandlers = new LinkedList<String>();
		else
			windowHandlers.clear();

		windowHandlers.add(driver.getWindowHandle());
		driver.manage().window().maximize();
		return driver;
	
	}


	public static boolean SwitchAlert() {
		boolean Flag = false;

		try {
			if (driver.switchTo().alert() != null) {
				driver.switchTo().alert().accept();
				Flag = true;
			}

		}

		catch (NoAlertPresentException e) {
			
		}
		return Flag;

	}
	public static void closeBrowser() {
	
			
			Common.driver.close();
	}
	
	
	public static void shutDownDriver() {
		if (driver != null)
			driver.quit();
	}
	
	
	public static WebDriver getWebDriver() {
		return driver;
	}

	public static void deleteTempFile() throws UnknownHostException {
		String property = "java.io.tmpdir";
		String temp = System.getProperty(property);
		File directory = new File(temp);
		try {
			delete(directory);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) { 
			if (file.list().length == 0) {
				file.delete();
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}
		} else {
			file.delete();
		}
	}

	public static WebElement findElement(String locator) {
		
		if (locator != null) {
			String[] arrLocator = locator.split("==");
			String locatorTag = arrLocator[0].trim();
			String objectLocator = arrLocator[1].trim();
			try {
				if (locatorTag.equalsIgnoreCase("id")) {
					webelement = driver.findElement(By.id(objectLocator));
					highlightElement(driver, webelement);
				} else if (locatorTag.equalsIgnoreCase("name")) {
					webelement = driver.findElement(By.name(objectLocator));
					highlightElement(driver, webelement);
				} else if (locatorTag.equalsIgnoreCase("xpath")) {
					webelement = driver.findElement(By.xpath(objectLocator));
					highlightElement(driver, webelement);
				} else if (locatorTag.equalsIgnoreCase("linkText")) {
					webelement = driver.findElement(By.linkText(objectLocator));
					highlightElement(driver, webelement);
				} else if (locatorTag.equalsIgnoreCase("class")) {
					webelement = driver
							.findElement(By.className(objectLocator));
					highlightElement(driver, webelement);
				} else if (locatorTag.equalsIgnoreCase("css")) {
					webelement = driver.findElement(By
							.cssSelector(objectLocator));
					highlightElement(driver, webelement);
				} else {
					String error = "Please Check the Given Locator Syntax :"
							+ locator;
					error = error.replaceAll("'", "\"");
				
					return null;
				}
			} catch (Exception exception) {
				String error = "Please Check the Given Locator Syntax :"
						+ locator;
				error = error.replaceAll("'", "\"");
								exception.printStackTrace();
				return null;
			}
		}
		return webelement;
	}

	
	public static void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				element, "border: 2px solid DeepPink;");
	}



	public static void doubleClick(WebElement element) {
		if ((driver != null) && (element != null))
			(new Actions(driver)).doubleClick(element).build().perform();
	}

	public static boolean isElementPresent(String locator) {
		List<WebElement> arrElements = null;
		arrElements = Common.findElements(locator);
		if (arrElements.size() > 0 && arrElements != null) {
			
			return true;
		}
		else
			System.out.println("Element not found");
		

		return false;
	}

	public static void ElementWait(String Locator) throws InterruptedException{

		WebElement element = null;
		for(int i=0;i<60;i++){
			try{
				element=Common.findElement(Locator);

			}catch(Exception e){
			}

			if(element!=null || element != null)
				return;
			Thread.sleep(3000);
			System.out.println("Waiting");
		}
	}
	

	public static void WaitForLoad(int TimeMillSec) throws InterruptedException{
			Thread.sleep(TimeMillSec);
	}

	public static List<WebElement> findElements(String locator) {
		if (locator != null) {
			String[] arrLocator = locator.split("==");
			String locatorTag = arrLocator[0].trim();
			String objectLocator = arrLocator[1].trim();
			if (locatorTag.equalsIgnoreCase("id")) {
				webelements = driver.findElements(By.id(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("name")) {
				webelements = driver.findElements(By.name(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("xpath")) {
				webelements = driver.findElements(By.xpath(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("linkText")) {
				webelements = driver.findElements(By.linkText(objectLocator));
			} else if (locatorTag.equalsIgnoreCase("class")) {
				webelements = driver.findElements(By.className(objectLocator));
			} else {
				System.out.println("Please Check the Locator Syntax Given :"
						+ locator);
				return null;
			}
		}
		return webelements;
	}

	public static void mousehovering(String locator) {
		WebElement mouseOverElement = findElement(locator);
		Actions builder = new Actions(driver);  
		Action mouseOver =builder.moveToElement(mouseOverElement).build(); 
		mouseOver.perform(); 
	}
		
	public static void PressShiftTab() throws AWTException {
		Robot robot = new Robot();
		robot.delay(3000);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB); 
		robot.keyRelease(KeyEvent.VK_SHIFT);
	}
		
	public static void PressTab() throws AWTException {
		Robot robot = new Robot();
		robot.delay(3000);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB); 
	}

	public static String getAttribute(String locator, String attributeName) {
		String attributeValue = null;
		try {
			WebElement element = Common.findElement(locator);
			if (element != null)
				attributeValue = element.getAttribute(attributeName);
			element = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return attributeValue;
	}
	
	public static void clearElement(String locator) {
			try {
			WebElement element = Common.findElement(locator);
			element.clear();
			element = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

	public static void enterText(String locator, String value) {
		try {
			WebElement element = Common.findElement(locator);
			element.sendKeys(value);
			element = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void click(String locator) {
		try {
			WebElement element = Common.findElement(locator);
			if (element != null)
				element.click();
			else
				System.out.println("Element Is NULL");
			element = null;		

		} catch (Exception e) {
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
		}
		
	}
	
	public static String getElementText(String locator) {
		WebElement element;
		String text = null;
		try {
			element = Common.findElement(locator);
			if (element != null)
				text = element.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		element = null;

		return text;
	}
	
	public static void driverInitialize(WebDriver webDriver) {
		Common.driver = webDriver;
	}
	
	public static void GoToUrl(String url) {
		try {
			driver.get(url);
			} catch (Exception e) {

		}
	}
	
	public static boolean isCheckboxChecked(String locator) {
		WebElement element = Common.findElement(locator);
		if (element.isSelected())
			return true;
		else
			return false;
	}

	public static void CheckCheckBox(String Locator, String Value)
	{
		List<WebElement> oCheckBox = Common.findElements(Locator);
  		int iSize = oCheckBox.size();

		for(int i=0; i < iSize ; i++ ){
			String sValue = oCheckBox.get(i).getAttribute("value");
			if (sValue.equalsIgnoreCase(Value))
			{
				oCheckBox.get(i).click();
				break;
			}
		}
	}
	
	public static boolean selectDropDownValue(String locator, String value) {
		try {
			WebElement element = Common.findElement(locator);

			if (element != null) {
				Select selectBox = new Select(element);
				selectBox.selectByValue(value);
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
	
	public static boolean selectDropDrownByIndex(String locator, int Index) {
		try {

			if (locator != null) {
			
				WebElement DropdownElement = Common.findElement(locator);
				Select SelectIndex = new Select(DropdownElement);
				SelectIndex.selectByIndex(Index);
			}
			return false;
		} catch (Exception e) {
			System.out
					.println(" Error occured while selecting the option in the select box *"
							+ locator + " ***" + e.getMessage());
			return false;
		}
	}

	/*public static WebDriver proxySetting()
{
	String serverIP=PropertyFileRead.FileRead("DBDetail.properties","proxyHort"); 
	String port= PropertyFileRead.FileRead("DBDetail.properties","proxyPort");
	WebDriver newDriver;
	FirefoxProfile profile = new FirefoxProfile();  
	profile.setPreference("network.proxy.type",1);  
	profile.setPreference("network.proxy.ftp",serverIP);  
	profile.setPreference("network.proxy.http",serverIP);  
	profile.setPreference("network.proxy.socks",serverIP);  
	profile.setPreference("network.proxy.ssl",serverIP);  
	profile.setPreference("network.proxy.ftp_port",port);  
	profile.setPreference("network.proxy.http_port",port);  
	profile.setPreference("network.proxy.socks_port",port);  
	profile.setPreference("network.proxy.ssl_port",port);  
	newDriver = new FirefoxDriver(profile);

	return newDriver;
	}*/
}
