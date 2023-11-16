package TestFolder;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.time.Duration;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.fetch.Fetch;
import org.testng.annotations.AfterTest;

public class MockNetworkReq {
	// This is the test to mock the the network req and ovserve the output
	private ChromiumDriver driver;
	private DevTools devTools;

	@Test
	public void RequestMocking() {
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector("button[routerlink*= 'library']")).click();
		System.out.println(driver.findElement(By.tagName("p")).getText());
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		MockNetwork();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}
	
	public void MockNetwork() {
		devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
		devTools.addListener(Fetch.requestPaused(), request -> {
			if (request.getRequest().getUrl().contains("shetty")) {
				String newurl = request.getRequest().getUrl().replace("=shetty", "=BadGuy");
				//System.out.println(newurl);
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(newurl),
						Optional.of(request.getRequest().getMethod()), Optional.empty(), Optional.empty(),
						Optional.empty()));
			} else {
				devTools.send(Fetch.continueRequest(request.getRequestId(), Optional.of(request.getRequest().getUrl()),
						Optional.of(request.getRequest().getMethod()), Optional.empty(), Optional.empty(),
						Optional.empty()));
			}
		});
	}

}
