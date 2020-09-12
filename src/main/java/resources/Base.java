package resources;

import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Base {

	public static RemoteWebDriver driver;
	public static Properties prop ; 
	public static WebDriverWait wait ; 
	public static String currentDirectory = System.getProperty("user.dir");
	public static List<String> toDoList = new ArrayList<>(Arrays.asList("Drink Water" , "Do some Workout","Get to Work","Sleep Well"));
	public static List<String> todosAfterAddition = new ArrayList<>();
	public static int todosLeft = toDoList.size();
	public static List<WebElement> listOfTodos = new ArrayList<WebElement>();
	public static List<String> todosAfterDeletion =new ArrayList<>();
	public void startApp() throws IOException {
		
		prop = new Properties();
		FileInputStream fileInput = new FileInputStream(currentDirectory+"\\properties\\data.properties");
		prop.load(fileInput);
		if(prop.getProperty("browser").equalsIgnoreCase("chrome")) {
		System.setProperty("webdriver.chrome.driver", currentDirectory+"\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		}
		else if(prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", currentDirectory+"\\drivers\\geckodriver.exe");
			driver = new  FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	public void click(WebElement ele) {
		try {
			wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", ele);
			}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public void clearAndType(WebElement ele , String data) {
		
		try {
			Thread.sleep(1000);
			ele.clear();
			ele.sendKeys(data);
			
		}
		catch(Exception e) {
			throw new RuntimeException();
		}
		
	}
	
	public WebElement locateElement(String locatorType , String value) {
		
		try {
			switch(locatorType.toLowerCase()) {
			case "id" : 
				return driver.findElementById(value);
			case "name":
				return driver.findElementByName(value);
			case "class":
				return driver.findElementByClassName(value);
			case "link":
				return driver.findElementByLinkText(value);
			case "xpath":
				return driver.findElementByXPath(value);
			
			}
		}
		catch(NoSuchElementException e) {
			 System.err.println("The element with locator: " +locatorType + "not found with value: " + value);
		}
		return null ;
	}
	
	public List<WebElement> locateElements(String type , String value){
		try {
			switch(type.toLowerCase()) {
			case "id" : 
				return driver.findElementsById(value);
			case "name":
				return driver.findElementsByName(value);
			case "class":
				return driver.findElementsByClassName(value);
			case "link":
				return driver.findElementsByLinkText(value);
			case "xpath":
				return driver.findElementsByXPath(value);
			
			}
		}
		catch(NoSuchElementException e) {
			 System.err.println("The element with locator: " +type + "not found with value: " + value);
		}
		return null ;
	}
	
	public String getElementText(WebElement ele) {
		String text = ele.getText();
		return text;
	}
	
	public String getAttribute(WebElement ele , String attribute) {
		String text = ele.getAttribute(attribute);
		return text;
	}
		
	
	
	
}
