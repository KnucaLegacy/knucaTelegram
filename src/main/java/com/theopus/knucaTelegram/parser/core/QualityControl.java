package com.theopus.knucaTelegram.parser.core;

import com.google.common.collect.Sets;
import com.theopus.knucaTelegram.entity.Group;
import com.theopus.knucaTelegram.parser.objects.entity.LessonProxy;
import com.theopus.knucaTelegram.parser.objects.entity.RoomDateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

class QualityControl {

    private static final Logger log = LoggerFactory.getLogger(QualityControl.class);

    private List<LessonProxy> lessonProxies;

    QualityControl(List<LessonProxy> lessonProxies) {
        this.lessonProxies = lessonProxies;
    }

    Set<LessonProxy> process(){
        int match = 0;
        int perevious = 0;
        log.info("Quality check.");
        log.info(lessonProxies.size() + " lessons to process.");
        for (int i = 0; i < 10; i++) {
            lessonProxies = qualityControl(lessonProxies);
            if (perevious == lessonProxies.size()){
                match++;
            }
            else {
                match = 0;
                perevious = lessonProxies.size();
            }

            if (match > 2)
                break;
            log.info(i + 1 + " iteration - " + lessonProxies.size() + ".");
        }
        log.info("Duplicates check.");
        Set<LessonProxy> res = new HashSet<>(lessonProxies);
        log.info(res.size() + " - result.");
        log.info("Done.");
        return res;
    }

    private Group TRASH = new Group("TRASH");
    private Group MERGEDSAME = new Group("MERGEDSAME");
    private Group MERGEDSAMELESSON = new Group("MERGEDSAMELESSON");

    private List<LessonProxy> merge(LessonProxy one, LessonProxy two){
        if (one == two) return null;


        if (one.getOwnerGroup() == TRASH || one.getOwnerGroup() == TRASH) return null;
        if (one.getGroups().containsAll(two.getGroups()))
            if (two.getGroups().containsAll(one.getGroups())) {
                List<LessonProxy> res = new ArrayList<LessonProxy>();
                res.add(mergeSameGroups(one,two));
                return res;
            }

        Set<RoomDateProxy> roomDateOne = one.getRoomDateProxies();
        Set<RoomDateProxy> roomDateTwo = two.getRoomDateProxies();

        for (RoomDateProxy rdp1 : roomDateOne) {
            for (RoomDateProxy rdp2 : roomDateTwo) {
                if (rdp1.getOrder().equals(rdp2.getOrder()) &&
                        rdp1.getRoom().equals(rdp2.getRoom())){
                    for (Date date : rdp1.getDates()) {
                        for (Date date1 : rdp2.getDates()) {
                            if (date.equals(date1)){
                                return mergeSameLessons(one, two);
                            }
                        }

                    }

                }
            }
        }
        return null;
    }

    private  List<LessonProxy> mergeSameLessons(LessonProxy one, LessonProxy two) {
        Set<RoomDateProxy> roomDateProxies = new HashSet<>();
        for (RoomDateProxy rdp1 : one.getRoomDateProxies()) {
            for (RoomDateProxy rdp2 : two.getRoomDateProxies()) {
                if (rdp1.getOrder().equals(rdp2.getOrder()) &&
                        rdp1.getRoom().equals(rdp2.getRoom())){
                    Sets.SetView<Date> intersection = Sets.intersection(rdp1.getDates(), rdp2.getDates());
                    if (intersection.size() > 0){
                        Set<Date> dates = new HashSet<>();
                        intersection.copyInto(dates);
                        rdp1.getDates().removeAll(dates);
                        rdp2.getDates().removeAll(dates);
                        roomDateProxies.add(new RoomDateProxy(rdp2.getOrder(),rdp2.getRoom(), dates));
                        }
                    }

                }
            }

        one.getRoomDateProxies().removeIf(roomDateProxy -> roomDateProxy.getDates().size() == 0);
        two.getRoomDateProxies().removeIf(roomDateProxy -> roomDateProxy.getDates().size() == 0);

        Set<Group> mergedGroups = new HashSet<>();
        mergedGroups.addAll(one.getGroups());
        mergedGroups.addAll(two.getGroups());

        LessonProxy newMerged = new LessonProxy();
        newMerged.setSubject(one.getSubject());
        newMerged.setLessonType(one.getLessonType());
        newMerged.setRoomDateProxies(roomDateProxies);
        newMerged.setGroups(mergedGroups);
        newMerged.setTeachers(new HashSet<>(one.getTeachers()));
        newMerged.setOwnerGroup(MERGEDSAMELESSON);

        boolean firstValid = true;
        boolean secondValid = true;
        if (one.getRoomDateProxies().isEmpty()){
            firstValid = false;
            one.setOwnerGroup(TRASH);
        }
        if (two.getRoomDateProxies().isEmpty()) {
            secondValid = false;
            two.setOwnerGroup(TRASH);
        }

        List<LessonProxy> result = new ArrayList<>();
        if (firstValid)
            result.add(one);
        if (secondValid)
            result.add(two);
        result.add(newMerged);
        return result;
    }

