package com.theopus.knucaTelegram.parser;

import com.theopus.knucaTelegram.data.entity.*;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import com.theopus.knucaTelegram.data.entity.enums.LessonType;
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

/**
 PDF Schedule Parser for KNUCA University Ukraine
 */

//TODO: REFACTOR IT
public class ParserPDF {

    private static final Logger log = LoggerFactory.getLogger(ParserPDF.class);

    private String parsedText;
    public Date upDate;
    private Calendar calendar;

    public ParserPDF(String path) {
        this.parsedText = parseToText(new File(path));
        upDate = parseUpDate(this.parsedText);
    }

    public ParserPDF(File file){
        this.parsedText = parseToText(file);
        upDate = parseUpDate(this.parsedText);

    }

    private String parseToText(File file){
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
            System.out.println(text);
            System.out.println("-----------------------------------------asdasdasd-----------asdasd");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (cosDoc != null)
                    cosDoc.close();
                if (pdDoc != null)
                    pdDoc.close();
            } catch (Exception e1) {
                e.printStackTrace();
            }

        }

        try {
            parser.clearResources();
            cosDoc.close();
            pdDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private Date parseUpDate(String parsedPDFtext){
        String text = parsedPDFtext;
        Pattern datePat = Pattern.compile("\\d?\\d\\s[А-я]+\\s\\d{4}");
        Matcher matcher = datePat.matcher(text);

        String dateString = "";
        if (matcher.find())
            dateString = text.substring(matcher.start(),matcher.end());
        int day = Integer.parseInt(dateString.split(" ")[0]);
        int month = ukrMothToNumber(dateString.split(" ")[1]);
        int year = Integer.parseInt(dateString.split(" ")[2]);

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,day);
        this.calendar = gregorianCalendar;
        Date date = gregorianCalendar.getTime();

        return date;
    }

    public Map<String, String> parseToLessonGroupMap(){

        String text = this.parsedText;
        text = StringUtils.replaceChars(text,"ACEHIKMOPTaceikoptXxBb","АСЕНІКМОРТасеікортХхВв");
        text = text.replaceAll("\n", "");
        text = text.replaceAll("\r", "");
        text = text.replaceAll("=", " ");
        text = text.replaceAll("\\s+",  " ");
        System.out.println(text);
        Pattern dayOfAWeek = Pattern.compile("" +
                "([^|]Понеділок)|" +
                "([^|]Вівторок)|" +
                "([^|]Середа)|" +
                "([^|]Четвер)|" +
                "([^|]П'ятниця)" +
                "|([^|]Субота)");
        Matcher mworkdayBegin = dayOfAWeek.matcher(text);
        Matcher mworkdayFinish = dayOfAWeek.matcher(text);

        Pattern begin = Pattern.compile("((\\d?\\d:\\d\\d)|(--\"--))");
        Pattern finish = Pattern.compile("((\\d?\\d:\\d\\d)|(--\"--)|(-{4,5}))");
        Matcher mb = begin.matcher(text);
        Matcher mf = finish.matcher(text);

        List<Integer> groupsIndexes = new ArrayList<Integer>();
        Map<Integer, String> groupMap = new LinkedHashMap<Integer, String>();
        Pattern groupLinePattern = Pattern.compile("(академгрупа)(\\s)([\\S]+)");
        Matcher groupMatcher = groupLinePattern.matcher(text);

        Pattern pattern = Pattern.compile("\\d?\\d\\.\\d\\d.{1,100}((В|в)сього)");
        Matcher toDayGroupMathcer = pattern.matcher(text);

        String currentMaxtoDate = null;

        while (groupMatcher.find()){
//            if (toDayGroupMathcer.find())
//                System.out.println("---------------today--------------" + toDayGroupMathcer.group());
            groupsIndexes.add(groupMatcher.end());
            groupMap.put(groupMatcher.end(),text.substring(groupMatcher.start(),groupMatcher.end()).split(" ")[1]);
        }

        Map<String, String> lessonGroup = new LinkedHashMap<String, String>();
        List<String> strings = new ArrayList<>();


        if (toDayGroupMathcer.find()){
            String tmp = toDayGroupMathcer.group().trim();
            String[] test = tmp.split("\\|");
            currentMaxtoDate = test[0];
        }



        String currentDay = "";
        if (mworkdayBegin.find()){
            currentDay = text.substring(mworkdayBegin.start(),mworkdayBegin.end());
            System.out.println(currentDay);
            mworkdayFinish.find();
            System.out.println(text.substring(mworkdayFinish.start(),mworkdayFinish.end()));
            mworkdayFinish.find();
            System.out.println(text.substring(mworkdayFinish.start(),mworkdayFinish.end()));
        }

        boolean isOk = true;

        for (int i = 0;mb.find();){
            if (mf.find(mb.end())) {
                if(isOk) {
                    if ((mf.end() > mworkdayBegin.start()) && (mf.end() < mworkdayFinish.start())) {

                    } else {
                        isOk = mworkdayBegin.find();
                        isOk = mworkdayFinish.find();
                        currentDay = text.substring(mworkdayBegin.start(), mworkdayBegin.end());
                        System.out.println(currentDay);
                    }
                }
                if (i != groupsIndexes.size() - 1) {
                    if (mf.start() > groupsIndexes.get(i) && mf.start() < groupsIndexes.get(i + 1)) ;
                    else {
                        if (toDayGroupMathcer.find())
                            currentMaxtoDate = toDayGroupMathcer.group().trim().split("\\|")[0];
                        i++;
                    }
                    lessonGroup.put(text.substring(mb.start(), mf.start()).trim(),groupMap.get(groupsIndexes.get(i)) + currentDay + " " + currentMaxtoDate);
                    strings.add(text.substring(mb.start(), mf.start()).trim());
                }
                else {
                    lessonGroup.put(text.substring(mb.start(), mf.start()).trim(),groupMap.get(groupsIndexes.get(i)) + currentDay + " " + currentMaxtoDate);
                    strings.add(text.substring(mb.start(), mf.start()).trim());
                }
            }
        }
//        print(strings);
        printMap(lessonGroup);
        System.out.println(groupMap);
        System.out.println(groupsIndexes);
        return lessonGroup;
    }

    public Set<Group> getGroups(){
        String text = this.parsedText;
        Set<Group> groups = new HashSet<>();
        Pattern groupLinePattern = Pattern.compile("(академгрупа)(\\s)([\\S]+)");
        Matcher groupMatcher = groupLinePattern.matcher(text);
        while (groupMatcher.find()){
            groups.add(new Group(text.substring(groupMatcher.start(),groupMatcher.end()).split(" ")[1]));
        }
        return groups;
    }

    public List<Lesson> parseLessonList(List<Lesson> lessons){
        List<Lesson> lessonList;
        if (lessons == null)
            lessonList = new ArrayList<Lesson>();
        else
            lessonList = lessons;

        Map<String,String> lessonGroup = parseToLessonGroupMap();

        String previousTime = null;

        for (Map.Entry<String,String> pair: lessonGroup.entrySet()) {

            String tmpPTime = pair.getKey().split(" ")[0];
            Lesson preAdd = null;
            if (!tmpPTime.equals("--\"--")) {
                preAdd = parseLesson(pair.getKey(), pair.getValue(), null);
                if (!lessonList.contains(preAdd)) {
                    lessonList.add(preAdd);
                }
                else {
                    System.out.println("HAS A DUPLICATE AND SAME TO");
                    System.out.println(lessonList.get(lessonList.indexOf(preAdd)));
                    System.out.println("---------------------------");
                }
                previousTime = tmpPTime;
            }
            else {
                preAdd = parseLesson(pair.getKey(), pair.getValue(), previousTime);
                if (!lessonList.contains(preAdd)) {
                    lessonList.add(preAdd);
                }
                else {
                    System.out.println("HAS A DUPLICATE AND SAME TO");
                    System.out.println(lessonList.get(lessonList.indexOf(preAdd)));
                    System.out.println("---------------------------");
                }
            }

        }
        return lessonList;
    }

    public Lesson parseLesson(String lessonLine, String labeledGroupPlusDayPlusMaxToDay, String previousTime){
        String lessonTime = (previousTime == null ? lessonLine.split(" ")[0] : previousTime);
        System.out.println( lessonTime + " " + lessonLine + " " + labeledGroupPlusDayPlusMaxToDay);

        String labeledGroup = labeledGroupPlusDayPlusMaxToDay.split(" ")[0].trim();
        String labeledDayOfWeek = labeledGroupPlusDayPlusMaxToDay.split(" ")[1].trim();
        String labeledMaxToDay = labeledGroupPlusDayPlusMaxToDay.split(" ")[2].trim();

        Lesson lesson = new Lesson();
        lesson.setDayOfWeek(stringToDayOfWeek(labeledDayOfWeek));
        lesson.setOrder(timeToOrder(lessonTime));
        lesson.setSubject(new Subject(parseSubjName(lessonLine)));
        lesson.setLessonType(parseLessonType(lessonLine));
        lesson.setRoomTimePeriod(parseRoomTimePeriod(lessonLine, labeledMaxToDay, lesson.getDayOfWeek()));
        lesson.setGroups(parseGroups(lessonLine,labeledGroup));
        lesson.setTeachers(parseTeachers(lessonLine));
        System.out.println(lesson);
        return lesson;
    }

    private String parseSubjName(String lessonLine){
        StringBuilder result = new StringBuilder("");
        String tmp = lessonLine.split(";")[0];
        String[] line = tmp.split(" ");
        for (int i = 1; i < line.length-1 ; i++) {
            result.append(line[i]).append(i == line.length-1 ? "" : " ");
        }
        return result.toString().trim();
    }

    private LessonType parseLessonType(String lessonLine){
        String tmp = lessonLine.split(";")[0];
        String[] line = tmp.split(" ");
        tmp = line[line.length - 1];
        tmp = tmp.toLowerCase();

        Pattern lect = Pattern.compile("((.+)?)лек((.+)?)");
        Pattern prakt = Pattern.compile("((.+)?)пра((.+)?)");
        Pattern lab = Pattern.compile("((.+)?)лаб((.+)?)");

        Matcher m = lect.matcher(tmp);
        if (m.matches())
            return LessonType.LECTION;

        m = prakt.matcher(tmp);
        if (m.matches())
            return LessonType.PRACT;

        m = lab.matcher(tmp);
        if (m.matches())
            return LessonType.LAB;


        return LessonType.ELSE;
    }

    private Set<RoomTimePeriod> parseRoomTimePeriod(String lessonLine, String labeledMaxToDate, DayOfWeek dayOfWeek){
        List<RoomTimePeriod> result = new ArrayList<>();
        Date maxToDate = stringToDate(labeledMaxToDate, dayOfWeek);

        List<String> a = new ArrayList<>();
        Pattern leftBrace = Pattern.compile("\\[");
        Pattern rightBrace = Pattern.compile("\\]");
        Matcher ml = leftBrace.matcher(lessonLine);
        Matcher mr = rightBrace.matcher(lessonLine);
        while (ml.find()){
            if (mr.find())
                a.add(lessonLine.substring(ml.start()+1,mr.start()).trim());
        }

        Pattern pattern = Pattern.compile("");
        Pattern patternRoom = Pattern.compile("ауд\\.[^].,\\s]+");
        Pattern singleDate = Pattern.compile("(([^доз]\\s)|^)\\d?\\d\\.\\d\\d");
        Pattern singlePureDate = Pattern.compile("\\d?\\d\\.\\d\\d");
        Pattern fromDate = Pattern.compile("з\\s?\\d?\\d\\.\\d\\d");
        Pattern toDate = Pattern.compile("до\\s?\\d?\\d\\.\\d\\d");

        Matcher matcher;
        Matcher matcher2;


        Collections.reverse(a);

        RoomTimePeriod roomTimePeriod;
        Set<LessonDate> lessonDates = null;
        Room previousRoom = null;

        for (String s : a) {
            matcher = patternRoom.matcher(s);
            if (matcher.find()) {
                previousRoom = new Room(s.substring(matcher.start(), matcher.end()));
                roomTimePeriod = new RoomTimePeriod(previousRoom);
//                System.out.println(s.substring(matcher.start(), matcher.end()));
            }
            else{
                if (previousRoom == null){
                    roomTimePeriod = new RoomTimePeriod(new Room("NoScheduledRoom"));
                }
                else roomTimePeriod = new RoomTimePeriod(previousRoom);
            }

            matcher = singleDate.matcher(s);
            while (matcher.find()) {
                matcher2 = singlePureDate.matcher(s);
                if (matcher2.find(matcher.start())) {
                    String notParsedDate = s.substring(matcher2.start(), matcher2.end()).trim();
                    Date parsedDate = stringToDate(notParsedDate,null);
                    roomTimePeriod.addLessonDate(new LessonDate(parsedDate));
//                    System.out.println("single date: " + notParsedDate);
                }
            }

            List<String> toDates = new ArrayList<>();
            List<String> fromDates = new ArrayList<>();

            matcher = fromDate.matcher(s);
            if (matcher.find()){
                do {
                    String notParsedFromDate = s.substring(matcher.start() + 2,matcher.end()).trim();
                    Date parsedFromDate = stringToDate(notParsedFromDate,null);
//                    System.out.print("from date: " + notParsedFromDate);
                    matcher2 = toDate.matcher(s);
                    if (matcher2.find(matcher.start())) {
                        String notParsedToDate = s.substring(matcher2.start() + 3, matcher2.end()).trim();
                        Date parsedToDate = stringToDate(notParsedToDate,null);
                        roomTimePeriod.addLessonDate(new LessonDate(parsedFromDate,parsedToDate));
                        toDates.add(notParsedToDate);
                        fromDates.add(notParsedFromDate);
//                        System.out.println(" to date: " + notParsedToDate);
                    }
                }while (matcher.find());
            }
            matcher = toDate.matcher(s);
            if (matcher.find()){
                String toDateStr = s.substring(matcher.start() + 3,matcher.end());
                if (!toDates.contains(toDateStr)) {
                    String notParsedDate = s.substring(matcher.start() + 3, matcher.end()).trim();
                    Date parsedDate = stringToDate(s.substring(matcher.start() + 3, matcher.end()),null);
                    roomTimePeriod.addLessonDate(new LessonDate(false ,parsedDate));
//                    System.out.println("only to date: " + notParsedDate);
                }
            }
            matcher = fromDate.matcher(s);
            if (matcher.find()){
                String notParsedFromDate = s.substring(matcher.start() + 2,matcher.end()).trim();
                if (!fromDates.contains(notParsedFromDate)){
                    Date parsedDate = stringToDate(notParsedFromDate, null);
                    roomTimePeriod.addLessonDate(new LessonDate(parsedDate,maxToDate));
                }
            }
            if ((roomTimePeriod.getLessonDate().isEmpty() || roomTimePeriod.getLessonDate() == null) && (lessonDates != null)){
                for (LessonDate l: lessonDates) {
                    roomTimePeriod.addLessonDate(new LessonDate(l.getSingleDate(),l.getFromDate(),l.getToDate()));
                }
            }
            result.add(roomTimePeriod);
            lessonDates = roomTimePeriod.getLessonDate();
        }

        //TODO: MAKE IT NORM
        for (RoomTimePeriod rtp :result) {
            if (rtp.getLessonDate() == null || rtp.getLessonDate().isEmpty()){
                if (lessonDates != null && !lessonDates.isEmpty())
                {
                    for (LessonDate s : lessonDates) {
                        if (s != null) {
                            rtp.addLessonDate(new LessonDate(s.getSingleDate(), s.getFromDate(), s.getToDate()));
                        }
                    }
                }
                else {
                    rtp.addLessonDate(new LessonDate(true, maxToDate));
                }
            }
        }

        Collections.reverse(a);
        Collections.reverse(result);
//        System.out.println(result);
//        System.out.println("----initial-----");
//        KNUCAUtil.print(a);]
        return new HashSet<>(result);

    }

    private Set<Teacher> parseTeachers(String lessonLine){
        Set<Teacher> result = new HashSet<>();
        String[] tmp = lessonLine.split("]");
        String teacherGroupline = tmp[tmp.length-1];
        Pattern teacherPattern = Pattern.compile("\\b((([^.,\\s\\d\\p{Punct}]{2,5}.)?[^.,\\s\\d\\p{Punct}]{2,4}\\.)|[^.,\\d\\s]{3,}\\.)\\s[^.,\\s\\d]+(\\s[^.,\\d\\s]\\.)?([^.,\\d\\s]\\.?)?");
        Matcher matcher = teacherPattern.matcher(teacherGroupline);
        while (matcher.find()){
            result.add(new Teacher(teacherGroupline.substring(matcher.start(),matcher.end())));
        }
        return result;
    }

    private Set<Group> parseGroups(String lessonLine, String labeledGroup){
        Set<Group> result = new HashSet<>();
        String[] tmp = lessonLine.split("]");
        String teacherGroupline = tmp[tmp.length-1];
        Pattern pattern = Pattern.compile("\\b[А-яІіЇїЄє]{1,6}-(\\S){1,6}\\b");
        Matcher matcher = pattern.matcher(teacherGroupline);
        while (matcher.find()){
            result.add(new Group(teacherGroupline.substring(matcher.start(),matcher.end())));
        }
        result.add(new Group(labeledGroup));
        return result;
    }

    /**
     *
     * @param date format dd.MM
     *             and
     * @return parsed Date
     */
    private Date stringToDate(String date, DayOfWeek dayOfWeek){

        String[] dateMonth = date.split("\\.");
        int day = Integer.parseInt(dateMonth[0]);
        if (dayOfWeek != null){
            day += dayOfWeek.ordinal();
        }
        int month = Integer.parseInt(dateMonth[1]);
        int year = calendar.get(Calendar.YEAR);
        return new GregorianCalendar(year,month - 1,day).getTime();
    }

    private DayOfWeek stringToDayOfWeek(String s){
        Pattern dayOfAWeek = Pattern.compile("" +
                "([^|]Понеділок)|" +
                "([^|]Вівторок)|" +
                "([^|]Середа)|" +
                "([^|]Четвер)|" +
                "([^|]П'ятниця)" +
                "|([^|]Субота)");
        switch (s){
            case "Понеділок":
                return DayOfWeek.MONDAY;
            case "Вівторок":
                return DayOfWeek.TUESDAY;
            case "Середа":
                return DayOfWeek.WEDNESDAY;
            case "Четвер":
                return DayOfWeek.THURSDAY;
            case "П'ятниця":
                return DayOfWeek.FRIDAY;
            case "Субота":
                return DayOfWeek.SATURDAY;
            default:
                return DayOfWeek.SUNDAY;
        }
    }

    public static LessonOrder timeToOrder(String string){
        switch (string){
            case "9:00": return LessonOrder.FIRST;
            case "10:30": return LessonOrder.SECOND;
            case "12:20": return LessonOrder.THIRD;
            case "13:50": return LessonOrder.FOURTH;
            case "15:20": return LessonOrder.FIFTH;
            case "16:50": return LessonOrder.SIXTH;
            case "18:20": return LessonOrder.SEVENTH;
            default: return LessonOrder.OUT_OF_SCHEDULE;
        }
    }

    public static void printMap(Map<String,String> map){
        for (Map.Entry<String, String> pair: map.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }
    }

    public static void print(Collection strings){
        for (Object o :
                strings) {
            System.out.println(o);
        }
    }

    public static int ukrMothToNumber(String month){
        month = month.toLowerCase();
        switch (month){
            case "січня": return 0;
            case "лютого": return 1;
            case "березня": return 2;
            case "квітня": return 3;
            case "травня": return 4;
            case "червня": return 5;
            case "липня": return 6;
            case "серпня": return 7;
            case "вересня": return 8;
            case "жовтня": return 9;
            case "листопада": return 10;
            case "грудня": return 11;
            default:return 0;
        }
    }

}
