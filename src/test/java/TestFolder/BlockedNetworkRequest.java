package TestFolder;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.AfterTest;

public class BlockedNetworkRequest {
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void BlockingNetworkReqTest() {
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}

}
