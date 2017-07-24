package com.theopus.knucaTelegram.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

public class FolderParserTest {

    @Test
    public void parseFolder() throws Exception {
        System.out.println(new FolderParser().parseFolder("pdfstest").size());
    }

}