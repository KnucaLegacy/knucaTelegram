package refactor.newentities;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Room;
import com.theopus.knucaTelegram.data.entity.Subject;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.LessonOrder;
import com.theopus.knucaTelegram.data.entity.enums.LessonType;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Lesson {
    private long id;

    private LessonOrder order;

    private Subject subject;

    private LessonType lessonType;

    private Map<Room, Set<Date>> roomDateMap = new LinkedHashMap<>();

    private Set<Teacher> teachers = new HashSet<>();

    private Set<Group> groups = new HashSet<>();
}
