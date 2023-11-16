package TestFolder;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.fetch.Fetch;
import org.openqa.selenium.devtools.v119.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v119.network.model.ErrorReason;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FailedNetworkReq {
	private ChromiumDriver driver;
	private DevTools devTools;

	// This test is to validate what will the output we see when the network
	// resquest failed.
	@Test
	public void FailingReq() {
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*= 'library']")).click();
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		FailNetworkRequest();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}

	public void FailNetworkRequest() {
		// Create request pattern object
		devTools.send(Fetch.enable(
				Optional.of(List.of(new RequestPattern(Optional.of("*GetBook*"), Optional.empty(), Optional.empty()))),
				Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			devTools.send(Fetch.failRequest(request.getRequestId(), ErrorReason.FAILED));
		});
	}

}
