package com.theopus.knucaTelegram.parser.ver20;

import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.entity.Lesson;
import com.theopus.knucaTelegram.data.entity.proxy.LessonProxy;
import com.theopus.knucaTelegram.data.entity.proxy.RoomDateProxy;
import javafx.util.Pair;

import java.util.*;

public class QualityControl {
    
    public static LessonProxy[] qualityDuplicateControl(LessonProxy firstL, LessonProxy secondL){
        if (firstL == null || secondL == null) return null;
        if (firstL == secondL) return null;
        if (firstL.getOrder() == null || secondL.getOrder() == null) return null;
        if (firstL.getOrder() != secondL.getOrder()) return null;
        if (firstL.getSubject() == null || secondL.getSubject() == null) return null;
        if (!firstL.getSubject().equals(secondL.getSubject())) return null;
        if (firstL.getLessonType()== null || secondL.getLessonType() == null) return null;
        if (firstL.getLessonType() != secondL.getLessonType()) return null;
        if (firstL.getTeachers()== null || secondL.getTeachers() == null) return null;
        if (!firstL.getTeachers().equals(secondL.getTeachers())) return null;
        if (firstL.getTeachers().size() != secondL.getTeachers().size()) return null;
        if (!secondL.getTeachers().containsAll(firstL.getTeachers())) return null;
        if (!firstL.getTeachers().containsAll(secondL.getTeachers())) return null;
        if (firstL.getOwnerGroup() == TRASH || secondL.getOwnerGroup() == TRASH) return null;


        Set<RoomDateProxy> rdp1 = firstL.getRoomDateProxies();
        Set<RoomDateProxy> rdp2 = secondL.getRoomDateProxies();
        boolean flag = false;
        for (RoomDateProxy rd1 : rdp1) {
            merged: for (RoomDateProxy rd2 : rdp2) {
                if (rd1.getRoom().equals(rd2.getRoom())){
                    for (Date date1 : rd1.getDates()) {
                        for (Date date : rd2.getDates()) {
                            if (date.equals(date1)) {
                                flag = true;
                                continue merged;
                            }
                        }

                    }

                }
            }
        }
        if (flag)
            return lessonProxies(firstL, secondL);
        return null;
    }

    public static Group TRASH = new Group("TRASH");
    public static Group MERGEDSAME = new Group("MERGEDSAME");

    private static LessonProxy[] lessonProxies(LessonProxy one, LessonProxy two){
        Set<RoomDateProxy> rdprox1 = one.getRoomDateProxies();
        Set<RoomDateProxy> rdprox2 = two.getRoomDateProxies();

        Set<RoomDateProxy> merged = new LinkedHashSet<>();
        Set<Group> mergedGroups = new HashSet<Group>();
        mergedGroups.addAll(one.getGroups());
        mergedGroups.addAll(two.getGroups());

        for (RoomDateProxy rdp1 : rdprox1) {
            for (RoomDateProxy rdp2 : rdprox2) {
                if (rdp1.getRoom().equals(rdp2.getRoom())){
                    Set<Date> sameDates = new HashSet<>();
                    for (Iterator<Date> iterator = rdp1.getDates().iterator(); iterator.hasNext(); ) {
                        Date date1 = iterator.next();
                        for (Iterator<Date> iterator1 = rdp2.getDates().iterator(); iterator1.hasNext(); ) {
                            Date date2 = iterator1.next();
                            if (date1.equals(date2)) {
                                sameDates.add(date1);
                                iterator.remove();
                                iterator1.remove();
                            }
                        }
                    }
                    merged.add(new RoomDateProxy(rdp1.getRoom(),sameDates));
                }
            }
        }
        LessonProxy newMerged = new LessonProxy();
        newMerged.setSubject(one.getSubject());
        newMerged.setLessonType(one.getLessonType());
        newMerged.setOrder(one.getOrder());
        newMerged.setRoomDateProxies(merged);
        newMerged.setGroups(mergedGroups);
        newMerged.setTeachers(new HashSet<>(one.getTeachers()));
        newMerged.setOwnerGroup(null);

        rdprox1.removeIf(roomDateProxy -> roomDateProxy.getDates().size() == 0);

        rdprox2.removeIf(roomDateProxy -> roomDateProxy.getDates().size() == 0);


        boolean firstValid = true;
        boolean secondValid = true;
        if (rdprox1.isEmpty()){
            firstValid = false;
            one.setLessonType(null);
            one.setTeachers(null);
            one.setGroups(null);
            one.setSubject(null);
            one.setRoomDateProxies(null);
            one.setOrder(null);
            one.setOwnerGroup(TRASH);
        }
        if (rdprox2.isEmpty()) {
            secondValid = false;
            two.setLessonType(null);
            two.setTeachers(null);
            two.setGroups(null);
            two.setSubject(null);
            two.setRoomDateProxies(null);
            two.setOrder(null);
            two.setOwnerGroup(TRASH);
        }

        if (firstValid && secondValid) {
            return new LessonProxy[]{newMerged, one, two};
        }
        if (firstValid) {
            return new LessonProxy[]{newMerged, one};
        }
        if (secondValid) {
            return new LessonProxy[]{newMerged, two};
        }
        return new LessonProxy[]{newMerged};

    }

