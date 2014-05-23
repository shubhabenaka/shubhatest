package Automated_Test_Suite;

import org.junit.runner.JUnitCore;

/**
 * Created by Ridhi on 06/05/14.
 */
public class TestRunner
{
    public static void main(String[] args)
    {
        String arg= args[0];
        AppTest.filename=arg;
        JUnitCore.runClasses(AppTest.class);
    }
}
