package com.theopus.knucaTelegram.parser.objects;

import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import com.theopus.knucaTelegram.parser.ver20.ParserUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayLessonListSheet {
    private DayOfWeek dayOfWeek;
    private Set<LessonLineSheet> lessonList;
    private String text;
    private GroupSheet parent;

    DayLessonListSheet(DayOfWeek dayOfWeek, String text, GroupSheet parent) {
        this.dayOfWeek = dayOfWeek;
        this.text = text;
        this.parent = parent;
    }

    public Set<LessonLineSheet> parse(){
        return makeLessonLineSheets(parseLessonLinesText(text));
    }

    private Set<LessonLineSheet> makeLessonLineSheets(Set<String> lines){
        Set<LessonLineSheet> result = new LinkedHashSet<>();
        LessonOrder tmpLO = null;

        for (String s : lines) {
            String[] lesson = s.split(" ");
            String order = lesson[0];
            StringBuilder line = new StringBuilder();
            for (int i = 1; i <lesson.length; i++) {
                line.append(lesson[i]).append(" ");
            }
            if (!order.contains("--"))
                tmpLO = ParserUtils.timeToOrder(order);
            result.add(new LessonLineSheet(line.toString().trim(),this,tmpLO));
        }

        lessonList = result;
        return result;
    }

    private Set<String> parseLessonLinesText(String text){
        text = text.replaceAll("\n", " ");
        text = text.replaceAll("\r", " ");
        text = text.replaceAll("\\s+",  " ");

        Pattern begin = Pattern.compile("((\\d?\\d:\\d\\d)|(--\"--))");
        Pattern finish = Pattern.compile("((\\d?\\d:\\d\\d)|(--\"--)|(-{4,5}))");
        Matcher mb = begin.matcher(text);
        Matcher mf = finish.matcher(text);
        Set<String> result = new LinkedHashSet<>();


        while (mb.find()){
            if (mf.find(mb.end())){
                result.add(text.substring(mb.start(), mf.start()));
            }
            else
                result.add(text.substring(mb.start()));
        }

        return result;
    }

    public GroupSheet getParent() {
        return parent;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}
