package com.theopus.knucaTelegram.entity.schedule.enums;

public enum LessonType {
    LECTURE,
    PRACTICE,
    LAB,
    EXAM,
    INDIVIDUAL,
    FACULTY, CONSULTATION,
    NONE, UNIDENTIFIED;

    @Override
    public String toString() {
        switch (this.name()){
            case "LECTION": return "Лекция" ;
            case "LAB": return "Лабораторная" ;
            case "PRACTICE": return "Практика" ;
            case "ELSE": return "Что-то там" ;
            default: return this.name();
        }
    }
}
