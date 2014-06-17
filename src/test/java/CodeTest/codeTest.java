package CodeTest;

import Ehq_Object_Repository.Objects;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class codeTest
{
    @Test
    public void testCode() throws InterruptedException
    {
        WebDriver driver=new FirefoxDriver();
        Objects obj=new Objects(driver);
        driver.get("http://expproj.abc.engagementhq.com/admin/newsletters/379?step=recipients");
        obj.userNameTB.sendKeys("ehqtesting");
        obj.passwordTB.sendKeys("ehqtesting_btt");
        obj.signBttn.click();
        Thread.sleep(5000);
        //obj.getNewsLtrrSelect("any");.mode-condition.moveaway
        System.out.println(driver.findElement(By.xpath(".//*[@id='any']")).findElements(By.cssSelector("input.filter-input.filter-text.moveaway")).size());
        driver.findElement(By.xpath(".//*[@id='any']")).findElements(By.cssSelector("input.filter-input.filter-text.moveaway")).get(2).sendKeys("blah blah blah");
        //sel.selectByVisibleText("does not contain");


    }
}