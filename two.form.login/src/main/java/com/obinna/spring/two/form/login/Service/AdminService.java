package com.obinna.spring.two.form.login.Service;

import com.obinna.spring.two.form.login.Entity.Admin;
import com.obinna.spring.two.form.login.Entity.AdminUserDetail;
import com.obinna.spring.two.form.login.Repo.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService  {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    PasswordEncoder encoder;

    public void saveAdmin(Admin admin){
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setUsername(admin.getUsername());
        adminRepo.save(admin);
    }
}
