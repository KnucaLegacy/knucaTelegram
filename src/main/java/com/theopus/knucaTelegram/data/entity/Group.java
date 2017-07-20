package com.theopus.knucaTelegram.data.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_p")
public class Group {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;


    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<Lesson> lessons = new HashSet<>();

    public Group() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Group(String name) {
        this.name = name;
    }

    public boolean addLesson(Lesson lesson){
        return lessons.add(lesson);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        return name != null ? name.equals(group.name) : group.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                '}';
    }
}
