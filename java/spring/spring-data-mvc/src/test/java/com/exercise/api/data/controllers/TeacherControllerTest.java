package com.exercise.api.data.controllers;

import com.exercise.api.data.helpers.ConstantURL;
import com.exercise.api.data.models.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeacherControllerTest extends ControllerTest {

    private Teacher buildTeacher(String name) {
        return Teacher.builder()
                      .name(name)
                      .build();
    }

    private Teacher postTeacher(Teacher teacher, URI uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Teacher> request = new HttpEntity<>(teacher);
        return restTemplate.postForObject(uri, request, Teacher.class);
    }

    @Test
    void createTeacherSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("post test case");

        URI uri = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uri);

        assertThat(postedTeacherResponse).isNotNull();
        assertThat(postedTeacherResponse.getName()).isEqualTo(teacher.getName());
    }

    @Test
    void getTeacherSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("get test case");

        URI uri = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uri);

        URI entityUri = buildURI(ConstantURL.TEACHERS, postedTeacherResponse.getId()
                .toString()
        );
        RestTemplate restTemplate = new RestTemplate();
        Teacher teacherResponse = restTemplate.getForObject(entityUri, Teacher.class);

        assertThat(teacherResponse).isNotNull();
        assertThat(teacherResponse.getName()).isEqualTo(teacher.getName());
    }

    @Test
    void getTeachersSuccessfully() throws URISyntaxException {
        Teacher teacher1 = buildTeacher("get all test case 1");
        Teacher teacher2 = buildTeacher("get all test case 2");

        URI uri = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse1 = postTeacher(teacher1, uri);
        Teacher postedTeacherResponse2 = postTeacher(teacher2, uri);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Teacher[]> teacherResponse = restTemplate.getForEntity(uri, Teacher[].class);
        List teacherList = Arrays.asList(teacherResponse.getBody());

        assertThat(teacherResponse).isNotNull();
        assertThat(teacherList.size()).isGreaterThanOrEqualTo(2);
        assertThat(teacherList).extracting("name")
                    .contains(teacher1.getName(), teacher2.getName());
    }

    @Test
    void deleteTeacherSuccessfully() throws URISyntaxException {
        Teacher teacher = buildTeacher("delete test case");

        URI uri = buildURI(ConstantURL.TEACHERS);
        Teacher postedTeacherResponse = postTeacher(teacher, uri);

        URI entityUri = buildURI(ConstantURL.TEACHERS, postedTeacherResponse.getId()
                                                                            .toString()
        );
        RestTemplate restTemplate = new RestTemplate();
        Teacher teacherResponse = restTemplate.getForObject(entityUri, Teacher.class);

        assertThat(teacherResponse).isNotNull();
        assertThat(teacherResponse.getName()).isEqualTo(teacher.getName());

        restTemplate.delete(entityUri);
        teacherResponse = restTemplate.getForObject(entityUri, Teacher.class);

        assertThat(teacherResponse).isNull();
    }

}
