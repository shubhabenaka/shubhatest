package CodeTest;

import Ehq_CommonFunctionLibrary.CommonFunctions;
import Ehq_Object_Repository.Objects;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static Ehq_CommonFunctionLibrary.CommonFunctions.waitForElement;
import static Ehq_CommonFunctionLibrary.CommonFunctions.waitForPageLoad;

public class codeTest
{
    @Test
     public void testCode() throws InterruptedException {

        //Test Code goes here.

        WebDriver testDriver=new FirefoxDriver();
        testDriver.get("http://expproj.abc.engagementhq.com/login");
        testDriver.manage().window().maximize();//Pass URL to browser
        CommonFunctions.testDriver=testDriver;
        Objects pageObj=new Objects(testDriver);
        waitForPageLoad();
        pageObj=new Objects(testDriver);
        pageObj.userNameTB.sendKeys("ehqtesting");  //Populate Username
        pageObj.passwordTB.sendKeys("ehqtesting_btt");  //Populate Password
        pageObj.signBttn.click();                                   //Click on Sign in button
        waitForPageLoad();
        pageObj.getLink("PROJECTS").click();
        waitForPageLoad();
        for (WebElement x:testDriver.findElements(By.cssSelector("td.sorting_1")))
        {
            WebElement y=testDriver.findElement(By.cssSelector("td.sorting_1"));
            System.out.println(y.getAttribute("textContent"));
            y.findElement(By.cssSelector("a")).click();
            waitForPageLoad();
            pageObj.getLink("Delete").click();
            waitForPageLoad();
            testDriver.findElement(By.cssSelector("input[id=password]")).sendKeys("ehqtesting_btt");
            testDriver.findElement(By.cssSelector("input.btn[value='Delete Project']")).click();
            waitForPageLoad();
            waitForElement(By.linkText("Add new project"));
        }
    }
}