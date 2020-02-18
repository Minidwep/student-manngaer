package com.example.studentmanager.repository;

import com.example.studentmanager.entity.ResetPwd;
import com.example.studentmanager.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResetPwdRepository extends JpaRepository<ResetPwd, String> {
    @Query("select r from ResetPwd r where  r.email = ?1 and r.pwdkey = ?2")
    ResetPwd resetpwd(String email,String pwdKey);

}