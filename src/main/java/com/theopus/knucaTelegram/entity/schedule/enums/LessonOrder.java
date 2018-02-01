package com.theopus.knucaTelegram.entity.schedule.enums;

public enum LessonOrder {
    FIRST,
    SECOND,
    THIRD,
    FOURTH,
    FIFTH,
    SIXTH,
    SEVENTH,
    EIGHTH,
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
            case EIGHTH: return "Восьмая";
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
            case EIGHTH: return "19:50";
            case OUT_OF_SCHEDULE: return "18:20+";
            default: return null;
        }
    }

    public static LessonOrder getById(int id){
        switch (id){
            case 0:return FIRST;
            case 1:return SECOND;
            case 2:return THIRD;
            case 3:return FOURTH;
            case 4:return FIFTH;
            case 5:return SIXTH;
            case 6:return SEVENTH;
            case 7:return EIGHTH;
            default: return OUT_OF_SCHEDULE;
        }
    }
}

