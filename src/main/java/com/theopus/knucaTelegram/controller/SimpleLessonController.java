package com.theopus.knucaTelegram.controller;

import com.theopus.knucaTelegram.controller.resources.SLResource;
import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.Lesson;
import com.theopus.knucaTelegram.entity.schedule.Room;
import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.service.data.GroupService;
import com.theopus.knucaTelegram.service.data.SimpleLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Controller
@RequestMapping("/slesson")
@EnableHypermediaSupport(type = { EnableHypermediaSupport.HypermediaType.HAL })
public class SimpleLessonController {


    @Resource
    private SimpleLessonService simpleLessonService;
    @Resource
    private GroupService groupService;

    @Autowired
    private RepositoryRestMvcConfiguration configuration;

    @RequestMapping(value = "/single/byGroup/{groupId}/{dateLong}/{lessonOrder}")
    public @ResponseBody SLResource singleByGroup(@PathVariable long groupId,
                                                             @PathVariable long dateLong,
                                                             @PathVariable long lessonOrder){
        SimpleLesson lesson = null;
        Group group = groupService.getById(groupId);
        Date date = new Date(dateLong);
        for (SimpleLesson simpleLesson : simpleLessonService.getByGroup(date, group)) {
            if (simpleLesson.getOrder().ordinal() == lessonOrder)
                lesson = simpleLesson;
        }
        return getResource(lesson, group);
    }
    @RequestMapping(value = "/day/byGroup{groupId}/{date}")
    public @ResponseBody List<SLResource> dayGroup(){
        List<SLResource> response = new ArrayList<>();
        simpleLessonService.getByGroup(new GregorianCalendar(2017, 5-1, 17).getTime(), groupService.getById(3)).forEach(simpleLesson -> {

        });
        return  null;
    }

    @RequestMapping(value = "/weekByGroup/{groupId}/{date}")
    public @ResponseBody Map<Date, List<SLResource>> week(){
        return null;
    }

    public SLResource getResource(SimpleLesson simpleLesson, Group owner){
        SLResource slResource = new SLResource(simpleLesson);
        slResource.add(linkTo(SimpleLessonController.class).slash(owner).slash(simpleLesson.getDate().getTime()).slash(simpleLesson.getOrder().ordinal()).withSelfRel());
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("subject").withRel("subject"));
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("groups").withRel("groups"));
        slResource.add(configuration.entityLinks().linkForSingleResource(Lesson.class, simpleLesson.getFullid()).slash("teachers").withRel("teachers"));
        for (Room room : simpleLesson.getRooms()) {
            slResource.add(configuration.entityLinks().linkToSingleResource(Room.class, room.getId()).withRel("rooms"));
        }
        return slResource;
    }

}
