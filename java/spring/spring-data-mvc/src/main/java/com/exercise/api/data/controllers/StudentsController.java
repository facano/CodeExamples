package com.exercise.api.data.controllers;

import com.exercise.api.data.models.Student;
import com.exercise.api.data.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentsController {

    private final StudentService studentService;

    @Autowired
    public StudentsController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public List<Student> students(){
        return studentService.getAll();
    }

    @PostMapping("")
    public Optional<Student> createStudents(@RequestBody Student student){
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudent(@PathVariable Long id){
        return studentService.get(id);
    }

    @PutMapping("/{id}")
    public Optional<Student> updateStudent(@RequestBody Student student, @PathVariable Long id){
        student.setId(id);
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.delete(id);
    }
}
