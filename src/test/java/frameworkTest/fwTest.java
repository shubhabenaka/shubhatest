package frameworkTest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import testFunctions.platformFunctions;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import testFunctions.platformFunctions;


/**
 * Created by shubha on 17/7/14.
 */
public class fwTest {

    public static String filename;
    ArrayList arr=new ArrayList();
    public static String browser;

    @Test

    public void driverScript() throws Exception
    {

        arr.add("Login");
        arr.add("testing@bangthetable.com");
        arr.add("ehqtesting_btt");

        executeKeywords(arr);

    }

    public void executeKeywords(ArrayList arr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        ArrayList<String> iList = new ArrayList();
        platformFunctions funcLib=new platformFunctions();
        Class libClass = funcLib.getClass();
        String keyword=arr.get(0).toString().trim();
        //System.out.println(keyArr);
        Method method = libClass.getMethod(keyword, iList.getClass());
        method.invoke(this, arr);
    }
}
