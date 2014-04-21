package ehq.Automated_Test_Suite;

import Ehq_CommonFunctionLibrary.CommonFunctions;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.junit.Test;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import static Ehq_CommonFunctionLibrary.CommonFunctions.*;


public class AppTest

{


   @Test

    public void testApp() throws Exception
    {


        HSSFSheet inputSheet=getSheet("/Users/Ridhi/ehq_automation_test_suite/Script Input.xls");
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
                    if (!CommonFunctions.keywordResult)
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
        CommonFunctions funcLib=new CommonFunctions();
        Class libClass = funcLib.getClass();
        String keyword=keyArr.get(0).toString().trim();
        Method method = libClass.getMethod(keyword, iList.getClass());
        method.invoke(this, keyArr);
    }


}