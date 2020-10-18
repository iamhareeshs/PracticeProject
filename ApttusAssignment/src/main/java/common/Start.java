package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Start {
	public static WebDriver driver;
	//sample
	@BeforeSuite
	public void initialization() throws Exception {
		System.out.println("Method Started");
		System.setProperty("webdriver.chrome.driver", ".//drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterSuite
	public void termination() throws Exception {
		driver.close();
	}
}
