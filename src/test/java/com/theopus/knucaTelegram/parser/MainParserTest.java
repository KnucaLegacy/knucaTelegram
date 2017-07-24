package com.theopus.knucaTelegram.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainParserTest {
    @Test
    public void parseFolder() throws Exception {
        MainParser.parseFolder("pdfs");
    }

}