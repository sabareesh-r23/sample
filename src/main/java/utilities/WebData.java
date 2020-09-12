package utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import resources.Base;
import validators.Validations;

public class WebData extends Base {

	Validations validator = new Validations();

	public void openURL() throws IOException {

		startApp();
		driver.get(prop.getProperty("url"));
		

	}

	public void add_a_TODO() {

		WebElement inputField = locateElement("xpath", prop.getProperty("textField"));
		Actions enterValues = new Actions(driver);
		for (String listOfTask : toDoList) {

			clearAndType(inputField, listOfTask);
			enterValues.sendKeys(Keys.ENTER).build().perform();

		}

	}

	public void verify_To_do_AreAdded() {

		listOfTodos = locateElements("xpath", prop.getProperty("listofTodo"));
		for (WebElement listOfTask : listOfTodos) {
			todosAfterAddition.add(getElementText(listOfTask));
		}

		validator.verifyList(todosAfterAddition, toDoList, "Verification of List");

		

	}

	public void make_some_task_completed(String[] tasks) {

		listOfTodos = locateElements("xpath", prop.getProperty("listofTodo"));
		for (int i = 0; i < tasks.length; i++) {

			for (int j = 0; j < listOfTodos.size(); j++) {

				if (listOfTodos.get(j).getText().equalsIgnoreCase(tasks[i])) {
					todosLeft--;
					//String check = "//label[text()='" + listOfTodos.get(j).getText() + "']//preceding::input[1]";

					String check1 = "//label[text()='" + listOfTodos.get(j).getText()
							+ "']//preceding::input[@type='checkbox'][1]";
					String strike = "//label[text()='" + listOfTodos.get(j).getText() + "']//ancestor::li";

					WebElement checkBox = locateElement("xpath", check1);
					checkBox.click();
					WebElement strikethrough = locateElement("xpath", strike);
					String attributes = getAttribute(strikethrough, "class");
					validator.verifyString(attributes, "ng-scope completed", "Verification of Strikethrough");

				}

			}

		}
	}

	public void validateTaskLeft() {

		WebElement taskLeft = locateElement("xpath", prop.getProperty("taskleft"));
		int taskPending = Integer.parseInt(getElementText(taskLeft));
		validator.verifyInt(taskPending, todosLeft, "Verification of No. of todos left");
	}

	public void delete_a_todo(String deleteTask[]) throws InterruptedException {

		//todosAfterDeletion = todosAfterAddition.stream().collect(Collectors.toList());
		
		for (String object : todosAfterAddition) {
			todosAfterDeletion.add(object);
		}

		for (int j = 0; j < todosAfterAddition.size(); j++) {
			for (int i = 0; i < deleteTask.length; i++) {

				if (todosAfterAddition.get(j).equalsIgnoreCase(deleteTask[i])) {
					String destroy = "//label[text()='" + todosAfterAddition.get(j)
							+ "']//following::button[@class='destroy'][1]";
					
					WebElement deleteTOdo = locateElement("xpath", destroy);
					String completed = "//label[text()='" + todosAfterAddition.get(j) + "']//ancestor::li[1]";
					WebElement strikethrough = locateElement("xpath", completed);
					String attributes = getAttribute(strikethrough, "class");

					String removeText = todosAfterAddition.get(j);
					todosAfterDeletion.remove(removeText);
					if (!attributes.equalsIgnoreCase("ng-scope completed")) {

						todosLeft--;
					}

					String beforeClick = "//label[text()='" + todosAfterAddition.get(j) + "']";
					WebElement point1 = locateElement("xpath", beforeClick);
					new Actions(driver).moveToElement(point1).pause(java.time.Duration.ofSeconds(3)).click(deleteTOdo)
							.build().perform();

				}
			}

		}
			
	}

	public void validateAfterDeletion() {
		locateElement("xpath", prop.getProperty("home")).click();
		List<String> validateThedeletedList = new ArrayList<>();
		
		boolean elementPresentinHomepage = false;
		try {
			elementPresentinHomepage = driver.findElementByXPath(prop.getProperty("listofTodo")).isDisplayed();
		} catch (NoSuchElementException e) {
			validateThedeletedList.add("");
		}
		
		if(elementPresentinHomepage==true) {
			List<WebElement> home_elements = locateElements("xpath", prop.getProperty("listofTodo"));
			for (WebElement element : home_elements) {
				validateThedeletedList.add(getElementText(element));
			}
			
		}
		
		validator.verifyList(validateThedeletedList, todosAfterDeletion, "Verification of DeletedList");
		
	}

	public void fetchCompletedAndValidate() {
		locateElement("xpath", prop.getProperty("home")).click();
		List<String> taskListinHomePage = new ArrayList<>();
		List<String> taskListinCompleted = new ArrayList<>();

		boolean elementPresentinHomePage = false;
		try {
			elementPresentinHomePage = driver.findElementByXPath(prop.getProperty("completedTask")).isDisplayed();
		} catch (NoSuchElementException e) {
			taskListinHomePage.add("");
		}
		WebElement completeButton = locateElement("xpath", prop.getProperty("completedButton"));
		completeButton.click();

		boolean elementPresentinCompleted = false;
		try {
			elementPresentinCompleted = driver.findElementByXPath(prop.getProperty("listofTodo")).isDisplayed();
		} catch (NoSuchElementException e) {
			taskListinCompleted.add("");
		}

		if (elementPresentinHomePage == true && elementPresentinCompleted == true) {

			

			List<WebElement> completed_elem = locateElements("xpath", prop.getProperty("completedTask"));
			for (WebElement element : completed_elem) {
				taskListinHomePage.add(getElementText(element));
			}

			List<WebElement> completed_elements = locateElements("xpath", prop.getProperty("listofTodo"));
			for (WebElement element : completed_elements) {
				taskListinCompleted.add(getElementText(element));
			}

		}
		validator.verifyList(taskListinHomePage, taskListinCompleted, "Verification of Completed stuff");

	}

	public void fetchActiveTodoAndValidate() {
		
		locateElement("xpath", prop.getProperty("home")).click();
		List<String> ActiveListinHomePage = new ArrayList<>();
		List<String> taskListinActive = new ArrayList<>();

		boolean elementPresentinHomePage = false;
		try {
			elementPresentinHomePage = driver.findElementByXPath(prop.getProperty("activeTask")).isDisplayed();
		} catch (NoSuchElementException e) {
			ActiveListinHomePage.add("");
		}
		WebElement activeButton = locateElement("xpath", prop.getProperty("activeButton"));
		activeButton.click();
		boolean elementPresentinActive = false;
		try {
			elementPresentinActive = driver.findElementByXPath(prop.getProperty("listofTodo")).isDisplayed();
		} catch (NoSuchElementException e) {
			taskListinActive.add("");
		}

		
		if(elementPresentinHomePage == true && elementPresentinActive == true) {
			
			
			List<WebElement> active_elem = locateElements("xpath", prop.getProperty("activeTask"));
			for (WebElement element : active_elem) {
				ActiveListinHomePage.add(getElementText(element));
			}

			

			
			List<WebElement> active_elements = locateElements("xpath", prop.getProperty("listofTodo"));
			for (WebElement element : active_elements) {
				taskListinActive.add(getElementText(element));
			}

			
			
		}

		validator.verifyList(ActiveListinHomePage, taskListinActive, "Verification of Active stuff");

	}

}
