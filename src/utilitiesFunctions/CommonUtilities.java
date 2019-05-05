package utilitiesFunctions;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class CommonUtilities {
	public static String basePath = System.getProperty("user.dir");
	public static String objectPath = basePath+"\\Objects\\objRepo.properties";
	public static String configPath = basePath+"\\Config\\config.properties";
	public static String resultsPath = basePath+"\\Results";
	public static String screenShotsPath = basePath+"\\ScreenShots";
	public static String testDataPath = basePath+"\\TestData\\";
	public static String WebDriverPath = basePath+"\\WebDrivers";
	public static String scriptPath = basePath+"\\BatchFiles";
	public WebDriver driver;
	
	/*************************************************
	 * Input: NO INPUTS
	 * Desc : Load Config Files before the Suites starts
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 *************************************************/
	@BeforeSuite
	public void loadConfig(String path, String key) throws FileNotFoundException, IOException {
		Properties prop = new Properties();
		prop.load(ReadFile(path));
		prop.getProperty(key);

	}
	
	
	/********************************************
	 * Desc: Read File Input Stream
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 ********************************************/
	public FileInputStream ReadFile(String filePath) throws FileNotFoundException{
		
		FileInputStream fis = new FileInputStream(new File(filePath));
		return fis;
	}
	
	
	/*******************************************************
	 *Inputs : Takes Browser Type IE, Chrome and Firefox.
	 *Desc :  Deletes all the Cache from Commandline
	 * @param browser
	 *******************************************************/
	@BeforeTest
	private void clearCache(String browser) throws Exception {
		Process run;
		
		if(browser.equalsIgnoreCase("IE")||browser.equalsIgnoreCase("InternetExplorer")) {
		run = Runtime.getRuntime().exec("cmd.exe /c Start "+scriptPath+"IEClearCache.bat");
		}
		
		else if (browser.equalsIgnoreCase("chrome")||browser.equalsIgnoreCase("GoogleChrome")){
		run = Runtime.getRuntime().exec("cmd.exe /c Start "+scriptPath+"ChromeClearCache.bat");
		}
		
		else if (browser.equalsIgnoreCase("firefox")||browser.equalsIgnoreCase("Fire Fox")){
		run = Runtime.getRuntime().exec("cmd.exe /c Start "+scriptPath+"FireFoxClearCache.bat");
		}
		else {
			System.out.println("Invalid Browser name. Please enter IE, Chrome and Firefox!!!");
		}
			
	
	}
	
	
	
	
	
	/*******************************************
	 * Input : NO INPUT
	 * Description : Closes and Quit all the open
	 *  drivers and closes all the instances.
	 * 
	 *******************************************/
	@AfterTest
	public void closeSession() {

		driver.close();
		driver.quit();

	}
	
	
	/***************************
	 * Desc: Takes the key as an input from ObjectReposetory file
	 * and returns a webelement of the respective locator type.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @param key
	 */
	public WebElement getObject(String key) throws FileNotFoundException, IOException {
		WebElement element;
		Properties prop = new Properties();
		prop.load(ReadFile(configPath));
		String locatorType;
		String locator;
		locatorType = prop.getProperty(key).split(":")[0].toLowerCase();
		locator = prop.getProperty(key).split(":")[1];
		
		switch (locatorType) {
		
		case "name":
			element = driver.findElement(By.name(locator));
			break;
		
		case "id":
			element = driver.findElement(By.id(locator));
			break;
			
		case "linktext":
			element=driver.findElement(By.linkText(locator));
			break;
			
		case "partiallinktext":
			element=driver.findElement(By.partialLinkText(locator));
			break;
			
		case "class":
			element=driver.findElement(By.className(locator));
			break;
			
		case "xpath":
			element=driver.findElement(By.xpath(locator));
			break;
			
		case "cssselector":
			element=driver.findElement(By.cssSelector(locator));
			break;
			
		case "tagname":
			element=driver.findElement(By.tagName(locator));
			break;
			
		default :
			return null;
			
		}
		
		return element;
	}
	
	
	/**********************
	 * Desc : Clicks on a WebElement
	 * @param element
	 */
	public void click(WebElement element) {
		element.click();
	}
	
	
	/**********************
	 * Desc: Send data on to a field
	 * @param element
	 * @param data
	 */
	public void sendkeys(WebElement element, String data) {
		element.sendKeys(data);
	}
	
	
	/*******************************
	 * Desc: Takes Browsername and
	 * driver initialization happens.
	 * @param browserName
	 *******************************/
	
	public void setBrowser(String browserName) {
		
		if(browserName.equalsIgnoreCase("IE")||browserName.equalsIgnoreCase("InternetExplorer")) {
			System.setProperty("webdriver.ie.driver", WebDriverPath+"\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			}
			
			else if (browserName.equalsIgnoreCase("chrome")||browserName.equalsIgnoreCase("GoogleChrome")){
				System.setProperty("webdriver.chrome.driver", WebDriverPath+"\\chromedriver.exe");
				driver = new ChromeDriver();
			}
			
			else if (browserName.equalsIgnoreCase("firefox")||browserName.equalsIgnoreCase("Fire Fox")){
				System.setProperty("webdriver.geeko.driver", WebDriverPath+"\\firefoxdriver.exe");
				driver = new FirefoxDriver();
			}
			else {
				System.out.println("Invalid Browser name. Please enter IE, Chrome and Firefox!!!");
			}
		
		
	}
	
	
	/******************
	 * Desc : Takes URL as an input and get navigated to the same.
	 * @param url
	 */
	public void navigate(String url) {
		driver.manage().window().maximize();
		driver.get(url);
	}
	
	

}
