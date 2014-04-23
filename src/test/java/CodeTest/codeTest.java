package CodeTest;

import Ehq_Object_Repository.Objects;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 * Created by Ridhi on 04/04/14.
 */
public class codeTest
{

    @Test

    public void testCode() throws Exception {




      WebDriver testDriver = new FirefoxDriver();
        testDriver.get("http://expproj.abc.engagementhq.com/login");
        Objects pageObj=new Objects(testDriver);


        ArrayList arr=new ArrayList();
        arr.add("AddNewProject");
        arr.add("Proj_3");
        arr.add("Desc");
        arr.add("Truncate");
        String projName=arr.get(1).toString();

         pageObj.userNameTB.sendKeys("ridhi@bangthetable.com");
        pageObj.passwordTB.sendKeys(password());
        pageObj.signBttn.click();
        //waitForPageLoad(testDriver);
        //assertTrue("Sign in Failed", waitForElement(pageObj.userLabel, testDriver));
        Objects.linkByIndex("Add new project",0).click();
        assertTrue("", pageObj.projectNameTB.isDisplayed());
        pageObj.projectNameTB.sendKeys(projName);
        testDriver.findElement(By.cssSelector("div.form-field:nth-child(1) > label:nth-child(1)")).click();
        String driverHandle=testDriver.getWindowHandle();
        testDriver.switchTo().frame(testDriver.findElement(By.className("redactor_redactor")));
        Thread.sleep(1000);
        testDriver.findElement(By.cssSelector("body > p > br")).sendKeys("\n"+ arr.get(2).toString());
        testDriver.switchTo().window(driverHandle);
        if (arr.size() > 3)
            {
                if (arr.get(3).toString() == "Truncate") {
                    pageObj.truncateDescCB.click();
                }
            }
            String regExp = "[^\\w\\s\\u2014]";
            String regExp2 = "[^\\w]|[\\_]";
            String expPermlink=((arr.get(1).toString().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());
            //System.out.println(expPermlink);
            System.out.println(pageObj.permalinkTB.getAttribute("value"));
            if (pageObj.permalinkTB.getAttribute("value").contains(expPermlink))
            {
                pageObj.addProjBtn.click();
            } else fail("test failed, permalink not updated.");

       //Verify

        Objects.linkByIndex("PROJECTS",0).click();
        Thread.sleep(5000);
        assertTrue(testDriver.findElements(By.linkText(projName)).get(0).isDisplayed());


    }




    public String password()
    {
        String password="*****";
        return password;
    }




}
