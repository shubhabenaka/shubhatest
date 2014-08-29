package Ehq_CommonFunctionLibrary;

import Ehq_Object_Repository.Objects;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

/**
 * Created by Ridhi on 31/03/14.
 */
public class CommonFunctions {
    public static String siteUrl;   //Url for the Site to be tested. Set via XLS.
    public static String alternateUrl=null; //A reference to an alternate Url which might need to be invoked during the execution, setting a valid value to this reference will lead to the opening of this URL instead of SiteURL. It is hence the Tester's duty to unset(set to null or empty) the value of this  variable post use.
    public static String testSuiteName; //The name of the Test Suite/Folder containing the scripts intended for execution.Set via XLS.
    public static String browser;   //The browser type to run the test on, i.e. Chrome,IE or Firefox(Safari/Opera).Set via XLS.
    public static String outputSuite;   //Path to the folder where the results are intended to be generated. Set via XLS.
    public static String appModule;     //A reference for future requirement, which would help switch out the Object repository. Set via XLS.
    public static String resultLogSuite;    //Path to the folder where the results logs of older execution are intended to be archived. Set via XLS.
    public static WebDriver testDriver;     //The WebDriver object, which acts as a handle to the browser opened via Selenium Test.
    public static boolean keywordResult = true;     //A boolean value set by each keyword to indicate Success or Failure status. This is evaluated at the end of each keyword execution.
    public static Objects pageObj;                  //The Object Repository object/instance.
    public static String errorMsg = "";           //A global reference that can be set by each function to report error messages this also prevents reporting keyword failure if we were expecting it



    public static void Login(ArrayList keywordArr) throws InterruptedException, MalformedURLException {
        if (keywordArr.size() >= 3) {
            if (keywordArr.size()==3)
            {
                    browserGrid();   //Reference to Browser and grid management Function
            }
            if (elementExists(pageObj.getLink("Sign In"))) {
                pageObj.getLink("Sign In").click();
                waitForPageLoad();
            }
            pageObj.userNameTB.sendKeys(keywordArr.get(1).toString());  //Populate Username
            pageObj.passwordTB.sendKeys(keywordArr.get(2).toString());  //Populate Password
            pageObj.signBttn.click();                                   //Click on Sign in button
            waitForPageLoad();
            if (elementExists(pageObj.loginErrorMsg))   //Validates presence of Error Message
            {
                errorMsg = (pageObj.loginErrorMsg.getAttribute("textContent").replaceAll("\n", "")).replaceAll("×", "");   //Populates error message with web message
                return;
            }
            waitForPageLoad();
            if (!elementExists(pageObj.addProjBtn))     //Validate Add new project button
            {
                waitForLoadProgress();   //Wait for Load Progress completion if it exists
            }
            if (keywordArr.size()==4)       //To check if the Login keyword has a 4th parameter (True/False) indicating if a new browser has to be opened. This is for logging in as a participant upon redirection.
            {
                if (!elementExists(testDriver.findElement(By.cssSelector("a.dropdown-toggle"))))        //Verify Login
                {
                    keywordResult = false;
                }
            }
            else
            {
                if (!elementExists(pageObj.userLabel))      //Verify Login
                {
                    keywordResult = false;
                }
            }
        } else {
            keywordResult = false;
        }
    }

    public static void Logout(ArrayList<String> keywordArr) throws InterruptedException {
        if (elementExists(pageObj.participantLoutLbl)) {
            pageObj.participantLoutLbl.click();
            elementExists(pageObj.participantLoutLnk);
            pageObj.participantLoutLnk.click();
            waitForPageLoad();
        } else if (elementExists(pageObj.loutTab)) {
            pageObj.loutTab.click();     //Click on the Username labelled dropdown tab on the right top corner
            pageObj.logoutLink.click();    //Click on Logout option
            waitForPageLoad();
        }
        if (!(elementExists(pageObj.logoutMsg))) {
            keywordResult = false;
        }
        testDriver.close();      //Empty the testDriver object
    }

