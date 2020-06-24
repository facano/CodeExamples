package com.exercise.api.data.services;

import com.exercise.api.data.models.Course;
import com.exercise.api.data.models.Teacher;
import com.exercise.api.data.repositories.CourseRepository;
import com.exercise.api.data.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService extends AbstractService<Course, CourseRepository> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    public CourseService(CourseRepository repository){
        this.repository = repository;
    }

    public List<Course> getAllByTeacher(Long teacherId){
        return repository.findByTeacherId(teacherId);
    }

    public Course createByTeacher(Course course, Long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isPresent()){
            Teacher teacher = teacherOptional.get();
            course.setTeacher(teacher);
            repository.save(course);
            teacher.addCourse(course);
            teacherRepository.save(teacher);
            return course;
        }
        return null;
    }

    public Optional<Course> getByTeacher(Long id, Long teacherId) {
        return repository.findByIdAndTeacherId(id, teacherId);
    }
}
