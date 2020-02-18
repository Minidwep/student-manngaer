package com.example.studentmanager.service;

import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.User;
import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.repository.UserRepository;
import com.example.studentmanager.security.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class StudentService {
    private static Logger logger = LoggerFactory
            .getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private JavaMailSender javaMailSender;
    private final UserService userService;


    private final Environment env;

    @Autowired
    public StudentService(StudentRepository studentRepository, JavaMailSender javaMailSender, UserService userService, Environment env) {
        this.studentRepository = studentRepository;
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.env = env;
    }
    /**
     * 增加新学生
     * @param student
     */
    public void add(Student student) {
        String password = userService.generateTempPassword();
        User user = student.getUser();
        user.setPassword(password);
	    // 发邮件告知密码
        sendPasswordEmail(student.getName(), user.getEmail(), password);
        logger.info("StudentNo: " + user.getUsername() + ", password: " + password);
        userService.add(student.getUser());
        studentRepository.save(student);
    }

    /**
     * 分页获取学生信息
     * @param page 第几页
     * @param size 每页条数
     * @return
     */
    public Page<Student> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> result = studentRepository.findAll(pageable);
        return result;
    }

    /**
     * 删除学生信息
     * @param studentNo
     */
    public void delete(String studentNo) {
        studentRepository.deleteById(studentNo);
        userService.delete(studentNo);
    }

    public Student findByStudentNo(String studentNo) {
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new EntityNotFoundException(studentNo));
        return student;
    }

    public void update(Student student) {
        studentRepository.save(student);
    }

    public Page<Student> search(String key, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentRepository.search(key, pageable);
    }

    /**
     * 通过邮件发送初始密码
     * @param name 学生姓名
     * @param email 学生邮件地址
     * @param password 初始密码
     */
    private void sendPasswordEmail(String name, String email, String password) {
        String senderEmail = env.getProperty("spring.mail.username");
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom(senderEmail);

        msg.setSubject("师生基本信息管理系统登录信息");
        msg.setText(String.format("%s同学：\n你好，你的师生基本信息管理系统账户已创建。初始登录密码为%s。请及时登录并修改密码。\n\n系统管理员", name, password));

        javaMailSender.send(msg);
    }
}