    public static void Goto(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 2) {
            pageObj.getLink(keywordArr.get(1).toString().trim()).click();   //Click on the first Link matching the parsed text
            waitForPageLoad();  //Wait for page reload post clicking the link
        }
    }

    /*public static void BrowseAddPhoto(ArrayList keywordArr)
    {
        if (keywordArr.size()==2)
        {
            pageObj.photoAddBttn.click();
            pageObj.browserPhotoBttn.click();
            //testDriver.getWindowHandle();
        }
    }*/


    public static void AddNewProject(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 3) {
            assertTrue("", pageObj.projectNameTB.isDisplayed());    //Validate the Project Name text box exists
            pageObj.projectNameTB.sendKeys(keywordArr.get(1).toString());   //Populate project name
            testDriver.findElement(By.cssSelector("div.form-field:nth-child(1) > label:nth-child(1)")).click();     //Click outside to enable postback for Permalink generation
            String driverHandle = testDriver.getWindowHandle();
            //testDriver.switchTo().frame(pageObj.redactorFrame(0));     //Switch to Description box frame
            pageObj.descTB(0).sendKeys("\n" + keywordArr.get(2).toString());      //Populate description
            testDriver.switchTo().window(driverHandle);     //Switch back to main frame
            if (keywordArr.size() > 3)      //Validate if Truncate check box input is given
            {
                if (keywordArr.get(3).toString().trim().equals("Truncate"))     //Validate if Truncate CB should be checked
                {
                    pageObj.truncateDescCB.click();     //Check the Truncate option
                }
            }
            String regExp = "[^\\w\\s\\u2014]";
            String regExp2 = "[^\\w]|[\\_]";
            String expPermlink = ((keywordArr.get(1).toString().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());      //Generate permalink based on logic used by Dev
            if (pageObj.permalinkTB.getAttribute("value") == null || pageObj.permalinkTB.getAttribute("value").isEmpty())     //Validate permalink is generated else wait
            {
                Thread.sleep(3000);
            }
            if (pageObj.permalinkTB.getAttribute("value").contains(expPermlink))        //Verify is the Permalink is valid
            {
                pageObj.addProjBtn.click();     //Click on the Add Project button based on updation of permalink
                waitForPageLoad();
                if (CommonFunctions.elementExists(pageObj.projErrorMsg))        //Validate if no errors are thrown upon Add Project event
                {
                    errorMsg = pageObj.projErrorMsg.getAttribute("textContent");
                    return;
                }
            } else
                keywordResult = false;    //Set the Test Case Status to failed
            waitForPageLoad();
        }
    }

    public static void VerifyProject(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() == 2) {
            pageObj.linkByIndex("PROJECTS", 0).click();
            waitForPageLoad();
            waitForLoadProgress();
            if (elementExists(pageObj.projSearchTB)) {
                pageObj.projSearchTB.sendKeys(keywordArr.get(1).toString());
            }
            try {
                if (!testDriver.findElements(By.linkText(keywordArr.get(1).toString())).get(0).isDisplayed())
                {
                keywordResult = false;
                }
                else
                {
                    pageObj.getLink(keywordArr.get(1).toString()).click();
                    waitForPageLoad();
                    pageObj.getLink("DETAILS").click();
                    waitForPageLoad();
                    String truncateStatus="";
                    try
                    {
                        if (pageObj.truncateDescCB.getAttribute("checked")!=null)
                        truncateStatus=pageObj.truncateDescCB.getAttribute("checked");
                        if (pageObj.truncateDescCB.getAttribute("checked").equals("null")) truncateStatus = "false";
                    }
                    catch (NullPointerException e)
                    {
                        truncateStatus = "false";
                    }
                    //System.out.println("Status: "+truncateStatus);
                    String mainTab=testDriver.getWindowHandle();
                    testDriver.switchTo().frame(pageObj.redactorFrame(1));
                    String str = testDriver.findElement(By.xpath("html/body")).getText();
                    String[] strArr = str.trim().replaceAll(Character.toString((char) 10),"").split("\\s+");
                    str= StringUtils.join(strArr," ");
                    String truncDesc = "";
                    if (strArr.length>100)
                    {
                        for (int i = 0; i < 100; i++) {
                            truncDesc = truncDesc + " " + strArr[i];
                        }
                    }
                    else
                        truncDesc=StringUtils.join(strArr," ");
                    testDriver.switchTo().window(mainTab);
                    pageObj.getLink("MANAGE").click();
                    pageObj.getLink("Preview").click();
                    ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
                    testDriver.switchTo().window(tabs.get(1));
                    waitForElement(By.cssSelector("div.truncated-description"));
                    if (testDriver.findElements(By.xpath("div[@class='col-lg-7 project_details' and ./h1[contains(.,'"+ keywordArr.get(1).toString()+"')]]")).size()>1)
                    {
                        keywordResult=false;
                        return;
                    }
                    if (truncateStatus.equals("true"))
                    {
                        String desc = testDriver.findElements(By.cssSelector("div.truncated-description")).get(0).getText().replaceAll(Character.toString((char) 10), "").replaceAll("....Read more", "");
                        if (truncDesc.trim().contains(desc)) {
                            errorMsg = "Project Created. Project Description successfully truncated.";
                            testDriver.switchTo().window(tabs.get(1)).close();
                            testDriver.switchTo().window(tabs.get(0));
                            return;
                        } else keywordResult = false;
                        testDriver.switchTo().window(tabs.get(1)).close();
                        testDriver.switchTo().window(tabs.get(0));
                    }
                    else
                    {
                        String desc=testDriver.findElements(By.cssSelector("div.truncated-description")).get(0).getText().replaceAll(Character.toString((char) 10), "");
                        //System.out.println(str);
                        //System.out.println(desc);
                        if (str.trim().equals(desc.trim()))
                        {
                            errorMsg = "Project Created.";
                            testDriver.switchTo().window(tabs.get(1)).close();
                            testDriver.switchTo().window(tabs.get(0));
                            return;
                        }else keywordResult = false;
                        testDriver.switchTo().window(tabs.get(1)).close();
                        testDriver.switchTo().window(tabs.get(0));
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                keywordResult = false;
            }
        }
    }


    public static void AddSurvey(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 6) {
            if (elementExists(pageObj.projToolsTab)) {
                if (pageObj.projToolsTab.getAttribute("textContent").contains("Surveys & Forms")) {
                    WebElement mngLnk = pageObj.manageLink("survey_tools");
                    mngLnk.click();
                    waitForPageLoad();
                    pageObj.getLink("Add").click();
                    waitForPageLoad();
                    if (elementExists(pageObj.surveyNameTB)) {

                        pageObj.surveyNameTB.sendKeys(keywordArr.get(1).toString().trim());
                        String currHandle = testDriver.getWindowHandle();
                        //testDriver.switchTo().frame(pageObj.redactorFrame(1));
                        pageObj.descTB(1).sendKeys("\n" + keywordArr.get(2).toString().trim());
                        testDriver.switchTo().window(currHandle);
                        pageObj.surveyPermLinkTB.sendKeys(keywordArr.get(3).toString().trim());
                        //testDriver.switchTo().frame(pageObj.redactorFrame(2));
                        pageObj.descTB(2).sendKeys("\n" + keywordArr.get(4).toString().trim());
                        testDriver.switchTo().window(currHandle);
                        if ((keywordArr.get(5).toString().trim()).equals("Verified")) {
                            //Do Nothing
                        } else if ((keywordArr.get(5).toString().trim()).equals("Unverified")) {
                            pageObj.surveyUnverPartModeRB.click();
                        } else if ((keywordArr.get(5).toString().trim()).equals("Anonymous")) {
                            pageObj.surveyAnonPartModeRB.click();
                        }
                        if ((keywordArr.get(6).toString().trim()).equals("Post Multiple Times")) {
                            pageObj.surveyAllowMulCB.click();
                        } else if ((keywordArr.size() == 8)) {
                            pageObj.surveyAllowMulMsgTB.sendKeys(keywordArr.get(7).toString().trim());
                        }
                        pageObj.projToolsCreateBtn.click();
                    }
                } else
                    testDriver.quit();
            } else
                keywordResult = false;
        }
    }

    public static void AddSurveyQuestions(ArrayList keywordArr) throws InterruptedException {
        pageObj.manageLink(keywordArr.get(1).toString()).click();
        waitForPageLoad();
        if (elementExists(pageObj.getLink("Questions"))) {
            for (int i = 2; i < keywordArr.size(); i++) {
                String[] arr = (keywordArr.get(i).toString().trim().split(Character.toString((char) 58)));
                waitForElement(By.linkText("Add"));
                pageObj.getLink("Add").click();
                waitForElement(By.id(Objects.surveyQTypeBoxId));
                int optIndex = setOptionGetIndex(pageObj.dropDownSurveyQType, arr[0]);
                switch (optIndex) {
                    case 0:
                        waitForElement((By.id(Objects.surveyDescQBoxId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        pageObj.surveyQDescTB.sendKeys(arr[2]);
                        pageObj.projToolsCreateBtn.click();
                        break;
                    case 1:
                        waitForElement((By.id(Objects.surveyQLenBoxId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        pageObj.surveyQLenTB.sendKeys(arr[2]);
                        if (arr[3].equals("True")) pageObj.surveyQReqCB.click();
                        pageObj.surveyQNotesTB.sendKeys(arr[4]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 2:
                        waitForElement((By.id(Objects.surveyQReqId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        pageObj.surveyQNotesTB.sendKeys(arr[3]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 3:
                        waitForElement((By.linkText("Add more")));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        String[] str = arr[3].split(",");
                        for (int j = 0; j < str.length; j++) {
                            pageObj.getSurveyOptions(j).sendKeys(str[j]);
                        }
                        pageObj.surveyQNotesTB.sendKeys(arr[4]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 4:
                        waitForElement((By.linkText("Add more")));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        if (arr[3].equals("True")) pageObj.surveyQHorizontalCB.click();
                        String[] str2 = arr[4].split(",");
                        for (int j = 0; j < str2.length; j++) {

                            pageObj.getSurveyOptions(j).sendKeys(str2[j]);

                        }
                        pageObj.surveyQNotesTB.sendKeys(arr[5]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 5:
                        waitForElement((By.linkText("Add more")));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        if (arr[3].equals("True")) pageObj.surveyQHorizontalCB.click();
                        String[] str3 = arr[4].split(",");
                        for (int j = 0; j < str3.length; j++) {

                            pageObj.getSurveyOptions(j).sendKeys(str3[j]);

                        }
                        pageObj.surveyQNotesTB.sendKeys(arr[5]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 6:
                        waitForElement((By.id(Objects.surveyQReqId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        pageObj.surveyQNotesTB.sendKeys(arr[3]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 7:
                        waitForElement((By.id(Objects.surveyQReqId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        pageObj.surveyQNotesTB.sendKeys(arr[3]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 8:
                        waitForElement((By.id(Objects.surveyQReqId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        pageObj.surveyQNotesTB.sendKeys(arr[3]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 9:
                        waitForElement((By.id(Objects.surveyImgTrueId)));
                        pageObj.surveyImgUpTrueRB.click();
                        pageObj.surveyImgLinkQTB.sendKeys(arr[2]);
                        pageObj.surveyQuesTB.sendKeys(arr[3]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 10:
                        waitForElement((By.linkText("Add More Statements")));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        String[] str4 = arr[3].split(",");
                        for (int j = 0; j < str4.length; j++) {

                            pageObj.getSurveyStatements(j).sendKeys(str4[j]);

                        }
                        String[] str5 = arr[4].split(",");
                        for (int j = 0; j < str5.length; j++) {

                            pageObj.getSurveyOptions(j).sendKeys(str5[j]);

                        }
                        pageObj.surveyQNotesTB.sendKeys(arr[5]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 11:
                        waitForElement((By.id(Objects.surveyQLenBoxId)));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        pageObj.surveyQLenTB.sendKeys(arr[2]);
                        if (arr[3].equals("True")) pageObj.surveyQReqCB.click();
                        pageObj.surveyQNotesTB.sendKeys(arr[4]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    case 12:
                        waitForElement((By.linkText("Add more")));
                        pageObj.surveyQuesTB.sendKeys(arr[1]);
                        if (arr[2].equals("True")) pageObj.surveyQReqCB.click();
                        String[] str6 = arr[3].split(",");
                        for (int j = 0; j < str6.length; j++) {

                            pageObj.getSurveyOptions(j).sendKeys(str6[j]);

                        }
                        pageObj.surveyQNotesTB.sendKeys(arr[4]);
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        break;
                    default:
                        break;
                }

            }
            waitForElement(By.linkText("Add"));
        }
    }

    public static void VerifySurvey(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 2) {
            pageObj.manageLink(keywordArr.get(1).toString()).click();
            waitForPageLoad();
            ArrayList<String> arr = new ArrayList<String>();
            for (WebElement element : testDriver.findElements(By.cssSelector("h6.pull-left.nomargin.type"))) {
                arr.add(element.getAttribute("textContent").replaceAll(" ", "_").toLowerCase() + "_answer");
            }
            pageObj.getLink("Surveys & Forms").click();
            waitForPageLoad();
            pageObj.previewLink(keywordArr.get(1).toString()).click();
            ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
            testDriver.switchTo().window(tabs.get(1));
            if (testDriver.getCurrentUrl().contains(keywordArr.get(1).toString()) && testDriver.getCurrentUrl().contains("preview")) {
                for (String str : arr) {
                    if (str.equals("radio_buttons_answer")) str = str.replaceAll("buttons", "button");
                    if (str.equals("checkboxes_answer")) str = str.replaceAll("checkboxes", "check_box");
                    if (str.equals("suburb_answer")) str = str.replaceAll("suburb", "region");
                    if (str.equals("dropdown_answer")) str = str.replaceAll("dropdown", "drop_down");
                    if (str.equals("file_upload_answer")) str = str.replaceAll("file_upload", "file");
                    if (!str.isEmpty()) {
                        if (testDriver.getPageSource().contains(str)) {//Do Nothing
                        } else keywordResult = false;
                    }
                }
            }
            testDriver.switchTo().window(tabs.get(1)).close();
            testDriver.switchTo().window(tabs.get(0));
        }

    }


    public static void AddProjectTool(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 2) {
            WebElement toolName = testDriver.findElement(By.cssSelector("input[label='" + keywordArr.get(1).toString().trim() + "']"));
            String toolCBStatus;
            try {
                toolCBStatus = toolName.getAttribute("checked");
            } catch (NullPointerException e) {
                toolCBStatus = "false";
            }
            if (toolCBStatus == null || toolCBStatus.equals("false")) {
                toolName.click();
                waitForPageLoad();
                pageObj.projSaveToolsBtn.click();
            } else pageObj.getLink("MANAGE").click();
            waitForPageLoad();
        }
    }

    public static void AddNewsFeed(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 4) {
            if (elementExists(pageObj.projToolsTab)) {
                if (pageObj.projToolsTab.getAttribute("textContent").contains("News Feed")) {
                    WebElement mngLnk = pageObj.manageLink("news_feed");
                    mngLnk.click();
                    waitForPageLoad();
                    pageObj.getLink("Add News Article").click();
                    waitForPageLoad();
                    if (elementExists(pageObj.newsPostTitleTB)) {
                        pageObj.newsPostTitleTB.sendKeys(keywordArr.get(1).toString().trim());
                        pageObj.newsPostLinkTB.sendKeys(keywordArr.get(2).toString().trim());
                        String currWinHandle = testDriver.getWindowHandle();
                        //testDriver.switchTo().frame(pageObj.redactorFrame(1));
                        pageObj.descTB(1).sendKeys("\n" + keywordArr.get(3).toString().trim());
                        testDriver.switchTo().window(currWinHandle);
                        if (keywordArr.size() > 5)
                            if (keywordArr.get(4).toString().equals("True")) pageObj.newsPostDescDispCB.click();
                        if (keywordArr.size() > 6)
                            if (keywordArr.get(5).toString().equals("True")) pageObj.newsPostCommentableCB.click();
                        if (keywordArr.size() > 7)
                            if (keywordArr.get(6).toString().equals("True")) pageObj.newsUnverPartCB.click();
                        if (keywordArr.size() > 8)
                            pageObj.newsPostCategoryTB.sendKeys(keywordArr.get(7).toString().trim());
                        pageObj.projToolsCreateBtn.click();
                        waitForPageLoad();
                        if (elementExists(pageObj.newsPostErrorMsg)) {
                            errorMsg = pageObj.newsPostErrorMsg.getText();
                            return;
                        }
                        waitForPageLoad();
                    }

                }
            }
        }
        if (elementExists(pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()))) { //Do nothing
        } else keywordResult = false;
    }

    public static void VerifyNewsForum(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 2) {
            if (elementExists(pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()))) {
                if (elementExists(pageObj.publishLink(keywordArr.get(1).toString().trim().toLowerCase()))) {
                    pageObj.publishLink(keywordArr.get(1).toString().trim().toLowerCase()).click();
                }
                //else if (elementExists())
                pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()).click();
                waitForPageLoad();
                ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
                testDriver.switchTo().window(tabs.get(1));
                //System.out.println(testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent"));
                //System.out.println(keywordArr.get(1).toString());
                if (testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent").trim().equals(keywordArr.get(1).toString().trim())) {
                    testDriver.switchTo().window(tabs.get(1)).close();
                } else {
                    keywordResult = false;
                }
                testDriver.switchTo().window(tabs.get(0));
            }
        }
    }

    public static void AddForum(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 2) {
            if (elementExists(pageObj.projToolsTab)) {
                if (pageObj.projToolsTab.getAttribute("textContent").contains("Forum")) {
                    WebElement mngLnk = pageObj.manageLink("forum_topics");
                    mngLnk.click();
                    waitForPageLoad();
                    pageObj.getLink("Add new topic").click();
                    waitForPageLoad();
                    if (elementExists(pageObj.forumNameTB)) {
                        pageObj.forumNameTB.sendKeys(keywordArr.get(1).toString().trim());
                        if (keywordArr.size() > 3) {
                            String currWinHandle = testDriver.getWindowHandle();
                            //testDriver.switchTo().frame(pageObj.redactorFrame(1));
                            pageObj.descTB(1).sendKeys("\n" + keywordArr.get(2).toString().trim());
                            testDriver.switchTo().window(currWinHandle);
                        }
                        if (keywordArr.size() > 4)
                            if (keywordArr.get(3).toString().equals("True")) pageObj.forumDescTruncCB.click();
                        if (keywordArr.size() > 5)
                            if (keywordArr.get(4).toString().equals("True")) pageObj.forumUnverPartCB.click();
                        if (keywordArr.size() > 6) {
                            Select obj = new Select(pageObj.forumRelMediaDD);
                            obj.selectByVisibleText(keywordArr.get(5).toString().trim());
                            Pattern p = Pattern.compile("(?=\\p{Lu})");
                            String[] s1 = p.split(keywordArr.get(5).toString().trim());
                            String componentId;
                            if (s1.length == 2) {
                                componentId = "forum_topic_related_media_" + (s1[1] + "_" + s1[2]).toLowerCase() + "_id";
                            } else componentId = "forum_topic_related_media_" + s1[0].toLowerCase() + "_id";
                            Select element = new Select(testDriver.findElement(By.id(componentId)));
                            if (element.getOptions().contains(keywordArr.get(6).toString()))
                                element.selectByVisibleText(keywordArr.get(6).toString().trim());
                            if (keywordArr.size() > 8)
                                pageObj.forumTopicLinkTB.sendKeys(keywordArr.get(7).toString().trim());
                            pageObj.projToolsCreateBtn.click();
                            waitForPageLoad();
                            if (elementExists(pageObj.newsPostErrorMsg)) {
                                errorMsg = pageObj.newsPostErrorMsg.getText();
                                return;
                            }
                            waitForPageLoad();
                        }
                    }
                }
            }
            if (elementExists(pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()))) { //Do nothing
            } else keywordResult = false;
        }
    }

    public static void AddNewsLetter(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() > 1) {
            if (elementExists(pageObj.newsLtrSubjectTB)) {
                pageObj.newsLtrSubjectTB.sendKeys(keywordArr.get(1).toString().trim());
                Select bannerDD = new Select(pageObj.newsLtrBannerDD);
                if (!(keywordArr.get(2).toString().isEmpty()))
                    bannerDD.selectByVisibleText(keywordArr.get(2).toString().trim());
                pageObj.newsLtrNextBtn.click();
                waitForPageLoad();
                String winHandle = testDriver.getWindowHandle();
                //testDriver.switchTo().frame(pageObj.redactorFrame(0));
                pageObj.descTB(0).sendKeys("\n" + keywordArr.get(3).toString().trim());
                testDriver.switchTo().window(winHandle);
                pageObj.newsLtrNextBtn.click();
                waitForPageLoad();
                if (pageObj.newsLtrMailPreviewLbl.getAttribute("textContent").contains(keywordArr.get(1).toString().trim())) {
                    pageObj.newsLtrTestEmailTA.sendKeys(keywordArr.get(4).toString().trim());
                    pageObj.newsLtrSendTestMailBtn.click();
                    pageObj.newsLtrNextBtn.click();
                } else {
                    keywordResult = false;
                    return;
                }
                if (keywordArr.get(5).toString().trim().contains(":")) {
                    String[] inputs = keywordArr.get(5).toString().trim().split(Character.toString((char) 58));
                    for (String str : inputs) {
                        setProjParticipants(str);
                    }
                } else {
                    String str = keywordArr.get(5).toString().trim();
                    setProjParticipants(str);
                }
                pageObj.newsLtrNextBtn.click();
                waitForPageLoad();
                waitForElement(By.cssSelector("div[id=any]"));
                // if (keywordArr.get(6).toString().trim().contains(","))
                String[] filters = keywordArr.get(6).toString().trim().split(Character.toString((char) 44));
                for (int i = 0; i < filters.length; i++) {
                    String[] inputs = filters[i].split(Character.toString((char) 58));
                    if (i != 0) {
                        if (inputs[0].toLowerCase().equals("any")) pageObj.linkByIndex("Add Filter", 0);
                        if (inputs[0].toLowerCase().equals("all")) pageObj.linkByIndex("Add Filter", 1);
                        waitForPageLoad();
                    }
                    pageObj.getNewsLtrSelect(inputs[0]).sendKeys(inputs[1]);
                    pageObj.getNewsLtrSelect2(inputs[0]).sendKeys(inputs[2]);
                    pageObj.getNewsLtrFilter(inputs[0]).sendKeys(inputs[3]);
                }
                pageObj.newsLtrNextBtn.click();
                waitForPageLoad();
                if (elementExists(pageObj.signBttn)) pageObj.signBttn.click();
                waitForPageLoad();
            }
        }
    }

    public static void VerifyEmail(ArrayList keywordArr) throws InterruptedException, ParseException {
        JavascriptExecutor executor = (JavascriptExecutor) testDriver;
        executor.executeScript("javascript:window.open('http://www.gmail.com', '_blank');");
        ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
        testDriver.switchTo().window(tabs.get(1));
        pageObj.gmailUsernameTB.sendKeys(keywordArr.get(1).toString().trim());
        pageObj.gmailPasswordTB.sendKeys(keywordArr.get(2).toString().trim());
        pageObj.gmailSignBtn.click();
        waitForPageLoad();
        elementExists(pageObj.mailSubjectLbl);
        WebElement mailTime = pageObj.mailTimeLbl;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date currDate = new Date(System.currentTimeMillis());
        String[] sysTime = (currDate.toString().split(" "));
        String mailDate = (mailTime.getAttribute("textContent").replaceAll(" ", ":00 "));
        long difference = format.parse(sysTime[3]).getTime() - format.parse(mailDate).getTime();
        //System.out.println(difference/(60*1000)%60);
        if ((difference / (60 * 1000) % 60) < 2) ///CHANGE
        {
            if (pageObj.mailSubjectLbl.getAttribute("textContent").contains(keywordArr.get(3).toString().trim())) {
                pageObj.mailSubjectLbl.click();
                waitForPageLoad();
                elementExists(pageObj.mailBodyLbl);
                if (keywordArr.size() >= 6) {
                    if (keywordArr.get(5).toString().equals("Register")) {
                        if (pageObj.mailBodyLbl.getAttribute("textContent").contains("confirmation_token")) {
                            pageObj.mailBodyLbl.findElement(By.cssSelector("a")).click();
                            waitForPageLoad();
                            ArrayList<String> tabs2 = new ArrayList(testDriver.getWindowHandles());
                            testDriver.switchTo().window(tabs2.get(2));
                            waitForElement(By.linkText("Home"));
                            if (elementExists(pageObj.logoutMsg)) {
                                if (pageObj.logoutMsg.getAttribute("textContent").contains("Your account was successfully confirmed. You are now signed in.")) {
                                    testDriver.switchTo().window(tabs2.get(2)).close();
                                } else keywordResult = false;
                            } else keywordResult = false;
                        }
                    }
                } else {
                    if (!(pageObj.mailBodyLbl.getAttribute("textContent").contains(keywordArr.get(4).toString().trim())))
                        keywordResult = false;
                }
            } else {
                keywordResult = false;
                return;
            }
        }
        testDriver.switchTo().window(tabs.get(1)).close();
        testDriver.switchTo().window(tabs.get(0));
    }

    public static void CleanupProjects(ArrayList keywordArr) throws InterruptedException {
        for (WebElement x : testDriver.findElements(By.cssSelector(pageObj.projectsLinkCss))) {
            WebElement y = pageObj.projectLinkTypeLbl;
            //System.out.println(y.getAttribute("textContent"));
            y.findElement(By.cssSelector("a")).click();
            waitForPageLoad();
            pageObj.getLink("Delete").click();
            waitForPageLoad();
            pageObj.projectDeletePsswdTB.sendKeys(keywordArr.get(1).toString().trim());
            pageObj.projectDeleteBtn.click();
            waitForPageLoad();
            waitForElement(By.linkText("Add new project"));
        }
    }

    public static void RegisterUser(ArrayList keywordArr) throws InterruptedException, MalformedURLException {
        if (keywordArr.size() > 4) {
            browserGrid();
            testDriver.get(siteUrl);
            waitForPageLoad();
            pageObj.registerBtn.click();
            waitForPageLoad();
            pageObj.registerNameTB.sendKeys(keywordArr.get(1).toString().trim());
            pageObj.registerMailTB.sendKeys(keywordArr.get(2).toString().trim());
            pageObj.registerPsswdTB.sendKeys(keywordArr.get(3).toString().trim());
            pageObj.registerPsswdConfmTB.sendKeys(keywordArr.get(3).toString().trim());
            pageObj.registerfrmSuburbDRL.click();
            waitForElement(By.cssSelector(pageObj.registerSuburbTB));
            pageObj.registerfrmSuburbTB.sendKeys(keywordArr.get(4).toString().trim());
            waitForElement(By.cssSelector(pageObj.registerSuburbOpt));
            pageObj.registerSuburbSelectLI.click();
            pageObj.registerTnCCB.click();
            pageObj.signBttn.click();
            waitForPageLoad();
            if (elementExists(pageObj.logoutMsg)) {
                if (pageObj.logoutMsg.getAttribute("textContent").contains("You have been signed up successfully.")) {
                    return;
                } else errorMsg = pageObj.logoutMsg.getAttribute("textContent");
            } else keywordResult = false;
        } else {
            keywordResult = false;
        }
        testDriver.close();
    }

    public static void ActivateUser(ArrayList keywordArr) throws InterruptedException {
        String rowIndex = testDriver.findElement(By.xpath(".//tr[contains(td[1],'" + keywordArr.get(1).toString().trim() + "')]")).getAttribute("rowIndex");
        if (elementExists(testDriver.findElement(By.xpath(".//tr[" + rowIndex + "]/td[6]/div/a[text()='Activate user']")))) {
            testDriver.findElement(By.xpath(".//tr[" + rowIndex + "]/td[6]/div/a[text()='Activate user']")).click();
            waitForPageLoad();
            if (elementExists(testDriver.findElement(By.xpath(".//tr[" + rowIndex + "]/td[6]/div[text()='User activated']")))) {
                return;
            } else keywordResult = false;
        } else keywordResult = false;
    }

    public static void VerifyUserActivation(ArrayList keywordArr) {
        String rowIndex = testDriver.findElement(By.xpath(".//tr[contains(td[1],'" + keywordArr.get(1).toString().trim() + "')]")).getAttribute("rowIndex");
        if (testDriver.findElement(By.xpath(".//tr[" + rowIndex + "]/td[6]")).getAttribute("textContent").trim().equals("Yes")) {
            return;
        } else keywordResult = false;
    }

    public static void AddComment(ArrayList keywordArr) throws InterruptedException {
        //System.out.println(keywordArr.get(1).toString().trim());
        pageObj.forumLink(keywordArr.get(1).toString().trim()).click();
        waitForPageLoad();
        if (elementExists(pageObj.addCommentBtn)) {
            pageObj.addCommentBtn.click();
            waitForVisibility(pageObj.addCommentTA);
            pageObj.addCommentTA.sendKeys(keywordArr.get(2).toString().trim());
            if (keywordArr.get(3).toString().trim().equals("False")) {
                pageObj.commentNotifyCB.click();
            }
            if (keywordArr.size() >= 5) pageObj.commentMailTB.sendKeys(keywordArr.get(4).toString().trim());
            if (keywordArr.size() >= 6) pageObj.commentScreenNameTB.sendKeys(keywordArr.get(5).toString().trim());
            pageObj.signBttn.click();
            waitForPageLoad();
            waitForVisibility(pageObj.addCommentBtn);
            if (!(pageObj.commentWrapperLbl.getAttribute("textContent").equals(keywordArr.get(2).toString().trim()))) {
                keywordResult = false;
                return;
            }
        }
    }

    public static void Site(ArrayList keywordArr) throws InterruptedException, MalformedURLException {
        browserGrid();
        waitForPageLoad();
    }

    public static void Close(ArrayList keywordArr) {
        testDriver.close();
    }

    public static void AddQuickPoll(ArrayList keywordArr) {
        if (elementExists(pageObj.projToolsTab)) {
            if (pageObj.projToolsTab.getAttribute("textContent").contains("Quick Poll")) {
                pageObj.manageLink("quick_polls").click();
                waitForPageLoad();
                pageObj.addSurveyBtn.click();
                waitForPageLoad();
                if (elementExists(pageObj.qPollQuesTB)) {
                    pageObj.qPollQuesTB.sendKeys(keywordArr.get(1).toString().trim());
                    String[] pollsOptions = keywordArr.get(2).toString().split(Character.toString((char) 58));
                    if (pollsOptions.length > pageObj.qPollOptionsTB.size()) {
                        while (pollsOptions.length != pageObj.qPollOptionsTB.size()) {
                            pageObj.getLink("Add more").click();
                            waitForPageLoad();
                        }
                    }
                    for (int i = 0; i < pollsOptions.length; i++) {
                        pageObj.qPollOptionsTB.get(i).sendKeys(pollsOptions[i]);
                    }
                    if (!(keywordArr.get(3).toString().isEmpty()))
                        pageObj.qPollMsgTA.sendKeys(keywordArr.get(3).toString().trim());
                    pageObj.qPollPermLnkTB.sendKeys(keywordArr.get(4).toString().trim());
                    if (keywordArr.size() > 5) {
                        if (keywordArr.get(5).toString().trim().equals("True")) pageObj.qPollUnverPartCB.click();
                    }
                    pageObj.projToolsCreateBtn.click();
                    waitForPageLoad();
                }
                if (!(elementExists(testDriver.findElement(By.xpath(".//li/div/div[1]/h4[text()='" + keywordArr.get(1).toString().trim() + "']"))))) {
                    keywordResult = false;
                }
            }
        }
    }

    public static void VerifyQuickPoll(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size() >= 2) {
            if (elementExists(pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()))) {
                if (elementExists(pageObj.publishLink(keywordArr.get(1).toString().trim().toLowerCase()))) {
                    pageObj.publishLink(keywordArr.get(1).toString().trim().toLowerCase()).click();
                }
                //else if (elementExists())
                pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()).click();
                waitForPageLoad();
                ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
                testDriver.switchTo().window(tabs.get(1));
                //System.out.println(testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent"));
                //System.out.println(keywordArr.get(1).toString());
                if (elementExists(testDriver.findElement(By.xpath(".//h2/a[text()='" + keywordArr.get(1).toString().trim() + "']")))) {
                    testDriver.switchTo().window(tabs.get(1)).close();
                } else {
                    keywordResult = false;
                }
                testDriver.switchTo().window(tabs.get(0));
            }
        }
    }


    public static void DeleteProject(ArrayList keywordArr) throws InterruptedException
    {
        waitForElement(By.cssSelector(pageObj.projectsLinkCss));
        if (elementExists(testDriver.findElement(By.xpath("//*[@id='project-listing']/tbody/tr/td/a[text()='" + keywordArr.get(2).toString().trim() + "']")))) {
            WebElement y = (testDriver.findElement(By.xpath("//*[@id='project-listing']/tbody/tr/td/a[text()='" + keywordArr.get(2).toString().trim() + "']")));
            //System.out.println(y.getAttribute("textContent"));
            y.click();
            waitForPageLoad();
            if (testDriver.findElement(By.cssSelector("div.project-publish")).getAttribute("textContent").contains("Unpublish"))
            {
                pageObj.getLink("Unpublish").click();
                //waitForPageLoad();
                Alert alert = testDriver.switchTo().alert();
                alert.accept();
                waitForPageLoad();
            }
            pageObj.getLink("Delete").click();
        }
        waitForPageLoad();
        pageObj.projectDeletePsswdTB.sendKeys(keywordArr.get(1).toString().trim());
        pageObj.projectDeleteBtn.click();
        waitForPageLoad();
        waitForElement(By.linkText("Add new project"));
    }



    public static void PublishProject(ArrayList keywordArr)
    {
        if (elementExists(pageObj.projPublishLnk))
        {
            pageObj.projPublishLnk.click();
            waitForPageLoad();
            waitForVisibility(pageObj.publishProjNowRB);
            pageObj.publishProjNowRB.click();
            testDriver.findElements(By.name("commit")).get(1).click();
            waitForPageLoad();
        }
    }

    public static void ViewPublishedProject(ArrayList keywordArr) throws MalformedURLException, InterruptedException {
        alternateUrl=siteUrl+"/"+keywordArr.get(1).toString().trim();
        browserGrid();
        waitForPageLoad();
        alternateUrl="";
    }

    public static void BrowserTab(ArrayList keywordArr)
    {
        JavascriptExecutor executor=(JavascriptExecutor)testDriver;
        executor.executeScript("javascript:window.open('" +keywordArr.get(1).toString().trim()+"', '_blank');");
        waitForPageLoad();
        ArrayList<String> tabs=new ArrayList<String>(testDriver.getWindowHandles());
        testDriver.switchTo().window(tabs.get(1));
    }

    public static void CloseTab(ArrayList keywordArr)
    {
        ArrayList<String> tabs=new ArrayList<String>(testDriver.getWindowHandles());
        for (int i=0;i<tabs.size();i++)
        {
            if (testDriver.switchTo().window(tabs.get(i)).getCurrentUrl().equals(keywordArr.get(1).toString().trim()))
            {
                testDriver.switchTo().window(tabs.get(i)).close();
            }
        }

    }

//======================
// INTERNAL FUNCTIONS
//======================

//Logout in case of Failure
    public static void logout() throws InterruptedException {
        ArrayList<String> arr=new ArrayList<String>();
        arr.add("Logout");
        Logout(arr);
    }


//Wait for the page to load
    public static boolean waitForPageLoad()
    {
        while (!(((JavascriptExecutor)testDriver).executeScript("return document.readyState").equals("complete")))
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

//Goto a link using link text and link index on the page as inputs
    public static void gotoLink(String linkName,Integer index)
    {
        pageObj.linkByIndex(linkName, index).click();
    }

    public static String createResultFile(String testSuiteName,String oldResultLog) throws IOException
    {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String dateString = (format.format(date));
        String resultFileName = testSuiteName + "PASSED - Sanity Suite " + dateString + ".txt";
        //String folderPath = testSuiteName;
        File directory = new File(testSuiteName);
        //File oldDirectory= new File(testSuiteName+"/workspace/");
        File[] fList = directory.listFiles();
        for (File file : fList)
        {
            file.renameTo(new File(oldResultLog+"/"+file.getName()));
        }
        FileWriter fileWriter = new FileWriter(resultFileName,true);
        fileWriter=null;
        //System.out.println(resultFileName);
        return resultFileName;
    }

//Write Results to a text file in the Suite folder currently being executed
    public static void writeResultLog(String scriptName,ArrayList keywordArr,String fileName) throws IOException, InterruptedException {
        //FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, false));
        if (errorMsg.isEmpty())
        {
            bufferedWriter.write("\n" + "FAILED: " + scriptName + keywordArr);
        }
        else logResult(scriptName,keywordArr,fileName);
        bufferedWriter.close();
        File file=new File(fileName);
        file.renameTo(new File(fileName.replaceAll("PASSED","FAILED")));
        file=null;
    }

//Log Results in case of testing negative scenarios
    public static void logResult(String scriptName,ArrayList keywordArr,String fileName) throws IOException {
        if (!(errorMsg.isEmpty()))
        {
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName, false));
            bufferedWriter.write("\n"+"Passed: "+scriptName+keywordArr+ "\n" +" Message: " +errorMsg);
            bufferedWriter.close();
        }
    }

//Verify if an Object physically exists on the page
    public static boolean elementExists(WebElement element)
    {
        try
        {
        element.isDisplayed();
        return true;
        }
        catch (NoSuchElementException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {return false;}
    }

    public static void waitForLoadProgress()
    {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (elementExists(pageObj.loadProgress))    //Validates presence of Page load bar
        {
            while (Float.parseFloat((pageObj.loadProgress.getAttribute("style").replaceAll("width: ","")).replaceAll("%;",""))>103) //Validates load progress
            {
                try
                {
                    Thread.sleep(1000);    //Waits for 10 seconds if the page has not appeared
                }
                catch (InterruptedException e)
                {
                    //Do Nothing
                }
            }
        }

    }

    public static boolean waitForElement(By locator)
    {
        WebElement element2;
        WebDriverWait wait=new WebDriverWait(testDriver,60);
        try
        {
            element2=wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        }
        catch (NoSuchElementException ex)
        {
            keywordResult=false;
            //testDriver.quit();
            return false;
        }
        if (element2!=null && element2.isDisplayed())
        {
            return true;
        }
        else
        {
            keywordResult=false;
            //testDriver.quit();
            return false;
        }
    }


    public static  void browserGrid() throws MalformedURLException, InterruptedException
    {
        if (browser.equals("chrome"))
        {
            System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        }
        if (browser.equals("internet explorer"))
        {
            System.setProperty("webdriver.ie.driver","src/main/resources/IEDriverServer.exe");
        }
        URL url=new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities cap=new DesiredCapabilities();
        cap.setBrowserName(browser);
        cap.setJavascriptEnabled(true);
        testDriver=new RemoteWebDriver(url,cap);
        pageObj= new Objects(testDriver);
        if (alternateUrl==null||alternateUrl.isEmpty())
        {
            testDriver.get(siteUrl);
        }
        else
        {
            testDriver.get(alternateUrl);
        }
        testDriver.manage().window().maximize(); //Pass URL to browser
        waitForPageLoad();
    }


    public static HSSFSheet getSheet(String filePath) throws IOException
    {
        //Extracting input from Script Input XLS file
        String fileName =filePath;
        POIFSFileSystem fileSystem = new POIFSFileSystem(new FileInputStream(fileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheetAt(0); //Get Excel Sheet
        fileSystem=null;
        return sheet;
    }

    public static void setSuiteInputs(HSSFSheet sheet)
    {
        ArrayList inputArr=new ArrayList();
        HSSFRow row=sheet.getRow(1);
        Iterator<Cell> cells = row.cellIterator();
        while (cells.hasNext())
        {
            HSSFCell cell = (HSSFCell) cells.next();
            String cellValue = cell.getStringCellValue();
            inputArr.add(cellValue);
        }
        if (inputArr.size()<row.getLastCellNum())
        {
           // fail("Invalid Input provided for one or more values. Hence, stopping further Test execution.");
            System.exit(0);
        }

        //Extracting input from Script Input File and assigning it to Global Variables
        browserParameters(inputArr);

    }


    public static ArrayList<File> returnCases(String folderPath)
    {
        ArrayList<File> files=new ArrayList<File>();
        //String folderPath = testSuiteName;
        File directory = new File(folderPath);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList)
        {
            if (file.isFile())
            {
                files.add(file);
            } else if (file.isDirectory())
            {
                returnCases(file.getAbsolutePath());
            }
        }
        return files;
    }

    public static void browserParameters(ArrayList inputArr)
    {
        testSuiteName=inputArr.get(0).toString();
        browser=inputArr.get(1).toString();
        outputSuite=inputArr.get(2).toString();
        appModule=inputArr.get(4).toString();
        resultLogSuite=inputArr.get(3).toString();
        siteUrl=inputArr.get(5).toString();
    }

    public static int setOptionGetIndex(WebElement dropdown,String optionText)
    {
       Select dropDown=new Select(dropdown);
       dropDown.selectByVisibleText(optionText);
       return Integer.parseInt((dropDown.getFirstSelectedOption().getAttribute("index")));
    }

    public static void setProjParticipants(String str)
    {
        String newsLtrPartProj="input[label='"+ str +"']";
        testDriver.findElement(By.cssSelector(newsLtrPartProj)).click();
    }

    public static boolean waitForVisibility(WebElement element)
    {
        WebElement element2;
        WebDriverWait wait=new WebDriverWait(testDriver,60);
        try
        {
            element2 = wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }
        catch (NoSuchElementException ex)
        {
            keywordResult=false;
            //testDriver.quit();
            return false;
        }
    }


}



