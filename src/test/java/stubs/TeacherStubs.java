package stubs;

import com.theopus.knucaTelegram.entity.schedule.Teacher;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TeacherStubs {

    public static Set<Teacher> getRand(int number){
        Random random = new Random();
        Set<Teacher> teacherSet = new HashSet<>();
        while (true){
            int next = random.nextInt(10000) + 1;
            if (next % 15 == 0)
                teacherSet.add(get2());
            if (next % 3 == 0)
                teacherSet.add(get1());
            if (next % 4 == 0)
                teacherSet.add(get3());
            if (next % 5 == 0)
                teacherSet.add(get4());
            if (next % 10 == 0)
                teacherSet.add(get5());
            int a = teacherSet.size();
            if (a >= number)
                break;
        }
        return teacherSet;
    }

    public static Teacher get1(){
        return new Teacher("testTeacher.1");
    }
    public static Teacher get2(){
        return new Teacher("testTeacher.2");
    }
    public static Teacher get3(){
        return new Teacher("testTeacher.3");
    }
    public static Teacher get4(){
        return new Teacher("testTeacher.4");
    }
    public static Teacher get5(){
        return new Teacher("testTeacher.5");
    }
    public static Teacher get6(){
        return new Teacher("testTeacher.6");
    }
}
