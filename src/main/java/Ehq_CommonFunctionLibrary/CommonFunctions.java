package Ehq_CommonFunctionLibrary;

import Ehq_Object_Repository.Objects;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    //public static WebDriver testDriver= new FirefoxDriver();



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

    public static void Login (ArrayList keywordArr) throws InterruptedException, MalformedURLException {
        if (keywordArr.size()==3)
        {
            browserGrid();
            testDriver.get("http://expproj.abc.engagementhq.com/login");
            waitForPageLoad();
            pageObj.userNameTB.sendKeys(keywordArr.get(1).toString());
            pageObj.passwordTB.sendKeys(keywordArr.get(2).toString());
            pageObj.signBttn.click();
            /*if (pageObj.loadProgress.isDisplayed())
            {
                while (!pageObj.loadProgress.getAttribute("nodeValue").equals("width: 104%;"))
                {
                    Thread.sleep(2000);
                }
            }
            //if (pageObj.loadProgress.isDisplayed())*/
            Thread.sleep(10000);
            waitForPageLoad();
            assertTrue("Sign in Failed", waitForElement(pageObj.userLabel));
        }
        else
        {
            keywordResult = false;
            //fail("Incorrect number of parameters passed.Please check the test script.Hence failing test.");

        }
    }

    public static void Logout(ArrayList keywordArr) throws InterruptedException {
       if (loutEnabled.equals("Enabled"))
       {
           pageObj.loutTab.click();
           Objects.getLink(keywordArr.get(0).toString().trim()).click();
           testDriver.quit();
       }
    }

    public static void Goto(ArrayList keywordArr) throws InterruptedException
    {
        if (keywordArr.size()==2)
        {
            Objects.getLink(keywordArr.get(1).toString().trim()).click();
            waitForPageLoad();
        }
    }

    public static void gotoLink(String linkName,Integer index)
    {
       pageObj.linkByIndex(linkName, index).click();
    }

    public static void browseAddPhoto(ArrayList keywordArr)
    {
        if (keywordArr.size()==2)
        {
            pageObj.photoAddBttn.click();
            pageObj.browserPhotoBttn.click();
            //testDriver.getWindowHandle();
        }
    }

    public static boolean waitForPageLoad() throws InterruptedException
    {
        while (!(((JavascriptExecutor)testDriver).executeScript("return document.readyState").equals("complete")))
        {
            Thread.sleep(1000);
        }
        return true;
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


    public static void AddNewProject(ArrayList keywordArr) throws InterruptedException
    {
        if (keywordArr.size()>=3)
        {
            assertTrue("", pageObj.projectNameTB.isDisplayed());
            pageObj.projectNameTB.sendKeys(keywordArr.get(1).toString());
            testDriver.findElement(By.cssSelector("div.form-field:nth-child(1) > label:nth-child(1)")).click();
            String driverHandle = testDriver.getWindowHandle();
            testDriver.switchTo().frame(pageObj.projDescriptionTB);
            testDriver.findElement(By.cssSelector("body > p > br")).sendKeys("\n" + keywordArr.get(2).toString());
            testDriver.switchTo().window(driverHandle);
            if (keywordArr.size() > 3)
            {
                if (keywordArr.get(3).toString().trim().equals("Truncate"))
                {
                    pageObj.truncateDescCB.click();
                }
            }
            String regExp = "[^\\w\\s\\u2014]";
            String regExp2 = "[^\\w]|[\\_]";
            String expPermlink=((keywordArr.get(1).toString().replaceAll(regExp, "")).replaceAll(regExp2, "-").toLowerCase());
            if (pageObj.permalinkTB.getAttribute("value")==null || pageObj.permalinkTB.getAttribute("value").isEmpty())
            {
                Thread.sleep(3000);
            }
            if (pageObj.permalinkTB.getAttribute("value").contains(expPermlink))
            {
                pageObj.addProjBtn.click();
            } else //fail("test failed, permalink not updated.");
            keywordResult=false;
            waitForPageLoad();
        }
    }

    public static void VerifyNewProject(ArrayList keywordArr) throws InterruptedException
    {
            if (keywordArr.size()==2)
            {
                Objects.linkByIndex("PROJECTS",0).click();
                waitForPageLoad();
                if (testDriver.findElements(By.linkText(keywordArr.get(1).toString())).get(0).isDisplayed()!=true)
                {
                    keywordResult=false;

                }
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

    public static void writeResultLog(String scriptName,ArrayList keywordArr,String fileName) throws IOException
    {
        FileWriter fileWriter = new FileWriter(fileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("\n" + "FAILED: "+scriptName+ keywordArr );
        bufferedWriter.close();
    }
}


