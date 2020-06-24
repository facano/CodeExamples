package com.exercise.api.data.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Teacher {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany
    private List<Course> courses;

    public Teacher(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Teacher() {
        courses = Collections.emptyList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course){
        courses.add(course);
    }
}
