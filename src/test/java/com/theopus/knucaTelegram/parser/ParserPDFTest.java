package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.bot.MessageHandleService;
import com.theopus.knucaTelegram.data.entity.Lesson;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by irina on 04.07.17.
 */
public class ParserPDFTest {
    @Test
    public void parseLesson() throws Exception {
        ParserPDF parserPDF = new ParserPDF("pdfs/КСМ-3.pdf");
        Lesson l = parserPDF.parseLesson("12:20 Вища математика (Практ.з.); [ ауд.349] ас. Мартинюк О.Г.", "КН-11 Понеділок", null);
    }

}