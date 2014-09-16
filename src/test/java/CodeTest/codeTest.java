package CodeTest;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;

public class codeTest {
    @Test
    public void testCode() throws InterruptedException, IOException {
        //Test Code goes here

        //LOGIN
        WebDriver driver = new FirefoxDriver();
        driver.get("http://autotuned.engagementhq.com/testproject-1/forum_topics/forum-18");
        Thread.sleep(2000);
        //driver.findElement(By.id("reply_54979")).click();
        System.out.println(driver.findElement(By.id("reply_54979")).getAttribute("disabled"));
        /*Objects obj = new Objects(driver);
        CommonFunctions.testDriver = driver;
        obj.userNameTB.sendKeys("ehqtesting");
        obj.passwordTB.sendKeys("ehqtesting_btt");
        obj.signBttn.click();
        Thread.sleep(5000);*/
        /*while (nullFxn("no")==null)
        {
            System.out.println("inside");
            nullFxn("now");
            break;
        }
        //return true;

    }

    public static String nullFxn(String str)
    {
        if (str.equals("now"))
        {
            return "true";
        }
        return null;
    }*/
    }

}