    private  LessonProxy mergeSameGroups(LessonProxy one, LessonProxy two) {
        Set<RoomDateProxy> merged = new HashSet<>();
        for (RoomDateProxy rdp1 : one.getRoomDateProxies()) {
            for (RoomDateProxy rdp2 : two.getRoomDateProxies()) {
                if (rdp1.getOrder().equals(rdp2.getOrder()) &&
                        rdp1.getRoom().equals(rdp2.getRoom())){
                    Set<Date> dates = new HashSet<>();
                    dates.addAll(rdp1.getDates());
                    dates.addAll(rdp2.getDates());
                    rdp1.getDates().removeAll(dates);
                    rdp2.getDates().removeAll(dates);
                    merged.add(new RoomDateProxy(rdp1.getOrder(),rdp1.getRoom(), dates));
                }
            }
        }

        one.getRoomDateProxies().forEach(roomDateProxy -> {
            if(!roomDateProxy.getDates().isEmpty()){
                merged.add(roomDateProxy);
            }
        });
        two.getRoomDateProxies().forEach(roomDateProxy -> {
            if(!roomDateProxy.getDates().isEmpty()){
                merged.add(roomDateProxy);
            }
        });
        one.setOwnerGroup(TRASH);
        two.setOwnerGroup(TRASH);

        LessonProxy newMerged = new LessonProxy();
        newMerged.setSubject(one.getSubject());
        newMerged.setLessonType(one.getLessonType());
        newMerged.setRoomDateProxies(merged);
        newMerged.setGroups(new HashSet<>(one.getGroups()));
        newMerged.setTeachers(new HashSet<>(one.getTeachers()));
        newMerged.setOwnerGroup(MERGEDSAME);

        return newMerged;
    }

    private boolean isSame(LessonProxy thiz, LessonProxy that){
        if (thiz.getSubject() != null ? !thiz.getSubject().equals(that.getSubject()) : that.getSubject() != null) return false;
        if (thiz.getLessonType() != that.getLessonType()) return false;
        if (!thiz.getTeachers().containsAll(that.getTeachers())) return false;
        if (!that.getTeachers().containsAll(thiz.getTeachers())) return false;
        return true;
    }
    private List<LessonProxy> qualityControl(List<LessonProxy> lessons){
        List<LessonProxy> result = new ArrayList<>();
        List<LessonProxy> buffer;
        for (LessonProxy proxyInit :lessons) {
            if (proxyInit.getOwnerGroup() == TRASH)
                continue;
            buffer = new ArrayList<>();
            for (LessonProxy proxyRes : result) {
                if (proxyRes.getOwnerGroup() == TRASH)
                    continue;
                if (isSame(proxyInit,proxyRes)){
                    List<LessonProxy> merge = merge(proxyInit, proxyRes);
                    if (merge == null)
                        continue;
                    else {
                        buffer.addAll(merge);
                        break;
                    }
                }
            }
            if (proxyInit.getOwnerGroup() != TRASH)
                result.add(proxyInit);
            result.addAll(buffer);
        }
        result.removeIf(lessonProxy -> lessonProxy.getOwnerGroup() == TRASH);
        return result;
    }
    private Set<LessonProxy> trash = new HashSet<>();
}
