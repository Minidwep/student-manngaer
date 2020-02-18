package com.example.studentmanager.service;

import com.example.studentmanager.entity.Staff;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.User;
import com.example.studentmanager.repository.StaffRepository;
import com.example.studentmanager.security.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StaffService {

    private static Logger logger = LoggerFactory.getLogger(StaffService.class);

    private final StaffRepository staffRepository;
    private JavaMailSender javaMailSender;
    private final UserService userService;
    private final Environment env;

    @Autowired
    StaffService(StaffRepository staffRepository, JavaMailSender javaMailSender, UserService userService, Environment env){

        this.staffRepository = staffRepository;
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.env = env;
    }

    /**
     * 添加教工
     * @param staff
     */

    public void add(Staff staff) {
        String password = userService.generateTempPassword();
        User user = staff.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        // 发邮件告知密码
        sendPasswordEmail(staff.getName(), user.getEmail(), password);
        logger.info("StaffNo: " + user.getUsername() + ", password: " + password);
        userService.add(user);
        staffRepository.save(staff);
    }

    /**
     * 分页获取职工信息
     * @param page 第几页
     * @param size 每页条数
     * @return
     */
    public Page<Staff> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Staff> result = staffRepository.findAll(pageable);
        return result;
    }

    public List<Staff> findAll() {
        List<Staff> result = staffRepository.findAll();
        return result;
    }

    /**
     * 删除职工信息
     * @param staffNo
     */
    public void delete(String staffNo) {
//        staffRepository.deleteById(staffNo);
        Staff staff = new Staff();
        staff.setStaffNo(staffNo);
        staffRepository.delete(staff);

    }

    public Staff findByStaffNo(String staffNo) {
        Staff staff = staffRepository.findById(staffNo)
                .orElseThrow(() -> new EntityNotFoundException(staffNo));
        return staff;
    }

    public void update(Staff staff) {

        User user = userService.findUserById(staff.getStaffNo());
        staff.getUser().setPassword(user.getPassword());
        staff.getUser().setEnabled(user.isEnabled());
        staffRepository.save(staff);
    }

    public Page<Staff> search(String key, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return staffRepository.search(key, pageable);
    }

    /**
     * 通过邮件发送初始密码
     * @param name 职工姓名
     * @param email 职工邮件地址
     * @param password 初始密码
     */
    private void sendPasswordEmail(String name, String email, String password) {
        String senderEmail = env.getProperty("spring.mail.username");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom(senderEmail);

        msg.setSubject("师生基本信息管理系统登录信息");
        msg.setText(String.format("%s：\n您好，你的基本信息管理系统账户已创建。初始登录密码为%s。请及时登录并修改密码。\n\n系统管理员", name, password));

        javaMailSender.send(msg);
    }


}
