package com.test.currencybot.bot.commands.impl;

import com.test.currencybot.bot.commands.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultCommand implements BotCommand {
    @Override
    public String process(Update update) {
        return "Hmm, looks like we don't understand your request right now";
    }
}
