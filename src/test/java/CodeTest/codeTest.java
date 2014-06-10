package CodeTest;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

public class codeTest
{
    @Test
    public void testCode() throws InterruptedException, IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
    {
        //Code for testing goes here
        Pattern p = Pattern.compile("(?=\\p{Lu})");
        String[] s1 = p.split("Photo");
        String componentId;
        if (s1.length==2) {componentId="forum_topic_related_media_"+(s1[1]+"_"+s1[2]).toLowerCase()+"_id";}
        else componentId=s1[0];
        System.out.println(componentId);
    }
}