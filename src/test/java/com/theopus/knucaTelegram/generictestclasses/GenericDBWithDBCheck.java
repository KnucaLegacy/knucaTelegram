package com.theopus.knucaTelegram.generictestclasses;

import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.service.data.GroupService;
import com.theopus.knucaTelegram.service.data.LessonService;
import com.theopus.knucaTelegram.service.data.TeacherService;
import com.theopus.knucaTelegram.parser.core.MainParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestPersistenceConfig.class)
public class GenericDBWithDBCheck {


    @Resource
    protected LessonService lessonService;
    @Resource
    protected TeacherService teacherService;
    @Resource
    protected GroupService groupService;

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void name() throws Exception {
    }
}
