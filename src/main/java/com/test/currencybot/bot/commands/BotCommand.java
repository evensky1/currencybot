package com.test.currencybot.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommand {
    String process(Update update);
}
