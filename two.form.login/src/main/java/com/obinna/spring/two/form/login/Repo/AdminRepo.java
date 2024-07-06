package com.obinna.spring.two.form.login.Repo;

import com.obinna.spring.two.form.login.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {

    Optional<Admin> findAdminByUsername(String username);
}
