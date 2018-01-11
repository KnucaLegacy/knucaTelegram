package com.theopus.knucaTelegram.service.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.theopus.knucaTelegram.entity.schedule.Group;
import com.theopus.knucaTelegram.entity.schedule.NewLesson;
import com.theopus.knucaTelegram.entity.schedule.SimpleLesson;
import com.theopus.knucaTelegram.entity.schedule.Teacher;
import com.theopus.knucaTelegram.entity.schedule.enums.DayOfWeek;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SimpleLessonServiceRest implements SimpleLessonService {

    private RestTemplate restTemplate;
    private static final SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");

    public SimpleLessonServiceRest() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
        restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter());
    }

    @Override
    public List<SimpleLesson> getById(Date date, long id) {
        return null;
    }

    @Override
    public List<SimpleLesson> getByGroup(Date date, Group group) {
        String dates = simp.format(date);
        NewLesson[] forObject = restTemplate.getForObject("http://localhost:8080/lessons/" + dates + "/group/" + group.getId(), NewLesson[].class);
        return Arrays.stream(forObject).map(this::newToSimple).collect(Collectors.toList());
    }

    @Override
    public List<SimpleLesson> getByTeacher(Date date, Teacher teacher) {
        String dates = simp.format(date);
        NewLesson[] forObject = restTemplate.getForObject("http://localhost:8080/lessons/" + dates + "/teacher/" + teacher.getId(), NewLesson[].class);
        return Arrays.stream(forObject).map(this::newToSimple).collect(Collectors.toList());
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByGroup(Set<Date> dates, Group group) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByTeacher(Set<Date> date, Teacher teacher) {
        return null;
    }

    @Override
    public Map<Date, List<SimpleLesson>> getWeekByGroup(Date date, Group group) {
        return week(getLocalDate(date)).stream().map(this::getDate).flatMap(date1 -> getByGroup(date, group).stream())
                .collect(Collectors.groupingBy(SimpleLesson::getDate));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getWeekByTeacher(Date date, Teacher teacher) {
        return week(getLocalDate(date)).stream().map(this::getDate).flatMap(date1 -> getByTeacher(date, teacher).stream())
                .collect(Collectors.groupingBy(SimpleLesson::getDate));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByGroup(Date from, Date to, Group group) {
        return range(getLocalDate(from), getLocalDate(to)).flatMap(localDate -> getByGroup(getDate(localDate), group).stream())
                .collect(Collectors.groupingBy(SimpleLesson::getDate));
    }

    @Override
    public Map<Date, List<SimpleLesson>> getByTeacher(Date from, Date to, Teacher teacher) {
        return range(getLocalDate(from), getLocalDate(to)).flatMap(localDate -> getByTeacher(getDate(localDate), teacher).stream())
                .collect(Collectors.groupingBy(SimpleLesson::getDate));
    }

    private SimpleLesson newToSimple(NewLesson lesson) {
        SimpleLesson lesson1 = new SimpleLesson();
        lesson1.setDate(getDate(lesson.getDate()));
        lesson1.setDayOfWeek(DayOfWeek.intToDay(lesson.getDate().getDayOfWeek().ordinal() - 1));
        lesson1.setGroups(lesson.getGroups());
        lesson1.setOrder(lesson.getOrder());
        lesson1.setSubject(lesson.getCourse().getSubject());
        lesson1.setTeachers(lesson.getCourse().getTeachers());
        lesson1.setLessonType(lesson.getCourse().getType());
        return lesson1;
    }

    private Date getDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return LocalDate.parse(jsonParser.getText());
            }
        });
        objectMapper.registerModule(module);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    private static Set<LocalDate> week(LocalDate localDate) {
        return Arrays.stream(java.time.DayOfWeek.values()).map(localDate::with)
                .collect(Collectors.toSet());
    }

    private static Stream<LocalDate> range(LocalDate startDate, LocalDate endDate) {
        return Stream.iterate(startDate, d -> d.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }

}
