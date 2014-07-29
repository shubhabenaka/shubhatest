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

    @FindBy(id = "user_email_or_login") public static WebElement userNameTB;

    @FindBy(id = "user_password") public static WebElement passwordTB;

    @FindBy(name = "commit") public static WebElement signBttn;

    @FindBy(xpath = "html/body/div[2]/div[5]") public static WebElement loginErrorMsg;

    @FindBy(css = "input.btn.btn-primary.js-submit") public static WebElement addProjBtn;

    @FindBy(css = "a.dropdown-toggle.show-cursor") public static WebElement userLabel;

    @FindBy(id = "project_name") public static WebElement projectNameTB;

    @FindBy(className = "error_messages") public static WebElement projErrorMsg;

    @FindBy(className = "redactor_redactor") public static WebElement projDescriptionTB;

    @FindBy(xpath = ".//*[@id='project-listing_filter']/label/input") public static WebElement projSearchTB;

    @FindBy(id = "project_description_display_mode") public static WebElement truncateDescCB;

    @FindBy(css = "#tools_ForumTopic") public static WebElement forumToolsCB;

    @FindBy(id = "project_permalink") public static WebElement permalinkTB;

    @FindBy(css = "#tools_SurveyTool") public static WebElement surveyToolsCB;

    @FindBy(css = "#tools_BlogPost") public static WebElement newsToolsCB;

    @FindBy(css="div.tiny-progress") public static WebElement loadProgress;

    @FindBy(css=".btn.btn-primary.btn.btn-primary.js-submit") public static WebElement projSaveToolsBtn;

    @FindBy(css= ".well.tools") public static WebElement projToolsTab;

       @FindBy(id="forum_topic_name") public static WebElement forumNameTB;

    @FindBy(css = "body > p > br") public static WebElement descTB;

    @FindBy(id="forum_topic_description_display_mode") public static WebElement forumDescTruncCB;

    @FindBy(id="forum_topic_allow_unverified_participation") public static WebElement forumUnverPartCB;

    @FindBy(id="forum_topic_related_media_type") public static WebElement forumRelMediaDD;

    @FindBy(id="forum_topic_link") public static WebElement forumTopicLinkTB;

    @FindBy(css="input.btn[value=Create]") public static WebElement projToolsCreateBtn;

    @FindBy(css="div[class=error_messages]") public static WebElement newsPostErrorMsg;

    @FindBy(css = ".btn.btn-primary.btn-large.add-new-comment-link.local") public static  WebElement addYourCommentBttn;

    @FindBy(id = "comment_comment") public  static WebElement commentTextArea;

    @FindBy(css = ".btn.btn-primary.submit-button") public static WebElement commentSubmitBttn;

    @FindBy(css=".new_replies > textarea:nth-child(4)") public static WebElement replyTextArea;

    @FindBy(css=".new_replies > div:nth-child(8) > input:nth-child(1)") public static WebElement replySubmitBttn;


    public final static String projectsLinkCss="td.sorting_1";
    @FindBy(css=projectsLinkCss) public static WebElement projectLinkTypeLbl;

    @FindBy(css="input[id=password]") public static WebElement projectDeletePsswdTB;
    @FindBy(css="input.btn[value='Delete Project']") public static WebElement projectDeleteBtn;

    public static final String loutTabId="html/body/div[1]/div[1]/div/div/ul[2]/li/a";
    @FindBy(xpath =loutTabId ) public static WebElement loutTab;

    @FindBy(linkText = "Logout") public static WebElement logoutLink;
    @FindBy(css="div.alert") public static WebElement logoutMsg;

    @FindBy(css = ".add-btn.btn.btn-primary.js_dependent_link") public static WebElement addKeydateBttn;



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

    public WebElement manageLink(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Manage")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Manage")))
            {
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }

    public WebElement previewLink(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Preview")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Preview")))
            {
                System.out.println(testDriver.findElements(By.linkText("Preview")));
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }

    public WebElement publishLink(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Preview")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Publish")))
            {
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }


    public WebElement redactorFrame(int index)
    {
        if (testDriver.findElements(By.className("redactor_redactor")).size()!=0)
        {
            WebElement frame=testDriver.findElements(By.className("redactor_redactor")).get(index);
            return frame;
        }
        else
            //fail("Frame not found on Page.Hence failing Test");
        testDriver.quit();
        return null;
    }

    public platformObjects(WebDriver driver) throws StaleElementReferenceException
    {
        PageFactory.initElements(driver, this);
        testDriver = driver;
    }

}
