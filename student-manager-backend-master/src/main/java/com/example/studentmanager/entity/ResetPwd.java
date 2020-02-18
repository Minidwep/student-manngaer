package com.example.studentmanager.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RESET_PWD")
public class ResetPwd {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String username;
    private String pwdkey;
    private int status;
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date pwdtime;

    public Date getPwdtime() {
        return pwdtime;
    }

    public void setPwdtime(Date pwdtime) {
        this.pwdtime = pwdtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwdkey() {
        return pwdkey;
    }

    public void setPwdkey(String pwdkey) {
        this.pwdkey = pwdkey;
    }

    @Override
    public String toString() {
        return "ResetPwd{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pwdkey='" + pwdkey + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
