package com.telegram.englishvocabulary.service;

import com.telegram.englishvocabulary.controller.TelegramBot;
import com.telegram.englishvocabulary.keyboards.MainMenuKeyboard;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Log4j
public class MessageHandlerImpl implements MessageHandler{

    private TelegramBot telegramBot;

    @Autowired
    private MainMenuKeyboard keyboardMarkup;

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void showMainMenuKeyboard(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMarkup.getMainMenuKeyboard());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Вы вошли в главное меню!");

        sendAnswerMessage(telegramBot, sendMessage);
    }

    @Override
    public void showVocabularyKeyboard(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMarkup.getVocabularyMenuKeyBoard());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Вы вошли в меню словаря!");

        sendAnswerMessage(telegramBot, sendMessage);

    }

    @Override
    public void showExerciseKeyboard(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(keyboardMarkup.getExerciseMenuKeyBoard());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Вы вошли в меню упражнений!");

        sendAnswerMessage(telegramBot, sendMessage);

    }

    public void sendAnswerMessage(TelegramBot telegramBot, SendMessage sendMessage) {
        if (sendMessage != null) {
            try {
                telegramBot.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
                log.error(e);
            }
        }
    }
}
