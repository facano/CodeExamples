package com.exercise.api.data.controllers;

import com.exercise.api.data.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {

    private final TeacherService teacherService;

    @Autowired
    public FrontEndController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping({"/teachers", "/"})
    public String teachers(Model model){
        model.addAttribute("teachers", teacherService.getAll());
        return "teachers";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
