package com.example.studentmanager.service;

import com.example.studentmanager.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class StudentManagerUserDetailsService implements UserDetailsService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private final String sqlLoadUser;
    private final String sqlLoadAuthorities;
    private final RowMapper<User> myUserDetailsRowMapper;
    private final RowMapper<GrantedAuthority> authorityRowMapper;

    private static Logger logger = LoggerFactory
            .getLogger(UserDetailsService.class);

    public StudentManagerUserDetailsService() {
        super();
        sqlLoadUser = "SELECT username, password, email, enabled FROM USER WHERE username=? OR email=?";
        sqlLoadAuthorities = "SELECT role FROM USER_ROLE WHERE username=?";
        myUserDetailsRowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getBoolean(4));
            }
        };
        authorityRowMapper = new RowMapper<GrantedAuthority>() {
            @Override
            public GrantedAuthority mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return new SimpleGrantedAuthority(rs.getString(1));
            }
        };
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        try {
            User userFromQuery = jdbcTemplate.queryForObject(sqlLoadUser,
                    myUserDetailsRowMapper, username, username);
            logger.debug("查询得到用户：{}", userFromQuery);
            List<GrantedAuthority> authorities = jdbcTemplate.query(
                    sqlLoadAuthorities, authorityRowMapper, username);
            logger.debug("得到其权限：{}", authorities);
            return new User(userFromQuery.getUsername(),
                    userFromQuery.getPassword(), userFromQuery.getEmail(), userFromQuery.isEnabled(),
                    authorities);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("查询结果集为空:{}", username);
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
    }
}
