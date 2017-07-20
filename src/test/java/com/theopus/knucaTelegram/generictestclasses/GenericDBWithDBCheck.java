package com.theopus.knucaTelegram.generictestclasses;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Teacher;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.data.service.LessonService;
import com.theopus.knucaTelegram.data.service.TeacherService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
}
