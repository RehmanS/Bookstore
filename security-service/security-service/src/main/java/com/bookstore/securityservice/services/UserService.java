package com.example.jwttoken.services;

import com.example.jwttoken.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User createUser(User user);

    User getUserById(long id);

    void delete(long id);

    User updateUser(User user, long id);

    User getOneUserByUserName(String userName);
}
