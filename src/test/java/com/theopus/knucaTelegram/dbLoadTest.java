package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.repository.LessonRepository;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import com.theopus.knucaTelegram.parser.FolderParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@PropertySource(value = "../../../../resources/presistence-mysql-test.properties")
public class dbLoadTest {

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private LessonService lessonService;
    @Resource
    private TeacherService teacherServicel;
    @Resource
    private LessonRepository lessonRepository;


    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void load() throws Exception {
        lessonService.saveAll(new FolderParser().parseFolder("pdfstest"));
//        teacherServicel.deleteById(1);
    }

    @Test
    public void deleteTeacherTest() throws Exception {
        Teacher t = teacherServicel.getById(4);
        for (Lesson l : t.getLessons()) {
            System.out.println(l.getId());
            l.removeTeacher(t);
            lessonService.saveOne(l);
        }
        teacherServicel.deleteById(4);
        System.out.println(lessonRepository.getAllByTeacherId(t.getId()));

    }

    @Test
    public void deleteGroupTest() throws Exception {
    }

    @Test
    public void deleteLesson() throws Exception {

    }

    @Test
    public void testGetAllLessons() throws Exception {

        lessonService.getAll().forEach(System.out::println);
    }

    @Test
    public void name() throws Exception {

    }
}
