package com.test.currencybot.service;

import com.test.currencybot.model.User;

import java.util.List;

public interface UserService {

    void enrollNewUser(Long chatId, String username);
    List<User> findAll();
}
