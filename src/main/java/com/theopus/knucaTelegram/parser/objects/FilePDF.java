package com.theopus.knucaTelegram.parser.objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilePDF {

    private static final Logger log = LoggerFactory.getLogger(FilePDF.class);

    private String filename;
    private String text;
    private Set<GroupSheet> sheets;


    public FilePDF(String path) throws IOException {
        File file = new File(path);
        this.text = pdfToText(file);
        this.text = edit(this.text);
        this.filename = file.getName();
    }


    public FilePDF(File file) throws IOException {
        this.text = pdfToText(file);
        this.text = edit(this.text);
        this.filename = file.getName();

    }
    private String edit(String text){
        text = text.replaceAll("\r\n", "\n");
        text = text.replaceAll("=+", "=");
        text = StringUtils.replaceChars(text,"ACEHIKMOPTaceikoptXxBb","АСЕНІКМОРТасеікортХхВв");
        text = text.replaceAll("\\?", "і");
        return text;
    }

    private String pdfToText(File file) throws IOException {
        if (!file.getName().contains("pdf")){
            log.error("PDFTOTEXT| not a pdf file - ", file.getName());
            throw new IllegalArgumentException("not a pdf file");
        }

        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        String text = null;

        try {
            parser = new PDFParser(new FileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            text = pdfStripper.getText(pdDoc);
        } catch (Exception e) {
            log.error("PDFTOTEXT", e);
        }
        finally {
            if (pdDoc != null)
                pdDoc.close();
            if (cosDoc!= null)
                cosDoc.close();
        }
        return text;
    }

    private final static Pattern GROUP_SHEET_SPLITTER = Pattern.compile("КТОУП|$");

    public Set<GroupSheet> parse(){
        Set<String> groupSheetText = new LinkedHashSet<>();
        Matcher m = GROUP_SHEET_SPLITTER.matcher(text);
        List<Integer> pos = new ArrayList<>();

        while (m.find()) {
            pos.add(m.start());
            if (text.substring(m.start(), m.end()).matches("$"))
                break;
        }

        for (int i = 1; i < pos.size(); i++) {
            groupSheetText.add(text.substring(pos.get(i - 1), pos.get(i)));
        }

        Set<GroupSheet> result = new LinkedHashSet<>();

        groupSheetText.forEach(s -> {
            result.add(new GroupSheet(s,this));
        });
        this.sheets = result;
        return result;
    }


}
