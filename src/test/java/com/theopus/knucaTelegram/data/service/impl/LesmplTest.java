package com.theopus.knucaTelegram.data.service.impl;

import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.entity.Lesson;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import com.theopus.knucaTelegram.service.LessonSimplifier;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;


public class LesmplTest extends GenericDBWithDBCheck {

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Resource
    private LessonSimplifier lessonSimplifier;


    @Test
    public void GetByGroup() throws Exception {
        Group group = groupService.getById(1);
        Date date = new GregorianCalendar(2017, 4 - 1, 20).getTime();
        Date date2 = new GregorianCalendar(2017, 4 - 1, 23).getTime();
        Set<Date> dateSet = new HashSet<>();
        dateSet.add(date);
        dateSet.add(date2);

        Map<Date, List<Lesson>> byGroup = lessonService.getByGroup(date,date2,group);

        System.out.println(byGroup.size());

        lessonSimplifier.simplify(byGroup).forEach((date1, simpleLessons) ->{
            System.out.println(date1);
            simpleLessons.forEach(System.out::println);
        } );

    }
}