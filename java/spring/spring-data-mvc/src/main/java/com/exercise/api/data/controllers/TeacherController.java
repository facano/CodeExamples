package com.exercise.api.data.controllers;

import com.exercise.api.data.models.Student;
import com.exercise.api.data.models.Teacher;
import com.exercise.api.data.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("")
    public List<Teacher> students(){
        return teacherService.getAll();
    }

    @PostMapping("")
    public Teacher createStudents(@RequestBody Teacher student){
        return teacherService.create(student);
    }

    @GetMapping("/{id}")
    public Optional<Teacher> getStudent(@PathVariable Long id){
        return teacherService.get(id);
    }

    @PutMapping("/{id}")
    public Teacher updateStudent(@RequestBody Teacher student, @PathVariable Long id){
        student.setId(id);
        return teacherService.update(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        teacherService.delete(id);
    }
}
