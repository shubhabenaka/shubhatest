package CodeTest;

import Ehq_CommonFunctionLibrary.CommonFunctions;
import Ehq_Object_Repository.Objects;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class codeTest
{
    @Test
     public void testCode() throws InterruptedException, IOException {
        //Test Code goes here

        //LOGIN
        WebDriver driver = new FirefoxDriver();
        driver.get("http://autotuned.engagementhq.com/admin/projects/description-project/edit");
        Thread.sleep(2000);
        Objects obj = new Objects(driver);
        CommonFunctions.testDriver = driver;
        obj.userNameTB.sendKeys("ehqtesting");
        obj.passwordTB.sendKeys("ehqtesting_btt");
        obj.signBttn.click();
        Thread.sleep(5000);
       System.out.println(obj.truncateDescCB.getAttribute("checked"));
    }
    }
