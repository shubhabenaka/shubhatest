package Ehq_CommonFunctionLibrary;

import Ehq_Object_Repository.Objects;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;
import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;

/**
 * Created by Ridhi on 31/03/14.
 */
public class CommonFunctions
{
    public static String testSuiteName;
    public static String browser;
    public static String hostIP;
    public static String appModule;
    public static String loutEnabled;
    public static WebDriver testDriver;
    public static boolean keywordResult=true;
    public static Objects pageObj;
    public static String errorMsg;           //A global reference that can be set by each function to report error messages this also prevents reporting keyword failure if we were expecting it
    //public static WebDriver testDriver= new FirefoxDriver();



    public static void Login (ArrayList keywordArr) throws InterruptedException, MalformedURLException
    {
        if (keywordArr.size()==3)
        {
            browserGrid();   //Reference to Browser and grid management Function
            testDriver.get("http://expproj.abc.engagementhq.com/login"); //Pass URL to browser
            waitForPageLoad();
            pageObj=new Objects(testDriver);
            pageObj.userNameTB.sendKeys(keywordArr.get(1).toString());  //Populate Username
            pageObj.passwordTB.sendKeys(keywordArr.get(2).toString());  //Populate Password
            pageObj.signBttn.click();                                   //Click on Sign in button
            if (elementExists(pageObj.loginErrorMsg))   //Validates presence of Error Message
            {
              errorMsg=(pageObj.loginErrorMsg.getAttribute("textContent").replaceAll("\n","")).replaceAll("×","");   //Populates error message with web message

            }
            waitForLoadProgress();   //Wait for Load Progress completion if it exists
            if (!elementExists(pageObj.addProjBtn))     //Validate Add new project button
            {
                Thread.sleep(10);
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

    public static void Logout(ArrayList keywordArr) throws InterruptedException
    {
           pageObj.loutTab.click();     //Click on the Username labelled dropdown tab on the right top corner
           pageObj.getLink(keywordArr.get(0).toString().trim()).click();    //Click on Logout option
           if (!elementExists(pageObj.logoutMsg))
           {
               keywordResult=false;
           }
           testDriver.quit();      //Empty the testDriver object
    }

    public static void Goto(ArrayList keywordArr) throws InterruptedException
    {
        if (keywordArr.size()==2)
        {
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
            String regExp = "[^\\w\\s\\u2014]";
            String regExp2 = "[^\\w]|[\\_]";
            String expPermlink=((keywordArr.get(1).toString().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());      //Generate permalink based on logic used by Dev
            if (pageObj.permalinkTB.getAttribute("value")==null || pageObj.permalinkTB.getAttribute("value").isEmpty())     //Validate permalink is generated else wait
            {
                Thread.sleep(3000);
            }
            if (pageObj.permalinkTB.getAttribute("value").contains(expPermlink))        //Verify is the Permalink is valid
            {
                pageObj.addProjBtn.click();     //Click on the Add Project button based on updation of permalink
                waitForPageLoad();
                if (CommonFunctions.elementExists(pageObj.projErrorMsg))        //Validate if no errors are thrown upon Add Project event
                {
                    errorMsg=pageObj.projErrorMsg.getAttribute("textContent");
                }
            } else
            keywordResult=false;    //Set the Test Case Status to failed
            waitForPageLoad();
        }
    }

    public static void VerifyProject(ArrayList keywordArr) throws InterruptedException
    {
            if (keywordArr.size()==2)
            {
                pageObj.linkByIndex("PROJECTS", 0).click();
                waitForPageLoad();
                waitForLoadProgress();
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





//======================
// INTERNAL FUNCTIONS
//======================




//Wait for the page to load
    public static boolean waitForPageLoad() throws InterruptedException
    {
        while (!(((JavascriptExecutor)testDriver).executeScript("return document.readyState").equals("complete")))
        {
            Thread.sleep(1000);
        }
        return true;
    }

//Goto a link using link text and link index on the page as inputs
    public static void gotoLink(String linkName,Integer index)
    {
        pageObj.linkByIndex(linkName, index).click();
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
    public static void writeResultLog(String scriptName,ArrayList keywordArr,String fileName) throws IOException
    {
        FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        if (errorMsg.isEmpty() || errorMsg==null)
        {
            bufferedWriter.write("\n" + "FAILED: " + scriptName + keywordArr);
        }
        bufferedWriter.close();
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
        pageObj= new Objects(testDriver);
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
            fail("Invalid Input provided for one or more values. Hence, stopping further Test execution.");
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


