package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {

    public WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver=driver;
    }

    By pageHeader= By.xpath("//h1[contains(text(),'todos')]");
    By toDoInputField = By.className("new-todo");
    By itemLeftCount= By.xpath("//span[@class='todo-count']/strong");
    By allTab=By.xpath("//a[contains(text(),'All')]");
    By completedTab= By.xpath("//a[contains(text(),'Completed')]");
    By activeTab=By.xpath("//a[contains(text(),'Active')]");
//    By toDoFullListFromUI=By.className("todo-list");
    By toDoActiveItem =By.className("todo");
    By completedItem=By.xpath("//li[@class='todo completed']");
    By completeCheckBox=By.className("toggle");
    By toggleAllCheckBox=By.xpath("//label[@for='toggle-all']");
    By itemList = By.xpath("//ul[@class='todo-list']/li");
    By clearCompletedButton = By.className("clear-completed");




    public WebElement getPageHeader(){ return driver.findElement(pageHeader);    }

    public WebElement getToDoInputField(){
        return driver.findElement(toDoInputField);
    }

    public WebElement getItemLeftCount(){
        return driver.findElement(itemLeftCount);
    }

    public WebElement getAllTabElement(){
        return driver.findElement(allTab);
    }

    public WebElement getCompletedTabElement(){
        return driver.findElement(completedTab);
    }

    public WebElement getActiveTabElement(){
        return driver.findElement(activeTab);
    }

//    public List<WebElement> getTodoFullListInAllTab(){
//        return driver.findElements(toDoFullListFromUI);
//    }

    public List<WebElement> getToDoActiveItems(){
        return driver.findElements(toDoActiveItem);
    }

    public List<WebElement> getCompletedItems(){ return driver.findElements(completedItem);
    }

    public List<WebElement> getCompleteCheckbox(){ return driver.findElements(completeCheckBox);
    }

    public void toggleAllItems(){  driver.findElement(toggleAllCheckBox).click();
    }

    public List<WebElement> getItemList(){ return driver.findElements(itemList);
    }

    public WebElement getClearCompletedElement(){ return driver.findElement(clearCompletedButton);
    }

    public WebElement getListItem(String inputItem){
        return driver.findElement(By.xpath("//label[contains(text(),'"+inputItem+"')]/ancestor::li"));
    }

    public WebElement getItemtoSelect(String itemToComplete){
        return driver.findElement(By.xpath("//label[contains(text(),'"+itemToComplete+"')]/parent::div/input"));
    }
}
