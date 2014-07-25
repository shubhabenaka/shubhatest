package frameworkTest;

import testFunctions.platformFunctions;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.Test;

import static testFunctions.platformFunctions.*;
import java.io.File;
import java.lang.String;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;




/**
 * Created by shubha on 17/7/14.
 */
public class fwTest {

    public static String filename;
    ArrayList arr=new ArrayList();

    @Test

    public void driverScript() throws Exception
    {
        filename="/home/shubha/ScriptInput.xls";
        HSSFSheet inputSheet=getSheet(filename);
        setSuiteInputs(inputSheet);
        String resultFile=createResultFile(testSuiteName+"/");

        //===========================================
        //Picking Test Cases From Test Suite
        //===========================================

        ArrayList<File> scriptsList= returnCases(testSuiteName);
        for(File testScript:scriptsList)
        {
            String scriptName=testScript.getName();
            if (testScript.getName().contains(".xls"))
            {

                ArrayList<String> keywordArr=new ArrayList<String>();
                HSSFSheet scriptSheet=getSheet(testScript.toString());
                Iterator rowCounter=scriptSheet.rowIterator();
                rowLoop:
                while(rowCounter.hasNext())
                {
                    HSSFRow myRow = (HSSFRow) rowCounter.next();
                    Iterator cellCounter = myRow.cellIterator();
                    while(cellCounter.hasNext())
                    {
                        HSSFCell myCell = (HSSFCell) cellCounter.next();
                        keywordArr.add(myCell.getStringCellValue());
                    }
                    executeKeywords(keywordArr);
                    if (!platformFunctions.keywordResult)
                    {
                        writeResultLog(scriptName,keywordArr,resultFile);
                        break rowLoop;
                    }
                    keywordArr.clear();
                }
            }
        }

    }



    public void executeKeywords(ArrayList keyArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        ArrayList<String> iList = new ArrayList();
        platformFunctions funcLib=new platformFunctions();
        Class libClass = funcLib.getClass();
        String keyword=keyArr.get(0).toString().trim();
        Method method = libClass.getMethod(keyword, iList.getClass());
        method.invoke(this, keyArr);
    }
}
