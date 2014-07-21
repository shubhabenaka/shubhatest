package testObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.*;
import testFunctions.platformFunctions;


/**
 * Created by shubha on 16/7/14.
 */
public class platformObjects {

    public static WebDriver testDriver;

    @FindBy(id = "signin-email") public static WebElement userLoginTB;
    @FindBy(id = "signin-password") public static WebElement userPasswordTB;
    @FindBy(css = ".submit.btn.primary-btn.flex-table-btn.js-submit") public static WebElement signInBttn;
    @FindBy(css = ".Icon.Icon--cog.Icon--large") public static WebElement loutTab;
    @FindBy(xpath = ".//*[@id='signout-button']/button") public static WebElement logoutLink;





    public static WebElement getLink(String linkText)
    {
        if (testDriver.findElements(By.linkText(linkText)).size()!=0)
        {
            WebElement link = testDriver.findElements(By.linkText(linkText)).get(0);
            return link;
        }
        else
            //fail("Link not found on the current page. Hence failing Test");
            return null;
    }

    public static WebElement linkByIndex(String linkText,Integer index)
    {
        if (testDriver.findElements(By.linkText(linkText)).size()!=0)
        {
            WebElement link = testDriver.findElements(By.linkText(linkText)).get(index);
            return link;
        }
        else
            //fail("Link not found on the current page. Hence failing Test");
            return null;
    }

    public platformObjects(WebDriver driver) throws StaleElementReferenceException
    {
        PageFactory.initElements(driver, this);
        testDriver = driver;
    }

}
