package TestFolder;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.ConnectionType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EmulateNetworkSpeed {
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void Emulate() {
		long startTime = System.currentTimeMillis();
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector(".btn-success")).click();
		driver.findElement(By.xpath("//a[text() = 'Selenium']")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		System.out.println(driver.findElement(By.className("sp")).getText());
		long endTime = System.currentTimeMillis();
		System.out.println(endTime-startTime);
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		mockSpeed();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}

	public void mockSpeed() {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.emulateNetworkConditions(false, 3000, 56000, 564000, Optional.of(ConnectionType.WIFI)));
		devTools.addListener(Network.loadingFailed(), req ->
		{
			String text = req.getErrorText();
			System.out.println(text + " "+ req.getTimestamp());
		});
	}
}
