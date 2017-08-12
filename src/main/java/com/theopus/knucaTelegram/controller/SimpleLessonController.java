package com.theopus.knucaTelegram.controller;

import com.theopus.knucaTelegram.controller.exceptions.ex404.GroupNotFoundException;
import com.theopus.knucaTelegram.controller.exceptions.ex404.SimpleLessonNotFoundException;
import com.theopus.knucaTelegram.controller.exceptions.utill.BadDateFormatException;
import com.theopus.knucaTelegram.controller.resources.DayLessonsResource;
import com.theopus.knucaTelegram.controller.resources.SimpleLessonResource;
import com.theopus.knucaTelegram.controller.resources.SimpleLessonResourceFull;
import com.theopus.knucaTelegram.entity.schedule.*;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import com.theopus.knucaTelegram.entity.schedule.enums.LessonOrder;
import com.theopus.knucaTelegram.service.data.GroupService;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import com.theopus.knucaTelegram.service.data.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/api/slessons")
@EnableHypermediaSupport(type = { EnableHypermediaSupport.HypermediaType.HAL })
public class SimpleLessonController {

    @Resource
    private SimpleLessonService simpleLessonService;
    @Resource
    private GroupService groupService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private RepositoryRestMvcConfiguration configuration;
    @Autowired
    private DateFormat format;

    @RequestMapping(value = "/{lessonId}/{dateLine}/{lessonOrder}")
    public @ResponseBody ResponseEntity<?> getOne(@PathVariable long lessonId,
                          @PathVariable String dateLine,
                          @PathVariable int lessonOrder) {
        SimpleLesson lesson = null;
        Date date = parseDate(dateLine);
        List<SimpleLesson> byId = simpleLessonService.getById(date, lessonId);
        if (byId == null)
            throw new SimpleLessonNotFoundException(lessonId, format.format(date));
        for (SimpleLesson simpleLesson : byId) {
            if (simpleLesson.getOrder().ordinal() == lessonOrder)
                lesson = simpleLesson;
        }
        if (lesson == null)
            throw new SimpleLessonNotFoundException(lessonId, format.format(date), LessonOrder.getById(lessonOrder));
        return new ResponseEntity<>(this.getResourceFull(lesson), HttpStatus.OK);
    }
    @RequestMapping("/day/{lessonId}/{dateLine}")
    public @ResponseBody ResponseEntity<?> getDay(@PathVariable long lessonId,
                                                  @PathVariable String dateLine){
        Date date = parseDate(dateLine);
        List<SimpleLesson> lessons = simpleLessonService.getById(date, lessonId);
        return new ResponseEntity<>(this.getDayResource(date, lessons),HttpStatus.OK);
    }

    @RequestMapping(value = "/day/byGroup/{groupId}/{dateLine}")
    public @ResponseBody ResponseEntity<?> dayGroup(@PathVariable long groupId,
                                                   @PathVariable String dateLine){
        Date date = parseDate(dateLine);
        Group group = validateGroup(groupId);
        List<SimpleLesson> lessons = simpleLessonService.getByGroup(date,group);
        return new ResponseEntity<>(this.getDayResource(date, lessons),HttpStatus.OK);
    }
    @RequestMapping(value = "/day/byTeacher/{teacherId}/{dateLine}")
    public @ResponseBody ResponseEntity<?> dayTeacher(@PathVariable long teacherId,
                                                   @PathVariable String dateLine){
        Date date = parseDate(dateLine);
        Teacher teacher = validateTeacher(teacherId);
        List<SimpleLesson> lessons = simpleLessonService.getByTeacher(date,teacher);
        return new ResponseEntity<>(this.getDayResource(date, lessons),HttpStatus.OK);
    }

    private Group validateGroup(long id){
        Group byId = groupService.getById(id);
        if (byId == null)
            throw new GroupNotFoundException(id);
        return byId;
    }
    private Teacher validateTeacher(long id){
        Teacher byId = teacherService.getById(id);
        if (byId == null)
            throw new GroupNotFoundException(id);
        return byId;
    }
    private Date parseDate(String dateLine) throws BadDateFormatException {
        Date date = null;
        if(dateLine.length() > 10)
            try {
                date = new Date(Long.parseLong(dateLine));
            }
            catch (NumberFormatException e){
                throw new BadDateFormatException(dateLine, "dd-MM-yyyy");
            }
        else
            try {
                date = format.parse(dateLine);
            } catch (ParseException e) {
                throw new BadDateFormatException(dateLine, "dd-MM-yyyy");
            }
        date = DayOfWeek.dateToRawDate(date);
        return date;
    }
    private Resources<Object> getEmpty(){
        EmbeddedWrappers wrappers = new EmbeddedWrappers(false);
        EmbeddedWrapper wrapper = wrappers.emptyCollectionOf(SimpleLessonResource.class);
        Resources<Object> resources = new Resources<>(Arrays.asList(wrapper));
        return resources;
    }


    private SimpleLessonResourceFull getResourceFull(SimpleLesson simpleLesson){
        SimpleLessonResourceFull slResource = new SimpleLessonResourceFull(simpleLesson);
        slResource.add(linkTo(SimpleLessonController.class).slash(simpleLesson.getFullid()).slash(simpleLesson.getDate().getTime()).slash(simpleLesson.getOrder().ordinal()).withSelfRel());
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("subject").withRel("subject"));
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("groups").withRel("groups"));
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("teachers").withRel("teachers"));
        for (Room room : simpleLesson.getRooms()) {
            slResource.add(configuration.entityLinks().linkToSingleResource(Room.class, room.getId()).withRel("rooms"));
        }
        return slResource;
    }
    private SimpleLessonResource getResource(SimpleLesson simpleLesson){
        SimpleLessonResource slResource = new SimpleLessonResource(simpleLesson);
        slResource.add(linkTo(SimpleLessonController.class).slash(simpleLesson.getFullid()).slash(simpleLesson.getDate().getTime()).slash(simpleLesson.getOrder().ordinal()).withSelfRel());
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("subject").withRel("subject"));
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("groups").withRel("groups"));
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("teachers").withRel("teachers"));
        for (Room room : simpleLesson.getRooms()) {
            slResource.add(configuration.entityLinks().linkToSingleResource(Room.class, room.getId()).withRel("rooms"));
        }
        return slResource;
    }
    private DayLessonsResource getDayResource(Date date, Collection<SimpleLesson> lessons){
        List<SimpleLessonResource> resources = new ArrayList<>();
        if (lessons == null || lessons.isEmpty())
            resources = new ArrayList<>();
        else {
            List<SimpleLessonResource> resourceList = new ArrayList<>();
            lessons.forEach(simpleLesson -> resourceList.add(getResourceFull(simpleLesson)));
            resources = resourceList;
        }
        return new DayLessonsResource(date, DayOfWeek.dateToDayOfWeek(date),resources);
    }
    private Resources<?> getDayResource(Map<Date, List<SimpleLesson>> lessonsMap){
        List<DayLessonsResource> dayLessonsResources = new ArrayList<>();
        lessonsMap.forEach((date, simpleLessons) -> {
            dayLessonsResources.add(getDayResource(date,simpleLessons));
        });
        return new Resources<>(dayLessonsResources);
    }

}
