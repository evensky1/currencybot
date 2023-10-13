package com.test.currencybot.bot.commands.impl;

import com.test.currencybot.bot.commands.BotCommand;
import com.test.currencybot.exceptions.UserCountLimitException;
import com.test.currencybot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component("/start")
@RequiredArgsConstructor
public class StartCommand implements BotCommand {

    private final UserService userService;

    @Override
    public String process(Update update) {
        var chatId = update.getMessage().getChatId();
        var name = update.getMessage().getChat().getFirstName();

        String answer;

        try {
            userService.enrollNewUser(chatId, name);
            answer = String.format("Hi, %s, you've been successfully registered", name);
        } catch (UserCountLimitException e) {
            log.error(e.getMessage());
            answer = String.format("Hi, %s, sorry, but now we are unable to register you", name);
        }

        return answer;
    }
}
