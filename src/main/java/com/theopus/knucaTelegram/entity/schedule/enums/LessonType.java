package com.theopus.knucaTelegram.entity.schedule.enums;

public enum LessonType {
    LECTION,
    LAB,
    PRACTICE,
    ELSE;

    @Override
    public String toString() {
        switch (this.name()){
            case "LECTION": return "Лекция" ;
            case "LAB": return "Лабораторная" ;
            case "PRACTICE": return "Практика" ;
            case "ELSE": return "Что-то там" ;
            default: return null;
        }
    }
}
