package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import com.theopus.knucaTelegram.parser.core.MainParser;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Oleksandr_Tkachov on 9/11/2017.
 */
public class KnucaTelegramApplicationTest extends GenericDBWithDBCheck{

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void loadAll() throws Exception {
        lessonService.saveAll(MainParser.parseFolder("pdfs"));
    }
}