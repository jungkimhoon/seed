package com.example.seed.service;

import com.example.seed.repository.UserRepository;
import com.example.seed.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserVo> findAll() {
        List<UserVo> users = new ArrayList<>();
        userRepository.findAll().forEach(e -> users.add(e));
        return users;
    }
}
