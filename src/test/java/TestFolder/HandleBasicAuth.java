package TestFolder;

import java.net.URI;
import java.util.function.Predicate;

import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class HandleBasicAuth {
	private ChromiumDriver driver;

	@Test
	public void handleAuth() {
		driver.get("https://httpbin.org/basic-auth/foo/bar");
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		// predicate functions
		Predicate<URI> uriPredicate = uri -> uri.getHost().contains("httpbin.org");
		// parse driver in hasAuthentication
		((HasAuthentication) driver).register(uriPredicate, UsernameAndPassword.of("foo", "bar"));

	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
