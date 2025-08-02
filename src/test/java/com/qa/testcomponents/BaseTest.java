package com.qa.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.pageobjects.LandingPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver intializeDriver() throws IOException {

		// Properties Class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\com\\qa\\resources\\GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("broswer") != null ? System.getProperty("broswer")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));  //Help us to run full screen

		} else if (browserName.contains("firefox")) {

		} else if (browserName.contains("edge")) {

		}

		// Set implicit wait and maximize window
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws Exception {
		// Read JSON file to String
		String jsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

		// Convert String to List<HashMap>
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports" + testCaseName + ".png");
		FileUtils.copyFile(srcFile, file);
		return testCaseName;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = intializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
		;
	}
}
