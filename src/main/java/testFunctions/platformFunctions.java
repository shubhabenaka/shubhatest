package testFunctions;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import testObjects.platformObjects;
import org.openqa.selenium.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;


/**
 * Created by shubha on 16/7/14.
 */
public class platformFunctions {

    public static String testSuiteName;
    public static String browser;
    public static String hostIP;
    public static String appModule;
    public static String loutEnabled;
    public static WebDriver testDriver;
    public static boolean keywordResult=true;
    public static platformObjects pageObj;
    public static String errorMsg;
    public  static String regExp = "[^\\w\\s\\u2014]";
    public  static String regExp2 = "[^\\w]|[\\_]";


    public static void Login (ArrayList keywordArr) throws InterruptedException, MalformedURLException
    {
        if (keywordArr.size()>=3) {

            browserGrid();   //Reference to Browser and grid management Function
            testDriver.get("http://helium.engagementhq.com/users/sign_in"); //Pass URL to browser
            testDriver.manage().window().maximize();
            waitForPageLoad();
            pageObj=new platformObjects(testDriver);
            pageObj.userNameTB.sendKeys(keywordArr.get(1).toString());  //Populate Username
            pageObj.passwordTB.sendKeys(keywordArr.get(2).toString());  //Populate Password
            pageObj.signBttn.click();                                   //Click on Sign in button
            if (elementExists(pageObj.loginErrorMsg))   //Validates presence of Error Message
            {
                errorMsg=(pageObj.loginErrorMsg.getAttribute("textContent").replaceAll("\n","")).replaceAll("×","");   //Populates error message with web message

            }
            //waitForLoadProgress();   //Wait for Load Progress completion if it exists
            if (!elementExists(pageObj.addProjBtn))     //Validate Add new project button
            {
                Thread.sleep(2000);
            }
            if (!elementExists(pageObj.userLabel))
            {
                keywordResult = false;
            }
        }
        else
        {
            keywordResult = false;
        }
    }

