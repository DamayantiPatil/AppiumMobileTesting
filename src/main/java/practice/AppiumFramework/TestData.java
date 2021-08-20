package practice.AppiumFramework;

import org.testng.annotations.DataProvider;

public class TestData {
	
	@DataProvider(name="InputData")
	public Object[][] getDataForEditField() throws Exception
	{
		Thread.sleep(5000);
		Object[][] obj = new Object[][]
		{
			{"hello"},{"@#$%"}
		};
		
		return obj;
	}
}
