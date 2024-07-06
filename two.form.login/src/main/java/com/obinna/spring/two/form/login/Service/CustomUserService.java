package com.obinna.spring.two.form.login.Service;

import com.obinna.spring.two.form.login.Entity.*;
import com.obinna.spring.two.form.login.Repo.AdminRepo;
import com.obinna.spring.two.form.login.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {
    private final StudentRepo studentRepo;
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserService(StudentRepo studentRepo, AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Student>student = studentRepo.findStudentByUsername(username);
        Optional<Admin> admin = adminRepo.findAdminByUsername(username);
        if (student.isPresent())
            return new  StudentUserDetail(student.get());
        if(admin.isPresent())
            return new AdminUserDetail(admin.get());
        throw new UsernameNotFoundException("user not found");
        }


    public void saveStudent(Student student){
       student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setUsername(student.getUsername());
        studentRepo.save(student);
    }

    public boolean authenticate(String username, String password) {
        Optional<Student> studentOpt = studentRepo.findStudentByUsername(username);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (passwordEncoder.matches(password, student.getPassword())) {
                return true;
            }
        }

        Optional<Admin> adminOpt = adminRepo.findAdminByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return passwordEncoder.matches(password, admin.getPassword());
        }

        return false;
    }

}
