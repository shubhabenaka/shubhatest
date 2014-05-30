package Ehq_Object_Repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;


/**
 * Created by Ridhi on 31/03/14.
 */
public class Objects


{

    public WebDriver testDriver;

        @FindBy(id = "user_email_or_login") public static WebElement userNameTB;

        @FindBy(id = "user_password") public static WebElement passwordTB;

        @FindBy(name = "commit") public static WebElement signBttn;

        @FindBy(css="div.tiny-progress") public static WebElement loadProgress;

        @FindBy(id = "project_name") public static WebElement projectNameTB;

        @FindBy(css = "body > p > br") public static WebElement descTB;

        @FindBy(id = "project_description_display_mode") public static WebElement truncateDescCB;

        @FindBy(id = "project_permalink") public static WebElement permalinkTB;

        @FindBy(css = "input.btn.btn-primary.js-submit") public static WebElement addProjBtn;

        @FindBy(css = "a.dropdown-toggle.show-cursor") public static WebElement userLabel;

        @FindBy(css = "a.add-new-photo.add-btn.btn-add.btn.btn-primary") public static WebElement photoAddBttn;

        @FindBy(id = "SWFUpload_0") public static WebElement browserPhotoBttn;

        @FindBy(id = "uploaded_successfully_SWFUpload_0_0") public static WebElement photoUpSuccess;

        public static final String loutTabId="html/body/div[1]/div[1]/div/div/ul[2]/li/a";
        @FindBy(xpath =loutTabId ) public static WebElement loutTab;

        @FindBy(css="a.dropdown-toggle.show-cursor") public static WebElement logoutLink;

        @FindBy(xpath = "html/body/div[2]/div[5]") public static WebElement loginErrorMsg;

        @FindBy(className = "error_messages") public static WebElement projErrorMsg;

        @FindBy(xpath = ".//*[@id='project-listing_filter']/label/input") public static WebElement projSearchTB;

        @FindBy(className = "alert.alert-info") public static WebElement logoutMsg;

        @FindBy(id = "tools_SurveyTool") public static WebElement surveyToolsCB;

        @FindBy(css = "ul.unstyled.admin-tabs.pinneditempreview") public static WebElement projToolsTab;

        @FindBy(css = "input.btn.btn-primary.btn.btn-primary.js-submit") public static WebElement projSaveTools;

        @FindBy(css = "a.btn.btn-primary.new") public static WebElement addSurveyBtn;

        @FindBy(id = "survey_tool_name") public static WebElement  surveyNameTB;

        @FindBy(id="survey_tool_permalink") public static WebElement surveyPermLinkTB;

        @FindBy(id="survey_tool_participation_mode_verified_participation_mode") public static WebElement surveyVerPartModeRB;

        @FindBy(id="survey_tool_participation_mode_unverified_participation_mode") public static WebElement surveyUnverPartModeRB;

        @FindBy(id="survey_tool_participation_mode_anonymous_participation_mode") public static WebElement surveyAnonPartModeRB;

        @FindBy(id="survey_tool_allow_user_to_post_multiple_times") public static WebElement surveyAllowMulCB;

        @FindBy(id="survey_tool_multiple_times_msg") public static WebElement surveyAllowMulMsgTB;

        @FindAll ({ @FindBy(name = "commit")}) public static List<WebElement> surveySaveBtn;

        public static final String surveyQTypeBoxId="question_type";
        @FindBy(id=surveyQTypeBoxId) public static WebElement dropDownSurveyQType;

        @FindBy(id="question_question") public static WebElement surveyQuesTB;

        public static final String surveyQLenBoxId="question_max_length";
        @FindBy(id=surveyQLenBoxId) public static WebElement surveyQLenTB;

        public static final String surveyQReqId="question_required";
        @FindBy(id="question_required") public static WebElement surveyQReqCB;

        @FindBy(id="question_notes") public static WebElement surveyQNotesTB;

        public final static String surveyDescQBoxId="question_descriptor";
        @FindBy(id=surveyDescQBoxId) public static WebElement surveyQDescTB;

        @FindBy(id="question_horizontal") public static WebElement surveyQHorizontalCB;

        public final static String surveyImgTrueId="question_use_image_link_true";
        @FindBy(id="question_use_image_link_true") public static WebElement surveyImgUpTrueRB;

        @FindBy(id="question_use_image_link_false") public static WebElement surveyImgUpFalseRB;

        public static final String surveyImgLinkBoxId="question_image_link";
        @FindBy(id=surveyImgLinkBoxId) public static WebElement surveyImgLinkQTB;





 /*   public static WebElement logoutTab(String username)
    {
        WebElement loutTab=testDriver.findElement(By.partialLinkText(username));
        return loutTab;
    } */

    public WebElement getSurveyOptions(int index) throws InterruptedException {
            if (testDriver.findElements(By.id("question_question_options_attributes_" + index + "_name")).size()!=0)
            {
                return testDriver.findElement(By.id("question_question_options_attributes_" + index + "_name"));
            }
        return null;
    }

    public WebElement getSurveyStatements(int index) throws InterruptedException {
            if (testDriver.findElements(By.id("question_statements_attributes_" + index + "_question")).size()!=0)
            {
                return testDriver.findElement(By.id("question_statements_attributes_" + index + "_question"));
            }

        return null;
    }

    public WebElement getLink(String linkText)
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

    public WebElement redactorFrame(int index)
    {
        if (testDriver.findElements(By.className("redactor_redactor")).size()!=0)
        {
            WebElement frame=testDriver.findElements(By.className("redactor_redactor")).get(index);
            return frame;
        }
        else
            fail("Frame not found on Page.Hence failing Test");
            return null;
    }

    public WebElement linkByIndex(String linkText,Integer index)
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
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }


    public Objects(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
        testDriver=driver;
    }
}
