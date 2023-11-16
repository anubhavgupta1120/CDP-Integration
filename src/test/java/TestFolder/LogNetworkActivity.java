package TestFolder;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.Request;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LogNetworkActivity {
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void LoggingTest() {
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*= 'library']")).click();
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		logging();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}

	public void logging() {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		// Event - Resquest will be sent

		devTools.addListener(Network.requestWillBeSent(), request -> {
			@SuppressWarnings("unused")
			Request req = request.getRequest();
			// System.out.println(req.getUrl());
		});

		// Event will get Fired
		devTools.addListener(Network.responseReceived(), response ->

		{
			org.openqa.selenium.devtools.v119.network.model.Response res = response.getResponse();
//			System.out.println(res.getUrl());
			// System.out.println(res.getStatus());
			if (res.getStatus().toString().startsWith("4")) {
				System.out.println(res.getUrl() + " is failing with status code " + res.getStatus());
			}

		});
	}

}
