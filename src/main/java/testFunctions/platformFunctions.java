package testFunctions;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.firefox.FirefoxDriver;
import testObjects.platformObjects;
import org.openqa.selenium.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;



/**
 * Created by shubha on 16/7/14.
 */
public class platformFunctions {

    public static WebDriver testDriver;
    public static platformObjects pageObj;
    public static String browser;
    //public static ArrayList arr;
    public static boolean keywordResult=true;

    public static void Login (ArrayList arr) throws InterruptedException, MalformedURLException
    {
        if (arr.size()>=3) {
            WebDriver testDriver = new FirefoxDriver();
            testDriver.get("https://twitter.com/?lang=en");
            testDriver.manage().window().maximize();//Pass URL to browser
            //waitForPageLoad();
            Thread.sleep(2000);
            pageObj = new platformObjects(testDriver);
            pageObj.userLoginTB.sendKeys(arr.get(1).toString());  //Populate Username
            pageObj.userPasswordTB.sendKeys(arr.get(2).toString());  //Populate Password
            pageObj.signInBttn.click();                                   //Click on Sign in button
            //waitForPageLoad();
            Thread.sleep(2000);
            pageObj.loutTab.click();
            if (!(elementExists(pageObj.logoutLink)))
            {
                keywordResult=false;
            }
            testDriver.close();

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


}
