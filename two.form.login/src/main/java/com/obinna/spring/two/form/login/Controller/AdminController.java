package com.obinna.spring.two.form.login.Controller;

import com.obinna.spring.two.form.login.Entity.Admin;
import com.obinna.spring.two.form.login.Service.AdminService;
import com.obinna.spring.two.form.login.Service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {
   @Autowired
   AdminService adminService;
    @Autowired
    CustomUserService customUserService;
    @GetMapping("/createAdmin")
    public String adminForm(Model model){
    Admin admin = new Admin();
        model.addAttribute("admin",admin);
        return "create-admin";
    }

    @PostMapping("/saveAdmin")
    public String saveAdmin(@ModelAttribute("admin") Admin admin){
        Admin newAdminLoginDetails = new Admin();
        newAdminLoginDetails.setPassword(admin.getPassword());
        newAdminLoginDetails.setUsername(admin.getUsername());
        adminService.saveAdmin(newAdminLoginDetails);
        return "admin-success";
    }

    @GetMapping("/adminLogin")
    public String login(){
        return "adminLogin";
    }

    @GetMapping("/api/admin/adminPage")
    public String studentPage(){
        return "adminPage";
    }


    @GetMapping("/api/admin/adminOnly")
    public String restrictStudent(Authentication authentication){
        if (authentication != null)
         System.out.println("Access allowed for authority: "+authentication.getAuthorities());
        return "restrictStudent";
    }
    @GetMapping("/api/admin/admin_logout")
    public String logout(){
        return "adminLogin";
    }
    @GetMapping("/403")
    public String showAccessDeniedPage(Authentication authentication){
        if ((authentication != null))
         System.out.println("Access Denied for authority: "+authentication.getAuthorities());
        return "accessDenied";

    }
}
