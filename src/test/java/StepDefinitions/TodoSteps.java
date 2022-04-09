package StepDefinitions;

import PageObjects.HomePage;
import Utils.Base;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.util.List;

public class TodoSteps extends Base {

    Logger log= LogManager.getLogger(TodoSteps.class.getName());

    @Before
    public void initialize_the_browser_with_chrome() throws Throwable {
        driver=initializeWebDriver();
        log.info("Initialised browser");
        driver.manage().window().maximize();
        driver.get(prop.getProperty("url"));
    }


    @After(order=1)
    public void capture_screenshot(Scenario scenario) throws Throwable {
        //validate if scenario has failed and generate screenshot on failure
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "image");
        }
    }

    @After(order=0)
    public void logout_and_close_browser() throws Throwable {
        Thread.sleep(10000);
        driver.close();
        log.info("Closed browser");
    }

    private List<String> toDoInputList;
    private String itemLeftCount;


    @Given("User is on Landing Page")
    public void user_is_on_landing_page() {
        HomePage hp=new HomePage(driver);
        wait.until(ExpectedConditions.visibilityOf(hp.getPageHeader()));
        log.info("User is on Landing Page");
    }


    @Given("No Todo items are present")
    public void no_todo_items_are_present() {
    }


    @When("User enters todo items")
    public void User_enters_todo_items() {
    }

    @When("User enters item in todo list")
    public void user_enters_item_in_todo_list(DataTable dataTable) throws InterruptedException {
        HomePage hp=new HomePage(driver);
        wait.until(ExpectedConditions.visibilityOf(hp.getPageHeader()));
        toDoInputList =dataTable.asList();
        for(int i=0;i<toDoInputList.size();i++)
        {
            hp.getToDoInputField().sendKeys(toDoInputList.get(i));
            hp.getToDoInputField().sendKeys(Keys.ENTER);
        }
        wait.until(ExpectedConditions.visibilityOf(hp.getAllTabElement()));
        Thread.sleep(1000);
    }

    @Then("Entered Item appears in All and Active ToDo list")
    public void entered_item_appears_in_all_and_active_to_do_list() {
        HomePage hp=new HomePage(driver);
        if (hp.getAllTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flag = true;
            List<WebElement> listOfItems = hp.getItemList();
            for (WebElement element : listOfItems) {
                if (!(element.getAttribute("class").equalsIgnoreCase("todo"))) {
                    flag = false;
                }
            }
            Assert.assertTrue(flag);
        }
        hp.getActiveTabElement().click();
        if (hp.getActiveTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flag = true;
            List<WebElement> listOfItems = hp.getItemList();
            for (WebElement element : listOfItems) {
                if (!(element.getAttribute("class").equalsIgnoreCase("todo"))) {
                    flag = false;
                }
            }
            Assert.assertTrue(flag);
        }
        log.info("All active items present in All and Active tab");
    }

    @Then("No item should be present in Completed list")
    public void no_item_should_be_present_in_completed_list() {
        HomePage hp=new HomePage(driver);
        //completed tab
        hp.getCompletedTabElement().click();
        if (hp.getCompletedTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            Assert.assertEquals(hp.getItemList().size(),0);
        }
        log.info("NO items present in Completed tab");
    }


    @Then("Items left count should be equal to count of entered input")
    public void Items_left_count_should_be_equal_to_count_of_entered_input() {
        HomePage hp=new HomePage(driver);
        Assert.assertEquals(hp.getItemLeftCount().getText(),String.valueOf(toDoInputList.size()));
    }


    @When("User clicks on checkbox to complete toDo item")
    public void user_clicks_on_checkbox_todo_item_to_complete() throws InterruptedException {
        HomePage hp=new HomePage(driver);
        for(WebElement element: hp.getCompleteCheckbox()){
            element.click();
        }
        wait.until(ExpectedConditions.visibilityOf(hp.getClearCompletedElement()));
        log.info("All items completed");
        Thread.sleep(1000);
    }

    @Then("Checked items should be completed in All and Completed tab")
    public void checked_items_should_be_completed_in_All_and_Completed_tab() {
        HomePage hp = new HomePage(driver);
        //        //check in All tab
        hp.getAllTabElement().click();
        if (hp.getAllTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flag = true;
            List<WebElement> listOfTodoItems = hp.getItemList();
            for (WebElement element : listOfTodoItems) {
                if (!(element.getAttribute("class").equalsIgnoreCase("todo completed"))) {
                    flag = false;
                }
            }
            Assert.assertTrue(flag);
        }
        //check in Completed tab
        hp.getCompletedTabElement().click();
        if (hp.getCompletedTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flagForCompleted = true;
            List<WebElement> listOfTodoItems = hp.getItemList();
            for (WebElement element : listOfTodoItems) {
                  if (!(element.getAttribute("class").equalsIgnoreCase("todo completed"))) {
                    flagForCompleted = false;
                }
            }
            Assert.assertTrue(flagForCompleted);
        }
        log.info("Selected items are completed in All and Completed tab");
    }


    @Then("Clear completed option is enabled")
    public void clear_completed_option_is_enabled() {
        HomePage hp=new HomePage(driver);
        boolean flag=true;
        if(!(hp.getClearCompletedElement().isDisplayed())){
            flag=false;
        }
        Assert.assertTrue(flag);
        log.info("Clear completed option enabled");
    }



    @When("User deselects a completed item")
    public void user_deselects_a_completed_item() throws InterruptedException {
        //deselect first item from list
        String itemToDeselect=toDoInputList.get(0);
      HomePage hp=new HomePage(driver);
        hp.getItemtoSelect(itemToDeselect).click();
        log.info("First item is deselected");
        Thread.sleep(1000);
    }

    @Then("Deselected item should become active in All and Active tab")
    public void deselected_item_should_become_active() throws InterruptedException {
        HomePage hp=new HomePage(driver);
        String itemToDeselect=toDoInputList.get(0);
        //All Tab
        if (hp.getAllTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            Assert.assertEquals(hp.getListItem(itemToDeselect).getAttribute("class"), "todo");
        }
        //Active tabe
        hp.getActiveTabElement().click();
        if(hp.getActiveTabElement().getAttribute("class").equalsIgnoreCase("selected")){
            Assert.assertEquals(hp.getListItem(itemToDeselect).getAttribute("class"), "todo");
        }
    }


    @Given("All active todo item present in todo list")
    public void all_active_todo_item_present_in_todo_list() {
      log.info("All active items present");
    }

    @Then("Items left count should become 0")
    public void items_left_count_should_decrease() {
        HomePage hp=new HomePage(driver);
        int actualCount=Integer.parseInt(hp.getItemLeftCount().getText());
        Assert.assertEquals(actualCount,0);
        log.info("Items left count is validated");
    }


    @Given("user completes {string} toDo item")
    public void user_completes_any_toDo_item(String itemToComplete) throws Throwable {
        HomePage hp=new HomePage(driver);
        hp.getItemtoSelect(itemToComplete).click();
        wait.until(ExpectedConditions.visibilityOf(hp.getClearCompletedElement()));
        itemLeftCount=hp.getItemLeftCount().getText();
        log.info("Selected item completed");
        Thread.sleep(1000);
    }

    @When("User clicks on Clear completed")
    public void user_clicks_on_clear_completed() throws InterruptedException {
       HomePage hp=new HomePage(driver);
       wait.until(ExpectedConditions.visibilityOf(hp.getClearCompletedElement()));
       hp.getClearCompletedElement().click();
        Thread.sleep(1000);
    }


    @Then("Completed items should be removed from All and Completed list")
    public void completed_items_should_be_removed_from_all_and_completed_list() {
        HomePage hp=new HomePage(driver);
        //all  tab should have only active todo items
        if (hp.getAllTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flag= true;
            List<WebElement> listOfTodoItems = hp.getItemList();
            for (WebElement element : listOfTodoItems) {
                if (!(element.getAttribute("class").equalsIgnoreCase("todo"))) {
                    flag= false;
                }
            }
            Assert.assertTrue(flag);
        }
        //completed tab
        hp.getCompletedTabElement().click();
        if (hp.getCompletedTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            Assert.assertEquals(hp.getItemList().size(),0);
        }
        log.info("Selected items are completed in All and Completed tab");
    }


    @Then("Count of active item left should remain same")
    public void count_of_active_item_left_should_remain_same() {
        HomePage hp=new HomePage(driver);
       Assert.assertEquals(itemLeftCount,hp.getItemLeftCount().getText());
    }



    @When("User clicks on toggleAll button")
    public void user_clicks_on_cross_sign_on_to_do_item() throws InterruptedException {
     HomePage hp=new HomePage(driver);
     wait.until(ExpectedConditions.visibilityOf(hp.getItemLeftCount()));
     hp.toggleAllItems();
        Thread.sleep(1000);
    }



    @Then("All items should be completed in All tab")
    public void item_should_be_removed_from_all_and_active_fields() {
       HomePage hp=new HomePage(driver);
        if (hp.getAllTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flag = true;
            List<WebElement> listOfItems = hp.getItemList();
            for (WebElement element : listOfItems) {
                System.out.println("attributes in all tab are " +element.getAttribute("class"));
                if (!(element.getAttribute("class").equalsIgnoreCase("todo completed"))) {
                    flag = false;
                }
            }
            Assert.assertTrue(flag);
        }
        log.info("All items are completed");
    }


    @Then("All items should be active in All tab")
    public void all_items_should_be_completed_in_all_and_completed_field() {
        HomePage hp=new HomePage(driver);
        if (hp.getAllTabElement().getAttribute("class").equalsIgnoreCase("selected")) {
            boolean flag = true;
            List<WebElement> listOfItems = hp.getItemList();
            for (WebElement element : listOfItems) {
                if (!(element.getAttribute("class").equalsIgnoreCase("todo"))) {
                    flag = false;
                }
            }
            Assert.assertTrue(flag);
        }
        log.info("All items are active");
    }


}
