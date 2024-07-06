package com.obinna.spring.two.form.login.Repo;

import com.obinna.spring.two.form.login.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    Optional<Student> findStudentByUsername(String username);
}
