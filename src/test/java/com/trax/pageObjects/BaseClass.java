package com.trax.pageObjects;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.google.api.client.util.DateTime;
import com.trax.utilities.ReadConfig;

public class BaseClass {

	static ReadConfig readconfig = new ReadConfig();

	public static String reviewURL = readconfig.getApplicationURL();
	public static String trainingURL = readconfig.getApplicationTrainingURL();
	public static String partsURL = readconfig.getPartsDevURL();
	public static String testURL = readconfig.getTestURL();

	public static String userId = readconfig.getUserId();
	public static String password = readconfig.getPassword();

	public static String AdminUserId = readconfig.getAdminUserId();
	public static String AdminPassword = readconfig.getAdminPassword();

	public static String AccountingUserId = readconfig.getAccountingUserId();
	public static String AccountingPassword = readconfig.getPassword();

	public static String envName = readconfig.getEnvName();// for report
	public static String userName = readconfig.getUserName(); // for report

	public static WebDriver driver;
	public static Logger logger;

	@Parameters("browser")
	@BeforeClass
	public void setup(String br) throws MalformedURLException {
		logger = Logger.getLogger("TT");
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/Resources/" + "Log4j.properties");

		if (br.equals("chrome")) {
			// System.setProperty("webdriver.chrome.driver", "C:\\Users\\AtulKumar.Sharma\\Downloads\\Auto-TestRails\\Automation-TestRails\\Drivers\\chromedriver.exe");

			MutableCapabilities sauceOptions = new MutableCapabilities();
			sauceOptions.setCapability("username", System.getenv("oauth-sharma.atulkumar29-3deed"));
			sauceOptions.setCapability("access_key", System.getenv("7daa122c-0473-443b-8763-beeadddfc4f9"));
			sauceOptions.setCapability("name", "Atul TCs");
			sauceOptions.setCapability("browserVersion", "105");
			sauceOptions.setCapability("PlatformName", "Windows 10");

			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-extenstions");
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.addArguments("force-device-scale-factor=0.65");
			chromeOptions.addArguments("high-dpi-support=0.65");
			chromeOptions.addArguments("--ignore-ssl-errors=yes");
			chromeOptions.addArguments("--ignore-certificate-errors");
			chromeOptions.addArguments("--allow-insecure-localhost");
			chromeOptions.addArguments("--allow-running-insecure-content");
			chromeOptions.addArguments("--unsafely-treat-insecure-origin-as-secure");
			chromeOptions.setCapability("sauce:options", sauceOptions);

			//driver = new ChromeDriver(chromeOptions);
			//driver.get("https://yahoo.com");
			
			URL url = new URL("https://oauth-sharma.atulkumar29-3deed:7daa122c-0473-443b-8763-beeadddfc4f9@ondemand.us-west-1.saucelabs.com:443/wd/hub");
					RemoteWebDriver driver = new RemoteWebDriver(url, chromeOptions);
					
			driver.navigate().to("https://www.head2hire.com");

			System.out.println("Target URL of  application: " + driver.getCurrentUrl());

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			driver.manage().window().maximize();
		}
	}

	@AfterClass
	public void tearDown() {

		if (driver != null)
			driver.quit();
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Reports/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}



