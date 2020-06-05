package com.yanwenl.codingmanager.repository;

import com.yanwenl.codingmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}
