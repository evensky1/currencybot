package com.test.currencybot.bot.commands.impl;

import com.test.currencybot.bot.commands.BotCommand;
import com.test.currencybot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;


@Slf4j
@Component("/quit")
@RequiredArgsConstructor
public class QuitCommand implements BotCommand {
    private final UserService userService;

    @Override
    public String process(Update update) {
        var chatId = update.getMessage().getChatId();

        userService.disenrollUser(chatId);

        return "Bye";
    }
}