	public String captureScreen(String tname) throws IOException {

		String datetime = new SimpleDateFormat("yyyy-MMdd-hhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String screenshotName = tname + "_" + datetime;
		File target = new File(System.getProperty("user.dir") + "/Reports/" + screenshotName + ".png");

		System.out.println("target-->" + target.toString());
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
		return screenshotName;

	}

	public void copyLogoToReports() throws IOException {
		File sourceFile = new File(System.getProperty("user.dir") + "/Resources/" + "TT.svg");
		File targetDir = new File(System.getProperty("user.dir") + "/Reports/");
		System.out.println(sourceFile.toString());
		System.out.println(targetDir.toString());

		FileUtils.cleanDirectory(targetDir);
		FileUtils.copyFileToDirectory(sourceFile, targetDir);
		System.out.println(" logo file placed");

	}

	public String ACaptureScreen(String tname) throws IOException {
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			fileInputStreamReader = new FileInputStream(sourceFile);
			byte[] bytes = new byte[(int) sourceFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));

			// encodedBase64 = Base64.encodeBase64String(bytes);

			String screenShotDestination = System.getProperty("user.dir") + "\\Reports\\" + tname + ".png";

			File destination = new File(screenShotDestination);
			FileUtils.copyFile(sourceFile, destination);

		} catch (IOException e) {
			e.printStackTrace();
		}
//		return "data:image/png;base64," + encodedBase64;
		return "data:image/png;charset=utf-8;base64," + encodedBase64;
	}

	public static String getBase64Screenshot(String tname) throws IOException {
		String encodedBase64 = null;
		FileInputStream fileInputStream = null;
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Reports/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");

		try {
			fileInputStream = new FileInputStream(target);
			byte[] bytes = new byte[(int) target.length()];
			fileInputStream.read(bytes);
			// encodedBase64 = new String(Base64.encodeBase64(bytes));
			encodedBase64 = Base64.encodeBase64URLSafeString(bytes);

			System.out.println("Screenshot taken-64");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return encodedBase64;
	}

	public String randomString() {
		String str = RandomStringUtils.randomAlphanumeric(5).toUpperCase();

		String randomString = "TEST-" + str;
		return (randomString);
	}

	public static String randomNum() {
		String randomNum = RandomStringUtils.randomNumeric(4);
		return (randomNum);
	}

	public void jsClickExecutor(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", element);

	}

	public void jsScrollBottomOfPage() throws InterruptedException {
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		Thread.sleep(500);
	}

	public void jsScrollTopOfPage() throws InterruptedException {
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		jse.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		Thread.sleep(500);
	}

	public void jsSendCharsToElement(WebElement element, String text) {

		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String js = "arguments[0].setAttribute('value','" + text + "')";
		jse.executeScript(js, element);
	}

	public void jsBlurExecutor(WebElement element) {

		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String js = "arguments[0].focus(); arguments[0].blur(); return true";
		jse.executeScript(js, element);

	}

	public void jsScrollIntoViewExecutor(WebElement element) throws InterruptedException {

		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		String js = "arguments[0].scrollIntoView(true);";
		jse.executeScript(js, element);
		Thread.sleep(500);

	}

	public void uploadFile(String fileLocation) throws AWTException {

		try {

			StringSelection selection = new StringSelection(fileLocation);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null); // copy the above string to
																							// clip board

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.delay(2000);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL); // paste the copied string into the dialog box

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER); // enter

			Thread.sleep(5000);

		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	// stale element exception
	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public boolean isNotNullOrEmpty(WebElement element) {
		boolean result = false;
		String valueofElement = element.getAttribute("value");
		System.out.println("Value of element is:-->" + valueofElement);

		if (valueofElement != null && !valueofElement.trim().isEmpty())
			return result = true;
		else
			result = false;
		return result;

	}

	public boolean isDivNullOrEmpty(WebElement element) {
		boolean result = false;
		String valueofDivElement = element.getAttribute("innerHTML");
		System.out.println("valueofDivElement" + element + "is " + valueofDivElement);

		if (valueofDivElement != null && !valueofDivElement.trim().isEmpty())
			return result = true;
		else
			result = false;
		return result;

	}

	public String valueofDiv(WebElement element) {

		String valueofDivElement = element.getAttribute("innerHTML");
		System.out.println("valueofDivElement" + element + "is " + valueofDivElement);

		if (valueofDivElement != null && !valueofDivElement.trim().isEmpty())
			return valueofDivElement;
		else
			return null;

	}

	public String valueofText(WebElement element) {

		String valueofTextElement = element.getAttribute("value");
		System.out.println("valueofTextElement" + element + "is " + valueofTextElement);

		if (valueofTextElement != null && !valueofTextElement.trim().isEmpty())
			return valueofTextElement;
		else
			return null;

	}

	public boolean isBgColorWhite(WebElement element) {
		boolean result = false;
		String bgColorofElement = element.getCssValue("background-color").toString();

		System.out.println("valueofElement" + element + "is " + bgColorofElement);

		if (bgColorofElement.contains("rgba(255, 255, 255, 1)")) // check bgcolor is white or not
			return result = true;
		else
			result = false;
		return result;

	}

	public boolean isBgColorGreen(WebElement element) {
		boolean result = false;
		String bgColorofElement = element.getCssValue("background-color").toString();
		System.out.println("valueofElement" + element + "is " + bgColorofElement);

		if (bgColorofElement.contains("rgba(214, 255, 214, 1)")) // check bgcolor is green or not
			return result = true;
		else
			result = false;
		return result;

	}

	public String valueofSpan(WebElement element) {

		String valueofSpanElement = element.getText();
		System.out.println("valueofSpanElement" + element + "is " + valueofSpanElement);

		if (valueofSpanElement != null && !valueofSpanElement.trim().isEmpty())
			return valueofSpanElement;
		else
			return null;

	}

	public boolean isBgColorPink(WebElement element) {
		boolean result = false;
		String bgColorofElement = element.getCssValue("background-color").toString();

		System.out.println("valueofElement" + element + "is " + bgColorofElement);

		if (bgColorofElement.contains("lightpink") || bgColorofElement.contains("rgba(255, 182, 193, 1)")) // check
																											// bgcolor
																											// is pink
																											// or not
			return result = true;
		else
			result = false;
		return result;

	}

	public String selectTodayDate() {

		java.util.Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int today = cal.get(Calendar.DATE);
		return String.valueOf(today);
	}

	public void sendCharsToElement(WebElement element, String text) throws InterruptedException {
		String val = text;
		element.clear();

		for (int i = 0; i < val.length(); i++) {
			char c = val.charAt(i);
			String s = new StringBuilder().append(c).toString();
			element.sendKeys(s);

			Thread.sleep(120);
			WaitForAjax();
		}

	}

	public void sendHumanKeys(WebElement element, String text) throws InterruptedException {
		Random rand = new Random();
		element.clear();
		for (int i = 0; i < text.length(); i++) {
			try {
				Thread.sleep((int) (rand.nextGaussian() * 15 + 100));
			} catch (InterruptedException e) {
			}
			String s = new StringBuilder().append(text.charAt(i)).toString();
			element.sendKeys(s);

			Thread.sleep(250);
			WaitForAjax();
		}
		// element.sendKeys(Keys.TAB);
	}

	public void sendTKeys(WebElement element, String charString) throws InterruptedException {

		element.clear();
		element.sendKeys("");
		WaitForAjax();
		element.sendKeys(charString);
		WaitForAjax();
		jsBlurExecutor(element);

	}

	public void WaitForAjax() throws InterruptedException {

		while (true) {

			Boolean ajaxIsComplete = (Boolean) ((JavascriptExecutor) driver).executeScript(
					"return (window.jQuery != null) && (jQuery.active === 0) && (document.readyState == 'complete');");
			System.out.println("Ajax Status is ->" + ajaxIsComplete);

			if (ajaxIsComplete) {
				System.out.println("Ajax Call completed.");
				break;
			}
			Thread.sleep(180);
		}
	}

	public boolean WaitForAjaxToLoad() {

		// Explicit Wait with ExpectedCondition
		WebDriverWait wait = new WebDriverWait(driver, 30);
		System.out.println("Waiting Ajax to close");

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};
		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	public boolean waitForJSandJQueryToLoad() {

		// Explicit Wait with ExpectedCondition

		WebDriverWait wait = new WebDriverWait(driver, 30);

		// wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					// no jQuery present
					return true;
				}
			}
		};

		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};

		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

	public void fillInAutoComplete(WebElement element, String inputText, String ListByXpath)
			throws InterruptedException {

		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 18);
		 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
		 * ListByXpath)));
		 */

		List<WebElement> list = driver.findElements(By.xpath(ListByXpath));

		System.out.println("Auto Suggest List ::" + list.size());

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getText());

			if (list.get(i).getText().toUpperCase().contains(inputText)) {
				list.get(i).click();
				jsBlurExecutor(element);
				WaitForAjax();
				break;
			}

		}
	}

	public boolean isElementPresent(WebElement ele) {
		boolean ElementPresent = false;
		try {
			if (ele.isDisplayed()) {
				ElementPresent = true;
			}
		} catch (NoSuchElementException e) {
			System.out.println("Element is not present =>" + e);
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is stale =>" + e);
		}

		return ElementPresent;
		// assertTrue(!isElementPresent(By.linkText("Empresas en Misión")));
		// assertFalse(...)
		// Boolean exist = driver.findElements(By.whatever(whatever)).size() == 0;

	}

	public void chkTableRows(WebElement table, int rowsExpected) {

		List<WebElement> rows = table.findElements(By.tagName("tr"));
		System.out.println("Total Number of Rows = " + rows.size());
		Assert.assertNotNull(rows, "There is no rows in the table");
		// Row count needs to be with 1 as table has header row.
		Assert.assertTrue(rows.size() >= (rowsExpected + 1));

	}

	public WebElement waitForElementToBeRefreshedAndClickable(WebDriver driver, WebElement element) {

		System.out.println("Inside wait For Element To Be Refreshed And Clickable");
		return new WebDriverWait(driver, 30)
				.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(element)));
	}

	public WebElement waitForElementToBeRefreshedAndVisible(WebDriver driver, WebElement element) {
		return new WebDriverWait(driver, 30)
				.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
	}

}