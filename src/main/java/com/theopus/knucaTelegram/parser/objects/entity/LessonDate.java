package com.theopus.knucaTelegram.parser.objects.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class LessonDate {


    private long id;

    private Date singleDate;

    private Date fromDate;


    private Date toDate;

    private RoomTimePeriod roomTimePeriod;

    private boolean in_a_week = false;

    public void setIn_a_week(boolean in_a_week) {
        this.in_a_week = in_a_week;
    }

    public boolean isIn_a_week() {
        return in_a_week;
    }

    public LessonDate() {
    }

    public LessonDate(Date singleDate) {
        this.singleDate = singleDate;
    }

    public LessonDate(boolean today, Date toDate) {
        this.toDate = toDate;
    }
    public LessonDate(Date fromDate, boolean fromday) {
        this.fromDate = fromDate;
    }

    public LessonDate(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public LessonDate(Date singleDate, Date fromDate, Date toDate) {
        this.singleDate = singleDate;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public RoomTimePeriod getRoomTimePeriod() {
        return roomTimePeriod;
    }

    public void setRoomTimePeriod(RoomTimePeriod roomTimePeriod) {
        this.roomTimePeriod = roomTimePeriod;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getSingleDate() {
        return singleDate;
    }

    public void setSingleDate(Date singleDate) {
        this.singleDate = singleDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessonDate that = (LessonDate) o;

        if (singleDate != null ? !singleDate.equals(that.singleDate) : that.singleDate != null) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        return toDate != null ? toDate.equals(that.toDate) : that.toDate == null;
    }

    @Override
    public int hashCode() {
        int result = singleDate != null ? singleDate.hashCode() : 0;
        result = 31 * result + (fromDate != null ? fromDate.hashCode() : 0);
        result = 31 * result + (toDate != null ? toDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("LessonDate{");
        if (singleDate != null)
            result.append(" singleDate=").append(singleDate).append(";");
        if (fromDate != null)
            result.append(" fromDate=").append(fromDate).append(";");
        if (toDate != null)
            result.append(" toDate=").append(toDate).append(";");
        result.append("}");
        return result.toString();
    }

    public boolean isAtDate(Date date) {
        if (fromDate == null && singleDate == null && toDate == null)
            return false;
        if (singleDate != null){

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            Date tmp = new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DAY_OF_MONTH)).getTime();

            if ((tmp.getTime() >= singleDate.getTime()) && (tmp.getTime() <= (singleDate.getTime() + 86400000)))
                return true;
        }
        if (fromDate != null && toDate != null){
            if ((date.getTime() >= fromDate.getTime() && (date.getTime() <= toDate.getTime())))
                return true;
        }
        if (fromDate == null && toDate != null){
            if (date.getTime() <= toDate.getTime())
                return true;
        }
        if (fromDate != null && toDate == null){
            if (fromDate.getTime() <= date.getTime())
                return true;
        }
        return false;
    }
}
