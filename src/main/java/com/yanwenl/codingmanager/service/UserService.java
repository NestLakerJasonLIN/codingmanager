package com.yanwenl.codingmanager.service;

import com.yanwenl.codingmanager.model.User;

public interface UserService {

    public User findUserByUserName(String userName);

    public User saveUser(User user);
}
