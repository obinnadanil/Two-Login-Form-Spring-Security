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

    @GetMapping("/studentForm")
    public String student(Model model){
        Student student = new Student();
        model.addAttribute("student",student);
        return "addStudentForm";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student){
        Student newStudentLoginDetails = new Student();
        newStudentLoginDetails.setPassword(student.getPassword());
        newStudentLoginDetails.setUsername(student.getUsername());
        customUserService.saveStudent(newStudentLoginDetails);
      return "success";
    }

    @GetMapping("/studentLogin")
    public String login(){
        return "studentLogin";
    }

    @GetMapping("/api/student/studentPage")
    public String studentPage(Authentication authentication){
        if (authentication != null)
         System.out.println("Access allowed for authority: "+authentication.getAuthorities());
        return "studentPage";
    }

      @GetMapping("/api/student/studentOnly")
    public String restrictAdmin(Authentication authentication){
        if(authentication != null)
          System.out.println("Access allowed for authority: "+authentication.getAuthorities());
        return "restrictAdmin";
    }



    @GetMapping("/api/student/student_logout")
    public String logout(){
        return "studentLogin";
    }

}
