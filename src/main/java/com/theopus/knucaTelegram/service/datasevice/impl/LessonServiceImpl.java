package com.theopus.knucaTelegram.service.datasevice.impl;

import com.theopus.knucaTelegram.entity.*;
import com.theopus.knucaTelegram.repository.*;
import com.theopus.knucaTelegram.service.datasevice.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;
    @Qualifier("groupRepository")

    @Autowired
    GroupRepository groupRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public Lesson addLesson(Lesson lesson) {
        System.out.println(lesson);
        Set<Group> groupSet = new HashSet<>();
        for (Group g : lesson.getGroups()) {
            Group findG = groupRepository.findByName(g.getName());
            if (findG != null) {
                groupSet.add(findG);
            }
            else
                groupSet.add(groupRepository.save(g));
            g.addLesson(lesson);
        }
        lesson.setGroups(groupSet);

        Set<Teacher> teachersSet = new HashSet<>();
        for (Teacher t: lesson.getTeachers()) {
            Teacher findT = teacherRepository.findByName(t.getName());
            if (findT != null)
                teachersSet.add(findT);
            else
                teachersSet.add(teacherRepository.save(t));
            t.addLesson(lesson);
        }
        lesson.setTeachers(teachersSet);

        for (RoomTimePeriod rtp: lesson.getRoomTimePeriod()) {
            Room r = rtp.getRoom();
            Room findR = roomRepository.findByName(rtp.getRoom().getName());
            if (findR != null)
                rtp.setRoom(findR);
            else
                r = roomRepository.save(rtp.getRoom());

            if (rtp.getLessonDate() != null) {
                for (LessonDate ld : rtp.getLessonDate()) {
                    ld.setRoomTimePeriod(rtp);

                }
            }
            rtp.setLesson(lesson);
        }
        Subject findS = subjectRepository.findByName(lesson.getSubject().getName());
        if (findS != null)
            lesson.setSubject(findS);
        else subjectRepository.save(lesson.getSubject());
        System.out.println(lesson);

        for (Group g :
                lesson.getGroups()) {
            System.out.println(g.getId());
        }
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Lesson getByName(String name) {
        return null;
    }

    @Override
    public Lesson editBank(Lesson bank) {
        return null;
    }

    @Override
    public List<Lesson> getAll() {
        return lessonRepository.findAll();
    }
}
