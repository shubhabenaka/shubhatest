package Ehq_Object_Repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;


/**
 * Created by Ridhi on 31/03/14.
 */
public class Objects


{

    public static WebDriver testDriver;

        @FindBy(id = "user_email_or_login") public static WebElement userNameTB;

        @FindBy(id = "user_password") public static WebElement passwordTB;

        @FindBy(name = "commit") public static WebElement signBttn;

        @FindBy(css="div.tiny-progress") public static WebElement loadProgress;

        @FindBy(id = "project_name") public static WebElement projectNameTB;

        @FindBy(className = "redactor_redactor") public static WebElement projDescriptionTB;

        @FindBy(id = "project_description_display_mode") public static WebElement truncateDescCB;

        @FindBy(id = "project_permalink") public static WebElement permalinkTB;

        @FindBy(css = "input.btn.btn-primary.js-submit") public static WebElement addProjBtn;

        @FindBy(css = "a.dropdown-toggle.show-cursor") public static WebElement userLabel;

        @FindBy(css = "a.add-new-photo.add-btn.btn-add.btn.btn-primary") public static WebElement photoAddBttn;

        @FindBy(id = "SWFUpload_0") public static WebElement browserPhotoBttn;

        @FindBy(id = "uploaded_successfully_SWFUpload_0_0") public static WebElement photoUpSuccess;

        @FindBy(xpath = "/html/body/div/div/div/div/ul[2]/li/a") public static WebElement loutTab;



 /*   public static WebElement logoutTab(String username)
    {
        WebElement loutTab=testDriver.findElement(By.partialLinkText(username));
        return loutTab;
    } */


    public static WebElement getLink(String linkText)
    {
        if (testDriver.findElements(By.linkText(linkText)).size()!=0)
        {
            WebElement link = testDriver.findElements(By.linkText(linkText)).get(0);
            return link;
        }
        else
            fail("Link not found on the current page. Hence failing Test");
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
            fail("Link not found on the current page. Hence failing Test");
        return null;
    }


    public Objects(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
        testDriver=driver;
    }
}
