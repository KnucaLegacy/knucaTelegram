package com.theopus.knucaTelegram;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.service.data.repository.LessonRepository;
import com.theopus.knucaTelegram.service.data.LessonService;
import com.theopus.knucaTelegram.service.data.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
