package com.theopus.knucaTelegram.service.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TeacherServiceImpl.class)
public class TeacherServiceImplTest {

    @Autowired
    private TeacherService teacherService;

    @Test
    public void getAllTeachers() throws Exception {

    }
}