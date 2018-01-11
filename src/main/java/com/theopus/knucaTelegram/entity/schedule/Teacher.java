package com.theopus.knucaTelegram.entity.schedule;

public class Teacher {

    private Long id;

    private String name;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
//    private Set<Lesson> lessons = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Lesson> getLessons() {
//        return lessons;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        return name != null ? name.equals(teacher.name) : teacher.name == null;
    }

    public long getId() {
        return id;
    }

//    public boolean addLesson(Lesson lesson){
//        return lessons.add(lesson);
//    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }
}
