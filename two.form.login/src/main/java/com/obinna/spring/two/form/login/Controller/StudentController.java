package com.obinna.spring.two.form.login.Controller;

import com.obinna.spring.two.form.login.Entity.Student;
import com.obinna.spring.two.form.login.Service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class StudentController {

    @Autowired
    CustomUserService customUserService;

    @GetMapping("/sForm")
    public String student(Model model){
        Student student = new Student();
        model.addAttribute("student",student);
        return "addStudentForm";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student){
        Student student1 = new Student();
        customUserService.saveStudent(student1);
      return "success";
    }

    @GetMapping("/studentLogin")
    public String login(){
        return "studentLogin";
    }

    @GetMapping("/studentPage")
    public String studentPage(Authentication authentication){
        if (authentication != null)
        System.out.println("Access allowed for authority: "+authentication.getAuthorities());
        return "studentPage";
    }

      @GetMapping("/studentRestricted")
    public String restrictStudent(Authentication authentication){
        if(authentication != null)
          System.out.println("Access allowed for authority: "+authentication.getAuthorities());
        return "restrictAdmin";
    }

//    @GetMapping("/403")
//    public String showAccessDeniedPage(Authentication authentication){
//        System.out.println("Access Denied for authority: "+authentication.getAuthorities());
//        return "accessDenied";
//
//    }

    @GetMapping("/student_logout")
    public String logout(){
        return "studentLogin";
    }

}
