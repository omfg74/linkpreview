package ru.omfgdevelop.linkpreview;

import org.junit.Before;
import org.junit.Test;

import ru.omfgdevelop.linkpreview.utils.LinkParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class LinkParcerTest {
    LinkParser linkParser;
    @Before
   public void prepare() {
        linkParser= new LinkParser();
    }
    @Test
    public void test(){
        String link = linkParser.parse("https://www.google.com/");
        assertNotNull(link);
        assertSame("https://www.google.com/",link);
    }


}

