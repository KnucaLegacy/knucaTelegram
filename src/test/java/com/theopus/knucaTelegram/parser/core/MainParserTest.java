package com.theopus.knucaTelegram.parser.core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oleksandr_Tkachov on 9/11/2017.
 */
public class MainParserTest {

    @Test
    public void test() throws Exception {
        MainParser.parseFolder("pdfs").forEach(System.out::println);

    }
}