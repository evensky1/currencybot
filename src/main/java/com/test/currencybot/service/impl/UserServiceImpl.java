package com.test.currencybot.service.impl;

import com.test.currencybot.config.Constraints;
import com.test.currencybot.exceptions.UserCountLimitException;
import com.test.currencybot.model.User;
import com.test.currencybot.repository.UserRepository;
import com.test.currencybot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Constraints constraints;
    private final UserRepository userRepository;

    @Override
    public void enrollNewUser(Long chatId, String username) {
        if (userRepository.count() > constraints.getUserCount())  {
            throw new UserCountLimitException("User count limit has been exceeded");
        }

        var user = User.builder()
                .chatId(chatId)
                .username(username)
                .build();

        userRepository.save(user);
    }

    @Override
    public void disenrollUser(Long chatId) {
        userRepository.deleteById(chatId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
