package stubs;

import com.theopus.knucaTelegram.entity.schedule.Group;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GroupStubs {

    public static Set<Group> getRand(int number){
        Random random = new Random();
        Set<Group> groupSet = new HashSet<>();
        while (true){
            int next = random.nextInt(10000) + 1;
            if (next % 2 == 0)
                groupSet.add(get2());
            if (next % 3 == 0)
                groupSet.add(get1());
            if (next % 4 == 0)
                groupSet.add(get3());
            if (next % 5 == 0)
                groupSet.add(get4());
            if (next % 6 == 0)
                groupSet.add(get5());
            int a = groupSet.size();
            if (a >= number)
                break;
        }
        return groupSet;
    }

    public static Group get1(){
        return new Group("TestGroup-1");
    }
    public static Group get2(){
        return new Group("TestGroup-2");
    }
    public static Group get3(){
        return new Group("TestGroup-3");
    }
    public static Group get4(){
        return new Group("TestGroup-4");
    }
    public static Group get5(){
        return new Group("TestGroup-5");
    }
}
