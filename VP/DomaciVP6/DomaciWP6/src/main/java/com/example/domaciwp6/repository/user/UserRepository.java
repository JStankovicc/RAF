package com.example.domaciwp6.repository.user;

import com.example.domaciwp6.model.User;

public interface UserRepository {

    public User findUser(String username);
}
