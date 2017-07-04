package com.theopus.knucaTelegram.parser;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by irina on 04.07.17.
 */
public class ParserPDFTest {
    @Test
    public void parseLesson() throws Exception {
        ParserPDF parserPDF = new ParserPDF("pdfs/КСМ-3.pdf");
        parserPDF.parseLesson("12:20 Вища математика (Практ.з.); [ ауд.349] ас. Мартинюк О.Г.", "КН-11 Понеділок", null);
    }

}