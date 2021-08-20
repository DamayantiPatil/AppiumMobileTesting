package practice.AppiumFramework;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {

	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	
	public AppiumDriverLocalService startServer() throws Exception
	{
		boolean flag = checkIfServerIsRunnning(4723);
		
		Thread.sleep(5000);
		
		if(!flag)
		{
			service=AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
		return service;
	}
	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);

			serverSocket.close();
		} catch (IOException e) {
			//If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}
	
	public static void startEmulator() throws IOException, InterruptedException
	{
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"/src/main/java/resources/startEmulator.bat");
		Thread.sleep(6000);
	}

	public static AndroidDriver<AndroidElement> capabilities(String appName) throws MalformedURLException, Exception {

		String filePath = System.getProperty("user.dir");
		FileInputStream fis = new FileInputStream(filePath + "/src/main/java/practice/AppiumFramework/config.properties");
		Properties prop = new Properties();
		prop.load(fis);
		prop.get(appName);

		File appDir = new File("src"); 
		File app = new File(appDir,(String) prop.get(appName));

		DesiredCapabilities capabilities = new DesiredCapabilities();

		//String device = (String) prop.get("device");
		
		String device = System.getProperty("deviceName");
		
		//Start emulator
		 if(device.contains("Emulator"))
		  {
			 startEmulator();
		  }

		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		//UIAutomator2 - Testing Framework
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);

		URL url=new URL("http://127.0.0.1:5554/wd/hub");
		driver = new AndroidDriver<AndroidElement>(capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		return driver;
	}
	
	public static void getScreenshot(String s) throws IOException
	{
		File scrfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile,new File(System.getProperty("user.dir")+"\\"+s+".png"));
	}

}	

/*
 * @Test public void launchBrowser() throws Exception {
 * mobiledriver.get("http://appium.io/"); Thread.sleep(3000);
 * Assert.assertEquals(mobiledriver.getCurrentUrl(), "http://appium.io/",
 * "URL Mismatch"); Assert.assertEquals(mobiledriver.getTitle(),
 * "Appium: Mobile App Automation Made Awesome.", "Title Mismatch"); }
 * 
 * @AfterTest public void afterTest( ) { mobiledriver.quit(); }
 */
