package TestFolder;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SettingDimensions {
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void setDimensionTest() {
		driver.findElement(By.xpath("//button[@class = 'navbar-toggler']")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(driver.findElement(By.xpath("//a[text() = ' Download']")).getText());
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
//		devTools.send(Emulation.setDeviceMetricsOverride(768, 1024, 50, true, Optional.empty(), Optional.empty(),
//				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
//				Optional.empty(), Optional.empty()));
		
		// DevTools sends work only if version matchess excaltly. 

		Map<String, Object> deviceMetrics = new HashMap<>();
		deviceMetrics.put("width", 414);
		deviceMetrics.put("height", 896);
		deviceMetrics.put("mobile", true);
		deviceMetrics.put("deviceScaleFactor", 50);
		
		
		driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://anubhavgupta1120.github.io/Tindog-Design-A-dog-Tinder-/");
	}

	@AfterTest
	public void afterTest() {
		 driver.quit();
	}

}
