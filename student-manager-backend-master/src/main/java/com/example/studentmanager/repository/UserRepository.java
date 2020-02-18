package com.example.studentmanager.repository;

import com.example.studentmanager.entity.ResetPwd;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("select r from User r where  r.email = ?1 ")
    User getUserByEmail(String email);

    @Query("select r from User r where  r.username = ?1 ")
    User getUserByUserName(String username);
}
