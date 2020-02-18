package com.example.studentmanager.controller;

import com.example.studentmanager.entity.ResetPwd;
import com.example.studentmanager.service.ResetPwdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class ResetPwdController {

    @Autowired
    ResetPwdService resetPwdService;

    @GetMapping("/resetPwd")
    @ResponseBody
    public String resetPwd(@RequestParam("email") String email){

            try {
                if(!StringUtils.isEmpty(email)){
                    ResetPwd resetPwd = new ResetPwd();
                    resetPwd.setEmail(email);
                    //生成一个UUID
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    resetPwd.setPwdkey(uuid);
                    resetPwdService.resetPwd(resetPwd);
                    return "200";
            } else {
                return  "500";
            }
            } catch (Exception e) {
                return  "500";
            }


    }

    @GetMapping("/resetPwdByUser")
    @ResponseBody
    public String  resetPwdByUser(@RequestParam("pwdkey")String pwdkey,@RequestParam("email")String email){

        ResetPwd resetPwd = resetPwdService.getResetPwdByEmailAndPwdKey(email, pwdkey);
        System.out.println("restPwd = " +resetPwd.toString());
        if(resetPwd != null ){

           long timePwd = resetPwd.getPwdtime().getTime();
           long timePwdNow =new Timestamp(new Date().getTime()).getTime();
            System.out.println("timeINfo");
            System.out.println(timePwd);
            System.out.println(timePwdNow);
            System.out.println("timeCha = "+ (timePwdNow - timePwd));

           boolean flag = (timePwdNow - timePwd)<3600000;
            System.out.println("timeCha = "+ (timePwdNow - timePwd));
            if( resetPwd.getStatus()==1 && flag){
                resetPwd.setStatus(0);
                resetPwdService.save(resetPwd);
                resetPwdService.resetUserPwd(resetPwd);

                return "200";
            } else {
                return "500";
            }

        }
        else
            return "500";

    }



}
