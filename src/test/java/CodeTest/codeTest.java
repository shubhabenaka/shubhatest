package CodeTest;

import Ehq_CommonFunctionLibrary.CommonFunctions;
import Ehq_Object_Repository.Objects;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class codeTest {

    @Test
    public void testCode() throws InterruptedException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException

    {
        //Code for testing goes here
        WebDriver driver = new FirefoxDriver();
        driver.get("http://expproj.abc.engagementhq.com/login");
        Objects obj = new Objects(driver);
        CommonFunctions.testDriver = driver;
        obj.userNameTB.sendKeys("ehqtesting");
        obj.passwordTB.sendKeys("ehqtesting_btt");
        obj.signBttn.click();

        driver.get("http://expproj.abc.engagementhq.com/admin/projects/project-21/survey_tools/Survey21PermLink/questions");
        //obj.getLink("PROJECTS").click();
        //obj.linkByIndex("Proj_3",0).click();
        ArrayList<String> arr=new ArrayList<String>();
        for (WebElement e2:driver.findElements(By.cssSelector("h6.pull-left.nomargin.type")))
        {
            arr.add(e2.getAttribute("textContent").replaceAll(" ", "_").toLowerCase() + "_answer");
        }
        driver.get("http://expproj.abc.engagementhq.com/admin/projects/project-21/survey_tools");
        for (WebElement e : driver.findElements(By.linkText("Preview"))) {
            if (e.getAttribute("href").contains("Survey21")) {
                e.click();
            }
        }
        ArrayList<String> tabs=new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        boolean qFlag=true;
        if (driver.getCurrentUrl().contains("Survey21") && driver.getCurrentUrl().contains("preview"))
        {
          for (String str :arr)
            {
                if (str.equals("radio_buttons_answer")) str=str.replaceAll("buttons","button");
                if (str.equals("checkboxes_answer")) str=str.replaceAll("checkboxes","check_box");
                if (str.equals("suburb_answer")) str=str.replaceAll("suburb","region");
                if (str.equals("dropdown_answer")) str=str.replaceAll("dropdown","drop_down");
                if (str.equals("file_upload_answer")) str=str.replaceAll("file_upload","file");
                if (!str.isEmpty())
                {
                    if (driver.getPageSource().contains(str)) {
                    }//Do nothing
                    else qFlag = false;
                }
            }
        }
        if (qFlag) System.out.println("Passed");
        driver.quit();




        //Add Questions


    }
}