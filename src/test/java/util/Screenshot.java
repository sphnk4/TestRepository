package util;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import util.Common;

import java.io.IOException; 
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Screenshot {
	
	public static void TakeScreenshot(String Path) throws IOException{
		WebDriver driver = Common.getWebDriver();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  
			Path = Path + "failure.png";
		FileUtils.copyFile(scrFile, new File(Path));
		
	}

		public static String CreateFolderWithTimeStamp()
		
		{
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-hh.mm.ss");
		String folderName = formatter.format(today);
		
		folderName = "c://" + folderName;
		File dir = new File(folderName);
		boolean exists = dir.exists();
		System.out.println("Directory " + dir.getPath() + " exists: " + exists);
		if (exists) {
		 System.out.println("Main Folder Exist");
		}
		else
		{
			System.out.println("Main Folder Doesnt Exist");
			File directory = new File(folderName);
			directory.mkdir();
		}
		
	return folderName ;
	}


}
