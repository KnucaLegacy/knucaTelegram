package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.entity.enums.DayOfWeek;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@WebAppConfiguration
public class LessonServiceImplTest {

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Resource
    private LessonService lessonServiceService;

    @Resource
    private TeacherService teacherService;

    @Resource
    private GroupServiceImpl groupService;

    private Date dateMock = new GregorianCalendar(2017, 5 - 1, 5).getTime();

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }


    @Test
    public void tofromtest() throws Exception {
        long oneDay = 1 * 1000 * 3600 * 24;
        Group group = groupService.getByExactName("ІУСТ-31");
        lessonServiceService.getByGroup(dateMock, new Date(dateMock.getTime() + 5 * oneDay), group)
                .forEach((date, lessonList) -> {
                    System.out.println("-------");
                    System.out.println(date);
                    lessonList.forEach(System.out::println);
                });
    }
}