package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.parser.core.MainParser;
import org.junit.Test;

public class MainParserTest {
    @Test
    public void parseFolder() throws Exception {

    }

    @Test
    public void parseNew() throws Exception {
        for (int i = 0; i < 2; i++) {
            MainParser.parseFolder("pdfs");
        }
    }
}