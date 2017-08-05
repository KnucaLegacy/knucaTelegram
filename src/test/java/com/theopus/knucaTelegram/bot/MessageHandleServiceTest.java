package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.parser.ParserPDF;
import org.junit.Test;

public class MessageHandleServiceTest {
    @Test
    public void lessonToString() throws Exception {
        ParserPDF parserPDF = new ParserPDF("pdfs/КСМ-3.pdf");
        Lesson l = parserPDF.parseLesson("12:20 Вища математика (Практ.з.); [ ауд.349] ас. Мартинюк О.Г.", "КН-11 Понеділок", null);
//        System.out.println(new MessageHandleService().lessonToString(l, new Date()));
    }

    @Test
    public void dateFormatTest() throws Exception {
//        System.out.println(new MessageHandleService().(DayOfWeek.MONDAY,new Date(), new GroupProxy("Test-28")));
    }
}