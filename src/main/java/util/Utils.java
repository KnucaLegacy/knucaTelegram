package util;

import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;

/**
 * Created by irina on 04.07.17.
 */
public class Utils {

    public static DayOfWeek intToDay(int i){
        switch (i){
            case 0: return DayOfWeek.MONDAY;
            case 1: return DayOfWeek.TUESDAY;
            case 2: return DayOfWeek.WEDNESDAY;
            case 3: return DayOfWeek.THURSDAY;
            case 4: return DayOfWeek.FRIDAY;
            case 5: return DayOfWeek.SATURDAY;
            default: return DayOfWeek.SUNDAY;
        }
    }
}
