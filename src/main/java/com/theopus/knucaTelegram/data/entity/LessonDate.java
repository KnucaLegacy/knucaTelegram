package com.theopus.knucaTelegram.data.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Entity
@Table(name = "lessonDate")
public class LessonDate {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @Column
    private Date singleDate;

    @Column
    private Date fromDate;

    @Column
    private Date toDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private RoomTimePeriod roomTimePeriod;

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
            System.out.println("testing - " + date) ;
            System.out.println("from " + fromDate + " - to " + toDate);
            if ((date.getTime() >= fromDate.getTime() && (date.getTime() <= toDate.getTime())))
                return true;
        }
        if (fromDate == null && toDate != null){
            System.out.println(date);
            System.out.println(toDate);
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
