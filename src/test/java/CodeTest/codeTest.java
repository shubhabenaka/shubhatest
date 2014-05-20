package CodeTest;

import org.junit.Test;

public class codeTest
{

    @Test

    public void testCode() throws InterruptedException

    {
    //Code for testing goes here

       /* WebDriver testDriver=new FirefoxDriver();
        testDriver.get("http://expproj.abc.engagementhq.com/users/sign_in"); //Pass URL to browser
        Objects pageObj=new Objects(testDriver);
        CommonFunctions.testDriver=testDriver;
        CommonFunctions.waitForPageLoad();

        pageObj.userNameTB.sendKeys("ehqtesting");
        pageObj.passwordTB.sendKeys("ehqtesting_btt");
        pageObj.signBttn.click();
        CommonFunctions.waitForPageLoad();
        if (CommonFunctions.elementExists(pageObj.loginErrorMsg))
        {
            System.out.println((pageObj.loginErrorMsg.getAttribute("textContent").replaceAll("\n", "")).replaceAll("×", ""));
        }
        if (CommonFunctions.elementExists(pageObj.loadProgress))
            {
                while (!pageObj.loadProgress.getAttribute("nodeValue").equals("width: 104%;"))
                {
                    Thread.sleep(10000);
                }
            }
        pageObj.linkByIndex("Add new project",0).click();
        pageObj.addProjBtn.click();
        if (CommonFunctions.elementExists(pageObj.projErrorMsg))
        {
            System.out.println(pageObj.projErrorMsg.getAttribute("textContent"));
        }*/
String pageObj="width: 104%";
        int x= Integer.parseInt((pageObj.replaceAll("width: ","")).replaceAll("%",""));
        System.out.println(x);
        //if (pageObj.loadProgress.isDisplayed())

    }

}

