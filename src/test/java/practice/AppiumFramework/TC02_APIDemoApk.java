package practice.AppiumFramework;

import pageObjects.Preferences;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import pageObjects.HomePage;

public class TC02_APIDemoApk extends Base {
	
	
	@Test(dataProvider="InputData", dataProviderClass=TestData.class)
	public void apiDemo(String input) throws Exception
	{
		service=startServer();
		AndroidDriver<AndroidElement> driver = capabilities("apiDemo");
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		HomePage page = new HomePage(driver);
		
		page.preferences.click();
	     
	     Preferences pf=new Preferences(driver);
	    
	     // driver.findElementByXPath("//android.widget.TextView[@text='3. Preference dependencies']").click();
	     pf.dependencies.click();
	     driver.findElementById("android:id/checkbox").click();
	     driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();
	     driver.findElementByClassName("android.widget.EditText").sendKeys(input);
	    // driver.findElementsByClassName("android.widget.Button").get(1).click();
	     pf.buttons.get(1).click();
	     service.stop();
	}

}
