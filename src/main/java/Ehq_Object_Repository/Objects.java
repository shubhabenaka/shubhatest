package Ehq_Object_Repository;

import Ehq_CommonFunctionLibrary.CommonFunctions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


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

        @FindBy(id = "project_description_display_mode") public static WebElement truncateDescCB;

        @FindBy(id = "project_permalink") public static WebElement permalinkTB;

        @FindBy(css = "input.btn.btn-primary.js-submit") public static WebElement addProjBtn;

        @FindBy(css = "a.dropdown-toggle.show-cursor") public static WebElement userLabel;

        @FindBy(css = "a.add-new-photo.add-btn.btn-add.btn.btn-primary") public static WebElement photoAddBtn;

        @FindBy(id = "SWFUpload_0") public static WebElement browserPhotoBtn;

        @FindBy(id = "uploaded_successfully_SWFUpload_0_0") public static WebElement photoUpSuccess;

        public static final String loutTabId="html/body/div[1]/div[1]/div/div/ul[2]/li/a";
        @FindBy(xpath =loutTabId ) public static WebElement loutTab;

        @FindBy(linkText = "Logout") public static WebElement logoutLink;

        @FindBy(xpath = "html/body/div[2]/div[5]") public static WebElement loginErrorMsg;

        @FindBy(className = "error_messages") public static WebElement projErrorMsg;

        @FindBy(xpath = ".//*[@id='project-listing_filter']/label/input") public static WebElement projSearchTB;

        @FindBy(css="div.alert") public static WebElement logoutMsg;

        @FindBy(css = "ul.unstyled.admin-tabs.pinneditempreview") public static WebElement projToolsTab;

        @FindBy(css = "input.btn[value=Save]") public static WebElement projSaveToolsBtn;

        @FindBy(css = "a.btn.btn-primary.new") public static WebElement addSurveyBtn;

        @FindBy(id = "survey_tool_name") public static WebElement  surveyNameTB;

        @FindBy(id="survey_tool_permalink") public static WebElement surveyPermLinkTB;

        @FindBy(id="survey_tool_participation_mode_verified_participation_mode") public static WebElement surveyVerPartModeRB;

        @FindBy(id="survey_tool_participation_mode_unverified_participation_mode") public static WebElement surveyUnverPartModeRB;

        @FindBy(id="survey_tool_participation_mode_anonymous_participation_mode") public static WebElement surveyAnonPartModeRB;

        @FindBy(id="survey_tool_allow_user_to_post_multiple_times") public static WebElement surveyAllowMulCB;

        @FindBy(id="survey_tool_multiple_times_msg") public static WebElement surveyAllowMulMsgTB;

        @FindBy(css="input.btn[value=Create]") public static WebElement projToolsCreateBtn;

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

        @FindBy(id="blog_post_name") public static WebElement newsPostTitleTB;

        @FindBy(id="blog_post_link") public static WebElement newsPostLinkTB;

        @FindBy(css="body>p>br") public static WebElement newsPostDescTA;

        @FindBy(id="blog_post_description_display_mode") public static WebElement newsPostDescDispCB;

        @FindBy(id="blog_post_commentable") public static WebElement newsPostCommentableCB;

        @FindBy(id="blog_post_allow_unverified_participation") public static WebElement newsUnverPartCB;

        @FindBy(id="blog_post_tag_list") public static WebElement newsPostCategoryTB;

        @FindBy(css="div[class=error_messages]") public static WebElement validationErrMsg;

        @FindBy(id="forum_topic_name") public static WebElement forumNameTB;

        @FindBy(id="forum_topic_description_display_mode") public static WebElement forumDescTruncCB;

        @FindBy(id="forum_topic_allow_unverified_participation") public static WebElement forumUnverPartCB;

        @FindBy(id="forum_topic_related_media_type") public static WebElement forumRelMediaDD;

        @FindBy(id="forum_topic_link") public static WebElement forumTopicLinkTB;

        @FindBy(id="newsletter_subject") public static WebElement newsLtrSubjectTB;

        @FindBy(id="newsletter_banner_source") public static WebElement newsLtrBannerDD;

        @FindBy(id="newsletter_test_email_emails") public static WebElement newsLtrTestEmailTA;

        @FindBy(css="div.mail-preview") public static WebElement newsLtrMailPreviewLbl;

        @FindBy(css="input.btn[value=Next]") public static WebElement newsLtrNextBtn;

        @FindBy(css="input.btn[value='Send Test Email']") public static WebElement newsLtrSendTestMailBtn;

        @FindBy(id="Email") public static WebElement gmailUsernameTB;

        @FindBy(id="Passwd") public static WebElement gmailPasswordTB;

        @FindBy(id="signIn") public static WebElement gmailSignBtn;

        @FindBy(css="td.xW.xY") public static WebElement mailTimeLbl;

        public final static String gmailSubjectCss="td.xY.a4W";
        @FindBy(css=gmailSubjectCss) public static WebElement mailSubjectLbl;

        public final static String gmailBodyCss="div.a3s";
        @FindBy(css=gmailBodyCss) public static WebElement mailBodyLbl;

        public final static String projectsLinkCss="td.sorting_1";
        @FindBy(css=projectsLinkCss) public static WebElement projectLinkTypeLbl;

        @FindBy(css="input[id=password]") public static WebElement projectDeletePsswdTB;

        @FindBy(css="input.btn[value='Delete Project']") public static WebElement projectDeleteBtn;

        @FindBy(css="a.btn.btn-primary.local") public static WebElement registerBtn;

        @FindBy(id="signup_form_response_answers_attributes_0_login") public static WebElement registerNameTB;

        @FindBy(id="email_field") public static WebElement registerMailTB;

        @FindBy(id="signup_form_response_answers_attributes_2_password") public static WebElement registerPsswdTB;

        @FindBy(id="signup_form_response_answers_attributes_2_password_confirmation") public static WebElement registerPsswdConfmTB;

        @FindBy(css = "a.select2-choice.script") public static WebElement registerfrmSuburbDRL;

        public static final String registerSuburbTB="input.select2-input";
        @FindBy(css = registerSuburbTB) public static WebElement registerfrmSuburbTB;

        public static final String registerSuburbOpt="div.select2-result-label";
        @FindBy(css = registerSuburbOpt) public static  WebElement registerSuburbSelectLI;

        @FindBy(id ="signup_form_response_answers_attributes_4_terms") public static WebElement registerTnCCB;

        @FindBy(css = "a.btn.btn-primary") public static WebElement addCommentBtn;

        @FindBy(xpath=".//form[@id='new_comment']/textarea[@id='comment_comment']") public static WebElement addCommentTA;

        @FindBy(id ="comment_auto_notify") public static WebElement  commentNotifyCB;

        @FindBy(css = "input[class='user-input-email']") public static WebElement commentMailTB;

        @FindBy(css ="input[class='user-input-screen-name']") public static WebElement commentScreenNameTB;

        @FindBy(xpath = ".//div[@class='comment-wrapper']/div[@class='comment']/div[@class='content']") public static WebElement commentWrapperLbl;

        @FindBy(xpath= ".//*[@id='mnav']/ul[2]/li/a") public static WebElement participantLoutLbl;

        @FindBy(xpath=".//*[@id='mnav']/ul[2]/li/ul/li[2]/a") public static WebElement participantLoutLnk;

        @FindBy(id="quick_poll_question_attributes_question") public static WebElement qPollQuesTB;

        @FindBy(id="quick_poll_thanks_msg") public static WebElement qPollMsgTA;

        @FindBy(id="quick_poll_permalink") public static WebElement qPollPermLnkTB;

        @FindBy(id="quick_poll_allow_unverified_participation") public static WebElement qPollUnverPartCB;

        @FindAll({@FindBy(xpath=".//div[@class='sp-form-builder']/div/input")} ) public static List<WebElement> qPollOptionsTB ;

        @FindBy(css = "input[id='project_publish_now']") public static WebElement publishProjNowRB;

        @FindBy(css = "a[class='publish_project']") public static WebElement projPublishLnk;

        @FindBy(xpath=".//a[text()='Home']") public static WebElement homeLink;

        @FindBy(xpath=".//a[text()='Dashboard']") public static WebElement participantDashbrdLnk;

    @FindBy(css = ".btn.btn-danger.js-close") public static WebElement qandaCloseBttn;

    @FindBy(css = ".local[href*=qanda]") public static WebElement qandaTabBttn;

    @FindBy(css = ".text.optional.control-label") public static WebElement qandaIntroLabel;

    @FindBy(css = "#qanda_question_question") public static WebElement qandaCommentTA;

    @FindBy(css = ".btn.btn-primary") public static WebElement qandaSubmitBttn;

    @FindBy(css = ".alert") public static WebElement qandaStatusMsg;

    @FindBy(xpath = "id('yield')/div[2]/div[2]/div[7]/div[1]/ul/li[2]/a") public static WebElement qandaDetailsTab;

    @FindBy(css = "#qanda_name") public static WebElement qandaTitleTB;

    @FindBy(css = ".btn.btn-primary.js-submit[value=Update]") public static WebElement qandaUpdateBttn;

    @FindBy(css = "#qanda_allow_unverified_participation") public static WebElement qandaUnverifaccessCB;

    @FindBy(css = "#qanda_archival_reason_message") public static WebElement qandaCloseMsgTB;

    @FindBy(xpath = ".//*[@id='archive-form']/div[3]/div/input") public static WebElement qandaArchiveSubmitBttn;

    @FindBy(xpath = ".//*[@id='yield']/div[2]/div[2]/div[5]/div/div/div/a") public static WebElement qandaReopenBttn;

    @FindBy(css = ".alert.alert-info") public static WebElement qandaArchiveStatusMsg;

    @FindBy(css = ".current span") public static WebElement qandaArchiveTab;

    @FindBy(css=".unstyled.filters>li>a[href*=archived] span.badge-info") public static WebElement qandaArchiveTabCnt;

    @FindBy(css = ".unstyled.filters>li>a[href*=newly]") public static WebElement qandaNewlyAddedTab;

    @FindBy(css = ".unstyled.filters>li>a[href*=newly_added] span.badge-info") public static WebElement qandaNewlyAddedTabCnt;

    @FindBy(css = ".unstyled.filters>li>a[href*=privately] span.badge-info") public static WebElement qandaAnswerPrivatelyTabCnt;

    @FindBy(css = ".unstyled.filters>li>a[href*=publicly] span.badge-info") public static WebElement qandaAnswerPubliclyTabCnt;

    @FindBy(css = ".unstyled.filters>li>a[href*=privately]") public static WebElement qandaAnswerPrivatelyTab;

    @FindBy(css = ".unstyled.filters>li>a[href*=publicly]") public static WebElement qandaAnswerPubliclyTab;

    @FindBy(xpath = "id('edit_question')/div[2]/div/input") public static WebElement qandaAnswerSubmitBttn;

    @FindBy(css = ".unstyled.filters>li>a[href*=junk]") public static WebElement qandaJunkTab;

    @FindBy(css = ".unstyled.filters>li>a[href*=junk] span.badge-info") public static WebElement qandaJunkTabCnt;


 /*   public static WebElement logoutTab(String username)
    {
        WebElement loutTab=testDriver.findElement(By.partialLinkText(username));
        return loutTab;
    } */

    public WebElement forumLink(String forumName)
    {
        return testDriver.findElement(By.xpath( ".//a[text()='" + forumName + " ']"));
    }

    public WebElement getNewsLtrFilter(String selectParentText)
    {
        String str=selectParentText.toLowerCase();
        int count = testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("input.filter-input.filter-text.moveaway")).size();
        if (count == 1) return testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("input.filter-input.filter-text.moveaway")).get(0);
        if (count > 1) return testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("input.filter-input.filter-text.moveaway")).get(count-1); else return null;
    }

    public WebElement getNewsLtrSelect2(String selectParentText)
    {
        String str=selectParentText.toLowerCase();
        int count = testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("select.mode-condition.moveaway")).size();
        if (count == 1) return testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("select.mode-condition.moveaway")).get(0);
        if (count > 1) return testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("select.mode-condition.moveaway")).get(count-1); else return null;
    }

    public WebElement getNewsLtrSelect(String selectParentText)
    {
        String str=selectParentText.toLowerCase();
        int count = testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("select.mode-name.select")).size();
        if (count == 1) return testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("select.mode-name.select")).get(0);
        if (count > 1) return testDriver.findElement(By.xpath(".//*[@id='"+str+"']")).findElements(By.cssSelector("select.mode-name.select")).get(count-1); else return null;
    }

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
            CommonFunctions.errorMsg="Link "+linkText+" not found on the current page. Hence failing Test";
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
            CommonFunctions.errorMsg="Frame not found on Page.Hence failing Test";
            testDriver.quit();
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
            CommonFunctions.errorMsg="Link " + linkText + " not found on the current page. Hence failing Test";
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

    public WebElement archiveQuestion(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Answer Privately")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Archive")))
            {
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }

    public WebElement unarchiveQuestion(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Junk")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Restore")))
            {
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }

    public WebElement RestoreJunkQuestion(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Delete")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Restore")))
            {
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }

    public WebElement junkQuestion(String hrefTxt)
    {
        if (testDriver.findElements(By.linkText("Restore")).size()!=0)
        {
            for(WebElement link: testDriver.findElements(By.linkText("Junk")))
            {
                if (link.getAttribute("href").contains(hrefTxt))
                {
                    return link;
                }
            }
        }
        return null;
    }

    public WebElement descTB(int index)
    {
        //public static WebElement descTB;
        if (CommonFunctions.browser.equals("chrome")||CommonFunctions.browser.equals("internet explorer"))
        {
          //testDriver.switchTo().parentFrame();
          testDriver.findElements(By.xpath(".//iframe")).get(index).click();
          return testDriver.findElements(By.xpath(".//iframe")).get(index);
        }
        else
        {
            testDriver.switchTo().frame(redactorFrame(index));
            return testDriver.findElement (By.cssSelector("body > p > br")) ;
        }

    }

    public WebElement commentObject(String userName,String commentText)
    {
        try
        {
            return testDriver.findElement(By.xpath(".//div[@class='comment-wrapper' and ./div/span[@class='comment-author' and contains(.,'" + userName + "')] and ./div/div[@class='content' and text()='" + commentText + "']]"));
        } catch (NoSuchElementException e)
        {
            CommonFunctions.errorMsg="No comment from user "+userName+" containing text '"+commentText+" found.";
            CommonFunctions.keywordResult=false;
            return null;
        }
    }

    public WebElement commentObjectsbyID(String objName,String id)
    {
        if (!objName.isEmpty())
        {
            if (objName.equals("ReplyBtn")) {
                return testDriver.findElement(By.xpath(".//*[@id='reply_" + id+"']"));
            }
            if (objName.equals("CommentTA"))
            {
                return testDriver.findElement(By.xpath(".//*[@id='comment_anchor_" + id + "']/div[@id='" + id + "']/div[@class='reply_form']/form/textarea"));
            }
            if (objName.equals("NotifyCB"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]/div/form[@id='new_comment']/div[@class='checkbox']"));
            }
            if (objName.equals("EmailTB"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]/div/form[@id='new_comment']/div//input[@class='user-input-email']"));
            }
            if (objName.equals("ScreenNameTB"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]/div/form[@id='new_comment']/div//input[@class='user-input-screen-name']"));
            }
            if (objName.equals("SubmitBtn"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]/div/form[@id='new_comment']/div//input[@name='commit']"));
            }
            if (objName.equals("AgreeBtn"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]//a[@class='btn btn-success btn-xs agree script']"));
            }
            if (objName.equals("DisagreeBtn"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]//a[@class='btn btn-danger btn-xs disagree script']"));
            }
            if (objName.equals("AlertBtn"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]/div[2]/div[2]/div[3]/a"));
            }
            if (objName.equals("HideLnk"))
            {
                return testDriver.findElement(By.xpath(".//*[@id=" + id + "]/div[2]/div[2]/div[5]/a/span"));
            }
            if (objName.equals("replyTextLbl"))
            {
                Integer num= testDriver.findElements(By.xpath(".//*[@id="+id+"]//div[@class='comment-wrapper']/div[@class='comment']/div[@class='content']")).size();
                return testDriver.findElements(By.xpath(".//*[@id="+id+"]//div[@class='comment-wrapper']/div[@class='comment']/div[@class='content']")).get(num-1);
            }
            else return null;
        }
        else return null;
    }


    public Objects(WebDriver driver) throws StaleElementReferenceException
    {
        PageFactory.initElements(driver, this);
        testDriver=driver;
    }
}
