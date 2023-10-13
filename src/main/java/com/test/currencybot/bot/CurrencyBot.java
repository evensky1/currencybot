package com.test.currencybot.bot;

import com.test.currencybot.exceptions.UserCountLimitException;
import com.test.currencybot.service.CurrencyService;
import com.test.currencybot.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class CurrencyBot extends TelegramLongPollingBot {

    private final CurrencyService currencyService;
    private final UserService userService;

    @Value("${bot.name}")
    private final String botUsername;

    @Value("${bot.token}")
    private final String botToken;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            var messageText = update.getMessage().getText();
            var chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else {
                sendMessage(chatId, "Don't call us, we'll call you.");
            }
        }
    }

    @Scheduled(fixedDelayString = "${app.constraints.pollDelay}", initialDelayString = "${app.constraints.pollDelay}")
    public void sendChangedRates() {

        currencyService.getChangedRates().subscribe(rates -> {
            var message = rates.stream()
                    .map(rate -> String.format("Name: %s, percent: %s", rate.name(), rate.percent()))
                    .collect(Collectors.joining("\n"));

            notifyAllUsers(message);
        });
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer;

        try {
            userService.enrollNewUser(chatId, name);
            answer = String.format("Hi, %s, you've been successfully registered", name);
        } catch (UserCountLimitException e) {
            log.error(e.getMessage());
            answer = String.format("Hi, %s, sorry, but now we unable to register you", name);
        }

        sendMessage(chatId, answer);
    }

    private void notifyAllUsers(String textToSend) {
        userService.findAll().forEach(u -> sendMessage(u.getChatId(), textToSend));
    }

    private void sendMessage(Long chatId, String textToSend) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
