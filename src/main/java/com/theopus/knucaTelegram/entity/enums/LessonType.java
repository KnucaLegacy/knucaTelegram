package com.theopus.knucaTelegram.entity.enums;

public enum LessonType {
    LECTION,
    LAB,
    PRACT,
    ELSE;

    @Override
    public String toString() {
        switch (this.name()){
            case "LECTION": return "Лекция" ;
            case "LAB": return "Лабораторная" ;
            case "PRACT": return "Практика" ;
            case "ELSE": return "Что-то там" ;
            default: return null;
        }
    }
}
