package com.theopus.knucaTelegram.controller.exceptions.ex404;

import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;


public class SimpleLessonNotFoundException extends NotFoundException {

    public SimpleLessonNotFoundException(long id, String date, LessonOrder order) {
        super("Not found lesson with id = " + id + ", at " + date + ", which ordered as " + order + "(" + order.ordinal() +").");
    }

    public SimpleLessonNotFoundException(long id, String date) {
        super("Not found lesson with id = " + id + ", at " + date + ".");
    }
}
