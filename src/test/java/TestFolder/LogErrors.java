package TestFolder;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;

public class LogErrors{
	private ChromiumDriver driver;
	private DevTools devTools;
	

	@Test
	public void errorLogging(){
		driver.get("https://rahulshettyacademy.com/angularAppdemo/");
		driver.findElement(By.cssSelector(".btn-success")).click();
		driver.findElement(By.xpath("//a[text() = 'Selenium']")).click();
		driver.findElement(By.cssSelector(".add-to-cart")).click();
		driver.findElement(By.linkText("Cart")).click();
		driver.findElement(By.id("exampleInputEmail1")).clear();
		driver.findElement(By.id("exampleInputEmail1")).sendKeys("2");
		
		  LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
		  List<LogEntry> log = entries.getAll();
		  System.out.println(log.size());
		  for(LogEntry e: log) {
			  System.out.println(e.getMessage());
		  }
		  
	}

	@BeforeTest
	public void beforeTest() {
		driver = new ChromeDriver();
		devTools = driver.getDevTools();
		devTools.createSession();
		BlockRequest(ImmutableList.of("*.jpg", "*.css"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		
	}

	@AfterTest
	public void afterTest() {
		devTools.disconnectSession();
		driver.quit();
	}
	public void BlockRequest(ImmutableList<String> types) {
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.send(Network.setBlockedURLs(types));
	}

}
