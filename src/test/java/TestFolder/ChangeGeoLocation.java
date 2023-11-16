package TestFolder;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.emulation.Emulation;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ChangeGeoLocation {
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void LocationOverride() {
		driver.get("https://my-location.org/");
		System.out.println(driver.findElement(By.id("address")).getText());
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		devTools.send(Emulation.setGeolocationOverride(Optional.of(40), Optional.of(3), Optional.of(1)));
//		Map<String, Object> parameters = new HashMap<>();
//		parameters.put("latitude", 48);
//		parameters.put("longitude", 2);
//		parameters.put("accuracy", 1);
//		driver.executeCdpCommand("Emulation.setGeolocationOverride", parameters);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}

}
