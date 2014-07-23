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
     public void testCode() throws InterruptedException, IOException
    {
        //Test Code goes here

        WebDriver driver=new FirefoxDriver();
        String txt="One:Two:Three:Four:Five:Six:Seven";
        driver.get("http://autotuned.engagementhq.com/admin/projects/proj09621/quick_polls/new");
        Thread.sleep(3000);
        Objects obj=new Objects(driver);
        CommonFunctions.testDriver=driver;
        obj.userNameTB.sendKeys("ehqtesting");
        obj.passwordTB.sendKeys("ehqtesting_btt");
        obj.signBttn.click();
        Thread.sleep(3000);
        String[] pollsOptions=txt.split(Character.toString((char) 58));
        //List<WebElement> lst=  driver.findElements(By.xpath(".//div[@class='sp-form-builder']/div/input"));
        //System.out.println(lst.size());
        if (pollsOptions.length>obj.qPollOptionsTB.size())
        {
            while (pollsOptions.length!=obj.qPollOptionsTB.size())
            {
                obj.getLink("Add more").click();
                Thread.sleep(3000);
            }
        }
        for (int i=0;i<pollsOptions.length;i++)
        {
            obj.qPollOptionsTB.get(i).sendKeys(pollsOptions[i]);
        }

    }
}