package Automated_Test_Suite;

import org.junit.runner.JUnitCore;

/**
 * Created by Ridhi on 06/05/14.
 */
public class TestRunner
{
    public static void main(String[] args)
    {
        JUnitCore.runClasses(AppTest.class);
    }
}
