package com.theopus.knucaTelegram.bot.botanalytics;

import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;

public enum ActionType {
    NOT_FOUND_ACTION,
    TODAY_GROUP,
    TODAY_TEACHER,
    SINGLE_GROUP,
    SINGLE_TEACHER,
    FEW_GROUP,
    FEW_TEACHER,
    CALLBACK_TODAY_GROUP,
    CALLBACK_TODAY_TEACHER,
    CALLBACK_SINGLE_GROUP,
    CALLBACK_SINGLE_TEACHER,
    CALLBACK_FEW_GROUP,
    CALLBACK_FEW_TEACHER
}
