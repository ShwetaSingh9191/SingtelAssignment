package Utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class Base {

    public WebDriver driver;
    public Properties prop;
    public WebDriverWait wait;


    public WebDriver initializeWebDriver() throws IOException {

        prop= new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//data.properties");
        prop.load(fis);

        //browsername = chrome
        if(prop.getProperty("browser").equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverpath"));
            driver=new ChromeDriver();
        }else if(prop.getProperty("browser").equalsIgnoreCase("ie")){

        }else if(prop.getProperty("browser").equalsIgnoreCase("firefox")){

        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver,5);
        return driver;
    }

}
