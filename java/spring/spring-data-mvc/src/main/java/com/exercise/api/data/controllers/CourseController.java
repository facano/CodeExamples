package com.exercise.api.data.controllers;

import com.exercise.api.data.models.Course;;
import com.exercise.api.data.services.CourseService;
import com.exercise.api.data.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers/{teacher_id}/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public List<Course> courses(@PathVariable Long teacher_id){
        return courseService.getAllByTeacher(teacher_id);
    }

    @PostMapping("")
    public Course createCourse(@PathVariable Long teacher_id, @RequestBody Course course){
        return courseService.createByTeacher(course, teacher_id);
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourse(@PathVariable Long teacher_id, @PathVariable Long id){
        return courseService.getByTeacher(id, teacher_id);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@RequestBody Course course, @PathVariable Long teacher_id, @PathVariable Long id){
        course.setId(id);
        return courseService.createByTeacher(course, teacher_id);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long teacher_id, @PathVariable Long id){
        courseService.delete(id);
    }
}
