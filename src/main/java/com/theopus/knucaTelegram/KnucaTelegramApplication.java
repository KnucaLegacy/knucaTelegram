package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.parser.FolderParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KnucaTelegramApplication {



    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(KnucaTelegramApplication.class, args);
//        run.getBean(FolderParser.class).parseFolder("pdfs");
//        ParserPDF parserPDF = new ParserPDF("pdfs/НОЗ-1.pdf");
//        parserPDF.parseLessonList(null);
    }
}
