package TestFolder;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class BlockedNetworkRequest {
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void BlockingNetworkReqTest() {
//		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
//		driver.findElement(By.cssSelector(".btn-success")).click();
//		driver.findElement(By.xpath("//a[text() = 'Selenium']")).click();
//		driver.findElement(By.cssSelector(".add-to-cart")).click();
//		System.out.println(driver.findElement(By.className("sp")).getText());
		driver.get("https://flowcv.com");
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		BlockRequest(ImmutableList.of("*.webp", "*.svg"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
//		driver.quit();
	}
	
	public void BlockRequest(ImmutableList<String> types) {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.setBlockedURLs(types));
	}
}
