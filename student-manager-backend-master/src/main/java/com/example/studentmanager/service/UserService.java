package com.example.studentmanager.service;

import com.example.studentmanager.entity.User;
import com.example.studentmanager.repository.UserRepository;
import com.example.studentmanager.security.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        // 创建随机密码生成器
        this.passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)	// 包含数字
                .useLower(true)		// 包含小写字母
                .useUpper(true)		// 包含大写字母
                .build();
    }

    public void add(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(String username) {
        userRepository.deleteById(username);
    }

    public boolean usernameExists(String username) {
        return userRepository.findById(username).orElse(null) != null;
    }

    public void setPassword(String username, String psw){
        User user = findUserById(username);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(psw));
        userRepository.save(user);
    }

    public void switchEnabled(String username){
        User user = findUserById(username);
        user.setEnabled(user.isEnabled() ? false : true);
        userRepository.save(user);
    }

    public User findUserById(String username){
        return userRepository.findById(username).orElse(new User());
    }

    /**
     * 生成8位随机密码
     * @return
     */
    public String generateTempPassword() {
        return passwordGenerator.generate(8);	// 生成8位随机密码
    }
}
