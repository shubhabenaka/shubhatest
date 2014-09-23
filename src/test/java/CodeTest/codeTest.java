package CodeTest;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

public class codeTest {
    @Test
    public void testCode() throws InterruptedException, IOException
    {
        //Test Code goes here

        //LOGIN
        /*WebDriver driver = new FirefoxDriver();
        driver.get("http://autotuned.engagementhq.com/testproject-1/forum_topics/forum-18");
        Thread.sleep(2000);
        Objects obj = new Objects(driver);
        CommonFunctions.testDriver = driver;
        obj.userNameTB.sendKeys("ehqtesting");
        obj.passwordTB.sendKeys("ehqtesting_btt");
        obj.signBttn.click();
        Thread.sleep(5000);*/

        URL url=new URL("http://localhost:4444/wd/hub");
        DesiredCapabilities cap=new DesiredCapabilities();
        cap.setBrowserName("firefox");
        cap.setJavascriptEnabled(true);
        WebDriver driver=new RemoteWebDriver(url,cap);

    }

}
