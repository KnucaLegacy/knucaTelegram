package com.theopus.knucaTelegram.data.entity.enums;

public enum LessonOrder {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH,
    OUT_OF_SCHEDULE;

    public static String toReadable(LessonOrder lessonOrder){
        switch (lessonOrder){
            case FIRST: return "Первая";
            case SECOND: return "Вторая";
            case THIRD: return "Третья";
            case FOURTH: return "Четвертая";
            case FIFTH: return "Пятая";
            case SIXTH: return "Шестая";
            case SEVENTH: return "Седьмая";
            case OUT_OF_SCHEDULE: return "ХзКакая";
            default: return null;
        }
    }

    public static String toTimeString(LessonOrder lessonOrder){
        switch (lessonOrder){
            case FIRST: return "9:00";
            case SECOND: return "10:30";
            case THIRD: return "12:20";
            case FOURTH: return "13:50";
            case FIFTH: return "15:20";
            case SIXTH: return "16:50";
            case SEVENTH: return "18:20";
            case OUT_OF_SCHEDULE: return "18:20+";
            default: return null;
        }
    }
}

//15:20": return LessonOrder.FIFTH;
//        case "16:50": return LessonOrder.SIXTH;
//        case "18:20":
