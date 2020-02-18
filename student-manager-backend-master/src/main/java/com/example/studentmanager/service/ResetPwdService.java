package com.example.studentmanager.service;

import com.example.studentmanager.entity.ResetPwd;
import com.example.studentmanager.entity.Staff;
import com.example.studentmanager.entity.User;
import com.example.studentmanager.repository.ResetPwdRepository;
import com.example.studentmanager.repository.StaffRepository;
import com.example.studentmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResetPwdService {

    private static Logger logger = LoggerFactory.getLogger(ResetPwdService.class);

    private final ResetPwdRepository resetPwdRepository;
    private final UserRepository userRepository;
    private JavaMailSender javaMailSender;
    private final UserService userService;
    private final Environment env;

    @Autowired
    ResetPwdService(UserRepository userRepository,ResetPwdRepository resetPwdRepository, JavaMailSender javaMailSender, UserService userService, Environment env){
        this.userRepository = userRepository;
        this.resetPwdRepository = resetPwdRepository;
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.env = env;
    }

    public void resetPwd(ResetPwd resetPwd) {
        sendPwdKeyEmail(resetPwd);
        resetPwd.setStatus(1);
        User user = userRepository.getUserByEmail(resetPwd.getEmail());
        resetPwd.setUsername(user.getUsername());
        Timestamp time2 = new Timestamp(new Date().getTime());
        resetPwd.setPwdtime(time2);
        resetPwdRepository.save(resetPwd);

    }


    private void sendPwdKeyEmail(ResetPwd resetPwd) {
        String senderEmail = env.getProperty("spring.mail.username");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(resetPwd.getEmail());
        msg.setFrom(senderEmail);
        msg.setSubject("ResetYourPassword");
        msg.setText("Your key is"
                +"                                                  " +
                        "                           " +
                        "                                             " +
                        "                                          "
                +resetPwd.getPwdkey()
                );
        javaMailSender.send(msg);
    }

    public ResetPwd getResetPwdByEmailAndPwdKey(String email, String pwdKey){
        ResetPwd resetpwd = resetPwdRepository.resetpwd(email, pwdKey);
        if(resetpwd != null)
            return resetpwd;
        else
            return null;
    }

    public void save(ResetPwd resetPwd) {
        resetPwdRepository.save(resetPwd);
    }

    public void resetUserPwd(ResetPwd resetPwd) {

        User user = userRepository.getUserByUserName(resetPwd.getUsername());
        String password = userService.generateTempPassword();
        user.setPassword(password);
        sendPwdKeyEmail(user);
        userRepository.save(user);

    }

    private void sendPwdKeyEmail(User user) {
        String senderEmail = env.getProperty("spring.mail.username");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getEmail());
        msg.setFrom(senderEmail);
        msg.setSubject("ResetYourPassword");
        msg.setText("Your id is "
                 +user.getUsername()+
                "Password is "+
                user.getPassword()
        );
        javaMailSender.send(msg);
    }

//    public boolean resetPwdByUser(String email, String pwdKey) {
//        ResetPwd resetpwd = resetPwdRepository.resetpwd(email, pwdKey);
//        if(!StringUtils.isEmpty(resetpwd)){
//            if(resetpwd.getStatus() ==1)
//                return  true;
//            else
//                return false;
//        } else {
//            return false;
//        }
//    }
}
