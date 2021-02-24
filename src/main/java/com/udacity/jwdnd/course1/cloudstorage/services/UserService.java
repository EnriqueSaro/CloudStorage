package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;


    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username){
        return userMapper.getUserByUsername(username) == null;
    }

    public User findUserByUsername(String username){
        return userMapper.getUserByUsername(username);
    }

    public Integer createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encoded_salt = Base64.getEncoder().encodeToString(salt);
        String hashed_value = hashService.getHashedValue(user.getPassword(),encoded_salt);
        user.setSalt(encoded_salt);
        user.setPassword(hashed_value);

        return userMapper.insertUser(user);
    }

}