    public static LessonProxy mergeSame(LessonProxy firstL, LessonProxy secondL){
        if (firstL == null || secondL == null) return null;
        if (firstL == secondL) return null;
        if (firstL.getOrder() == null || secondL.getOrder() == null) return null;
        if (firstL.getOrder() != secondL.getOrder()) return null;
        if (firstL.getSubject() == null || secondL.getSubject() == null) return null;
        if (!firstL.getSubject().equals(secondL.getSubject())) return null;
        if (firstL.getLessonType()== null || secondL.getLessonType() == null) return null;
        if (firstL.getLessonType() != secondL.getLessonType()) return null;
        if (firstL.getTeachers()== null || secondL.getTeachers() == null) return null;
        if (!firstL.getTeachers().equals(secondL.getTeachers())) return null;
        if (firstL.getTeachers().size() != secondL.getTeachers().size()) return null;
        if (!secondL.getTeachers().containsAll(firstL.getTeachers())) return null;
        if (!firstL.getTeachers().containsAll(secondL.getTeachers())) return null;
        if (firstL.getGroups() == null || secondL.getGroups() == null) return null;
        if (!firstL.getGroups().equals(secondL.getGroups())) return null;
        if (firstL.getGroups().size() != secondL.getGroups().size()) return null;
        if (!secondL.getGroups().containsAll(firstL.getGroups())) return null;
        if (!firstL.getGroups().containsAll(secondL.getGroups())) return null;
        if (firstL.getOwnerGroup() == TRASH || secondL.getOwnerGroup() == TRASH) return null;

        Set<RoomDateProxy> merged = new HashSet<>();
        merged.addAll(firstL.getRoomDateProxies());
        merged.addAll(secondL.getRoomDateProxies());

        firstL.getRoomDateProxies().removeIf(roomDateProxy -> true);
        secondL.getRoomDateProxies().removeIf(roomDateProxy -> true);

        firstL.getRoomDateProxies().forEach(roomDateProxy -> {

        });

        LessonProxy newMerged = new LessonProxy();
        newMerged.setSubject(firstL.getSubject());
        newMerged.setLessonType(firstL.getLessonType());
        newMerged.setOrder(firstL.getOrder());
        newMerged.setRoomDateProxies(merged);
        newMerged.setGroups(new HashSet<>(firstL.getGroups()));
        newMerged.setTeachers(new HashSet<>(firstL.getTeachers()));
        newMerged.setOwnerGroup(MERGEDSAME);

        firstL.setLessonType(null);
        firstL.setTeachers(null);
        firstL.setGroups(null);
        firstL.setSubject(null);
        firstL.setRoomDateProxies(null);
        firstL.setOrder(null);
        firstL.setOwnerGroup(TRASH);

        secondL.setLessonType(null);
        secondL.setTeachers(null);
        secondL.setGroups(null);
        secondL.setSubject(null);
        secondL.setRoomDateProxies(null);
        secondL.setOrder(null);
        secondL.setOwnerGroup(TRASH);

        return newMerged;
    }
}
