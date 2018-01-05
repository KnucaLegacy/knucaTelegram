package stubs;

import com.theopus.knucaTelegram.entity.schedule.Circumstance;
import com.theopus.knucaTelegram.entity.schedule.Room;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CircumstanceStubs {



    public static Set<Circumstance> getCircumstances(Date initialDate,
                                                            int order,
                                                            int numberOfDates,
                                                            int countOjCircumstances,
                                                            boolean containsSameRooms){
        Random random = new Random();
        Set<Circumstance> circumstances = new HashSet<>();

        for (int i = 0; i < countOjCircumstances; i++) {
            Circumstance circumstance = new Circumstance();
            Set<Date> dates = new HashSet<>();
            Date date;
            if (containsSameRooms)
                date = initialDate;
            else
                date = DayOfWeek.dayDateOffset(DayOfWeek.dateToRawDate(initialDate), i * (random.nextInt(4) + 1));

            for (int j = 0; j < numberOfDates; j++) {
                dates.add(DayOfWeek.weekDateOffset(date, random.nextBoolean() ? j : j *(-1)));
            }
            circumstance.setOrder(LessonOrder.getById(order));
            circumstance.setDates(dates);
            if (containsSameRooms){
                circumstance.setRoom(RoomStubs.getRand(1));
                circumstances.add(circumstance);
                while (circumstances.size() < countOjCircumstances){
                    Circumstance tmp = clone(circumstance);
                    Room room = RoomStubs.getRand(1);
                    tmp.setRoom(room);
                    circumstances.add(tmp);
                }
                return circumstances;
            }
            else {
                circumstance.setRoom(RoomStubs.getRand(1));
            }
            circumstances.add(circumstance);
        }
        return circumstances;
    }

    private static Circumstance clone(Circumstance circumstance){
        Circumstance result = new Circumstance();
        result.setRoom(circumstance.getRoom());
        result.setDates(new HashSet<>(circumstance.getDates()));
        result.setLesson(circumstance.getLesson());
        result.setOrder(circumstance.getOrder());
        return result;
    }


    public static Circumstance get1(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.FIRST);
        circumstance.setRoom(RoomStubs.get1());
        return circumstance;
    }
    public static Circumstance get2(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.SECOND);
        circumstance.setRoom(RoomStubs.get3());
        return circumstance;
    }
    public static Circumstance get3(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.THIRD);
        circumstance.setRoom(RoomStubs.get1());
        return circumstance;
    }
    public static Circumstance get4(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.FOURTH);
        circumstance.setRoom(RoomStubs.get4());
        return circumstance;
    }
    public static Circumstance get5(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.FIRST);
        circumstance.setRoom(RoomStubs.get2());
        return circumstance;
    }
    public static Circumstance get6(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.FIFTH);
        circumstance.setRoom(RoomStubs.get1());
        return circumstance;
    }
    public static Circumstance get7(){
        Circumstance circumstance = new Circumstance();
        circumstance.setOrder(LessonOrder.SIXTH);
        circumstance.setRoom(RoomStubs.get4());
        return circumstance;
    }
}