    public static void AddNewProject(ArrayList keywordArr) throws InterruptedException
    {
        if (keywordArr.size()>=3)
        {
            assertTrue("", pageObj.projectNameTB.isDisplayed());    //Validate the Project Name text box exists
            pageObj.projectNameTB.sendKeys(keywordArr.get(1).toString());   //Populate project name
            testDriver.findElement(By.cssSelector("div.form-field:nth-child(1) > label:nth-child(1)")).click();     //Click outside to enable postback for Permalink generation
            String driverHandle = testDriver.getWindowHandle();
            testDriver.switchTo().frame(pageObj.projDescriptionTB);     //Switch to Description box frame
            testDriver.findElement(By.cssSelector("body > p > br")).sendKeys("\n" + keywordArr.get(2).toString());      //Populate description
            testDriver.switchTo().window(driverHandle);     //Switch back to main frame
            if (keywordArr.size() > 3)      //Validate if Truncate check box input is given
            {
                if (keywordArr.get(3).toString().trim().equals("Truncate"))     //Validate if Truncate CB should be checked
                {
                    pageObj.truncateDescCB.click();     //Check the Truncate option
                }
            }

            String expPermlink=((keywordArr.get(1).toString().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());      //Generate permalink based on logic used by Dev
            if (pageObj.permalinkTB.getAttribute("value")==null || pageObj.permalinkTB.getAttribute("value").isEmpty())     //Validate permalink is generated else wait
            {
                Thread.sleep(3000);
            }
            if (pageObj.permalinkTB.getAttribute("value").contains(expPermlink))        //Verify is the Permalink is valid
            {
                pageObj.addProjBtn.click();     //Click on the Add Project button based on updation of permalink
                waitForPageLoad();
                if (platformFunctions.elementExists(pageObj.projErrorMsg))        //Validate if no errors are thrown upon Add Project event
                {
                    errorMsg=pageObj.projErrorMsg.getAttribute("textContent");
                }
            } else
                keywordResult=false;    //Set the Test Case Status to failed
            waitForPageLoad();
        }
    }

    public static void Logout(ArrayList<String> keywordArr) throws InterruptedException
    {
        pageObj.loutTab.click();     //Click on the Username labelled dropdown tab on the right top corner
        pageObj.logoutLink.click();    //Click on Logout option
        waitForPageLoad();
        if (!(elementExists(pageObj.logoutMsg)))
        {
            keywordResult=false;
        }
        testDriver.close();      //Empty the testDriver object
    }

    //Logout in case of Failure
    public static void logout() throws InterruptedException {
        ArrayList<String> arr=new ArrayList<String>();
        arr.add("Logout");
        Logout(arr);
    }


    public static void VerifyProject(ArrayList keywordArr) throws InterruptedException
    {
        if (keywordArr.size()==2)
        {
            pageObj.linkByIndex("PROJECTS", 0).click();
            waitForPageLoad();
            //waitForLoadProgress();
            waitForElement(By.linkText("Add new project"));
            if (elementExists(pageObj.projSearchTB))
            {
                pageObj.projSearchTB.sendKeys(keywordArr.get(1).toString());
            }
            if (!testDriver.findElements(By.linkText(keywordArr.get(1).toString())).get(0).isDisplayed())
            {
                keywordResult=false;
            }
        }
    }

    public static void AddProjectTool(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size()>=2)
        {
            WebElement toolName=null;
            if (keywordArr.get(1).toString().equals("Surveys and Forms")) toolName=pageObj.surveyToolsCB;
            if (keywordArr.get(1).toString().equals("News Feed")) toolName=pageObj.newsToolsCB;
            if (keywordArr.get(1).toString().equals("Forum")) toolName=pageObj.forumToolsCB;
            String toolCBStatus;
            try {
                toolCBStatus=toolName.getAttribute("checked");
            }catch (NullPointerException e){
                toolCBStatus="false";
            }
            if (toolCBStatus==null ||toolCBStatus.equals("false"))
            {
                toolName.click();
                waitForPageLoad();
                testDriver.findElement(By.cssSelector(".btn.btn-primary.btn.btn-primary.js-submit[value=Save]")).click();

               // pageObj.projSaveToolsBtn.click();
            }
            else pageObj.getLink("MANAGE").click();
            waitForPageLoad();
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
                            testDriver.switchTo().frame(pageObj.redactorFrame(1));
                            pageObj.descTB.sendKeys("\n" + keywordArr.get(2).toString().trim());
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
                            System.out.println(s1);
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
                            if (elementExists(pageObj.newsPostErrorMsg))
                            {
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



    public static void VerifyNewsForum(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size()>=2)
        {
            String forumName=((keywordArr.get(1).toString().trim().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());

            if (elementExists(pageObj.previewLink(forumName)))
            {
                pageObj.publishLink(keywordArr.get(1).toString().trim().toLowerCase()).click();
                pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()).click();
                waitForPageLoad();
                ArrayList<String> tabs=new ArrayList<String>(testDriver.getWindowHandles());
                testDriver.switchTo().window(tabs.get(1));
                //System.out.println(testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent"));
                //System.out.println(keywordArr.get(1).toString());
                if (testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent").trim().equals(keywordArr.get(1).toString().trim()))
                {
                    testDriver.switchTo().window(tabs.get(1)).close();
                }
                else {keywordResult=false;}
                testDriver.switchTo().window(tabs.get(0));
            }
        }
    }

    public static void ReplytolatestComment(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size()>=2)
        {

                if (elementExists(pageObj.projToolsTab)) {
                    if (pageObj.projToolsTab.getAttribute("textContent").contains("Forum")) {
                        WebElement mngLnk = pageObj.manageLink("forum_topics");
                        mngLnk.click();
                        waitForPageLoad();
                    }
                }


            /*String forumName=((keywordArr.get(1).toString().trim().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());
                if(elementExists(pageObj.previewLink(forumName))) {
                    pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()).click();*/
                    testDriver.findElement(By.cssSelector(".btn-group-seperators[target=_blank]")).click();
                    waitForPageLoad();
                    ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
                    testDriver.switchTo().window(tabs.get(1));

                    if (testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent").trim().equals(keywordArr.get(1).toString().trim())) {
                        pageObj.addYourCommentBttn.click();
                        pageObj.commentTextArea.sendKeys(keywordArr.get(2).toString().trim());
                        pageObj.commentSubmitBttn.click();
                        waitForPageLoad();
                        waitForElement((By.cssSelector(".comment > .content")));

                        WebElement replyButton = testDriver.findElements(By.linkText("Reply")).get(0);
                        replyButton.click();
                        waitForElement(pageObj.replyTextArea);
                        pageObj.replyTextArea.sendKeys(keywordArr.get(3).toString().trim());
                        pageObj.replySubmitBttn.click();
                        waitForPageLoad();

                    } else {
                        keywordResult = false;
                    }
                    testDriver.switchTo().window(tabs.get(0));
               // }
        }
    }

    public static void ValidateComment(ArrayList keywordArr) throws InterruptedException {
        if (keywordArr.size()>=2)
        {

            if (elementExists(pageObj.projToolsTab)) {
                if (pageObj.projToolsTab.getAttribute("textContent").contains("Forum")) {
                    WebElement mngLnk = pageObj.manageLink("forum_topics");
                    mngLnk.click();
                    waitForPageLoad();
                }
            }


            /*String forumName=((keywordArr.get(1).toString().trim().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());
                if(elementExists(pageObj.previewLink(forumName))) {
                    pageObj.previewLink(keywordArr.get(1).toString().trim().toLowerCase()).click();*/
            testDriver.findElement(By.cssSelector(".btn-group-seperators[target=_blank]")).click();
            waitForPageLoad();
            ArrayList<String> tabs = new ArrayList<String>(testDriver.getWindowHandles());
            testDriver.switchTo().window(tabs.get(1));

            if (testDriver.findElement(By.cssSelector("h1")).getAttribute("textContent").trim().equals(keywordArr.get(1).toString().trim())) {
                pageObj.addYourCommentBttn.click();
                waitForElement(pageObj.commentTextArea);
                String commentText = pageObj.commentTextArea.getText();
                pageObj.commentSubmitBttn.click();

                if (commentText.equalsIgnoreCase(""))
                {
                    WebDriverWait wait = new WebDriverWait(testDriver, 5);
                    wait.until(ExpectedConditions.alertIsPresent());
                    Alert alert = testDriver.switchTo().alert();
                    if (alert.getText().equalsIgnoreCase("comment cant be left blank"))
                    {
                        alert.accept();
                        System.out.println("Comment was left blank, hence closing test.");

                    }
                }
            } else {
                keywordResult = false;
            }
            testDriver.switchTo().window(tabs.get(0));
            // }
        }
    }

    public static void CleanupProjects(ArrayList keywordArr) throws InterruptedException {
        for (WebElement x:testDriver.findElements(By.cssSelector(pageObj.projectsLinkCss)))
        {
            WebElement y=pageObj.projectLinkTypeLbl;
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

    //Wait for the page to load
    public static boolean waitForPageLoad() throws InterruptedException
    {
        while (!(((JavascriptExecutor)testDriver).executeScript("return document.readyState").equals("complete")))
        {
            Thread.sleep(1000);
        }
        return true;
    }

    public static void Goto(ArrayList keywordArr) throws InterruptedException
    {
        if (keywordArr.size()==2)
        {
            pageObj.getLink(keywordArr.get(1).toString().trim()).click();   //Click on the first Link matching the parsed text
            waitForPageLoad();  //Wait for page reload post clicking the link
        }
    }

    //Goto a link using link text and link index on the page as inputs
    public static void gotoLink(String linkName,Integer index)
    {
        pageObj.linkByIndex(linkName, index).click();
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

    public static String createResultFile(String testSuiteName) throws IOException
    {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        String dateString = (format.format(date));
        String resultFileName = testSuiteName + "Test Result Log " + dateString + ".txt";
        FileWriter fileWriter = new FileWriter(resultFileName,true);
        fileWriter=null;
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
        bufferedWriter.close();
        File file=new File(fileName);
        file.renameTo(new File(fileName.replaceAll("PASSED","FAILED")));
        file=null;
    }

    //Verify if an Object physically exists on the page

    public static boolean elementExists(WebElement element)
    {
        try
        {
            element.isDisplayed();
            return true;
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {return false;}
    }

    public static void waitForLoadProgress()
    {
        if (elementExists(pageObj.loadProgress))    //Validates presence of Page load bar
        {
            while (Integer.parseInt((pageObj.loadProgress.getAttribute("nodeValue").replaceAll("width: ","")).replaceAll("%",""))>103) //Validates load progress
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

    public static boolean waitForElement(WebElement element)
    {
        WebElement element2;
        WebDriverWait wait=new WebDriverWait(testDriver,60);
        try
        {
            element2=wait.until(ExpectedConditions.visibilityOf(element));
        }
        catch (NullPointerException ex)
        {
            keywordResult=false;
            return false;
        }
        if (element2!=null && element2.isDisplayed())
        {
            return true;
        }
        else
        {
            keywordResult=false;
            return false;
        }
    }


    public static  void browserGrid() throws MalformedURLException
    {
        URL url=new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities cap=new DesiredCapabilities();
        cap.setBrowserName(browser);
        cap.setJavascriptEnabled(true);
        testDriver=new RemoteWebDriver(url,cap);
        pageObj= new platformObjects(testDriver);
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
            //fail("Invalid Input provided for one or more values. Hence, stopping further Test execution.");
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
        hostIP=inputArr.get(2).toString();
        appModule=inputArr.get(3).toString();
        loutEnabled=inputArr.get(4).toString();
    }


}
