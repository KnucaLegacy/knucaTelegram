package stubs;

import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Room;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoomStubs {

    public static Room getRand(int number){
        Random random = new Random();
        Set<Room> hashSet = new HashSet<>();
        while (true){
            int next = random.nextInt(9) + 1;
            if (next % 2 == 0)
                return get3();
            if (next % 3 == 0)
                return get6();
            if (next % 4 == 0)
                return get2();
            if (next % 5 == 0)
                return get4();
            if (next % 6 == 0)
                return get3();
            if (next % 7 == 0)
                return get1();
            if (next % 8 == 0)
                return get5();
            if (next % 9 == 0)
                return get7();
        }
    }

    public static Room get1(){
        return new Room("room_1");
    }
    public static Room get2(){
        return new Room("room_2");
    }
    public static Room get3(){
        return new Room("room_3");
    }
    public static Room get4(){
        return new Room("room_4");
    }
    public static Room get5(){
        return new Room("room_5");
    }
    public static Room get6(){
        return new Room("room_6");
    }
    public static Room get7(){
        return new Room("room_7");
    }
}
