package com.telegram.englishvocabulary.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    public void getConnect() {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            log.debug("TelegramAPI started!");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();

        var response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        log.debug(message.getFrom().getFirstName() + ": " + message.getText());

        switch (message.getText()) {
            case ("/start"):
                response.setText("There are /hello and /showinfo commands");
                sendAnswerMessage(response);
                break;
            case ("/hello"):
                response.setText("Hello from Bot, " + message.getFrom().getFirstName() + "!");
                sendAnswerMessage(response);
                break;
            case ("/showinfo"):
                response.setText("You speak on " + message.getFrom().getLanguageCode() + " language");
                sendAnswerMessage(response);
                break;
            default:
                response.setText("Unknown command, type /start to get some info");
                sendAnswerMessage(response);
                break;
        }

    }

    public void sendAnswerMessage(SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }
    }

}
