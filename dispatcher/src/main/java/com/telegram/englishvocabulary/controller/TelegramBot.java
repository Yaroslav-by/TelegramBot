package com.telegram.englishvocabulary.controller;

import com.telegram.englishvocabulary.service.MessageHandlerImpl;
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

    private final MessageHandlerImpl messageHandler;

    public TelegramBot(MessageHandlerImpl messageHandler) {
        this.messageHandler = messageHandler;
    }

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
            messageHandler.registerBot(this);
            log.debug("TelegramAPI started!");
        } catch (TelegramApiException e) {
            log.error(e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpdateReceived(Update update) {

        var message = update.getMessage();

        if (update.hasMessage()) {
            try {
                var response = new SendMessage();
                response.setChatId(message.getChatId());
                log.debug(message.getFrom().getFirstName() + ": " + message.getText());

                switch (message.getText()) {
                    case ("/start"):
                    case ("/menu"):
                        messageHandler.showMainMenuKeyboard(message);
                        break;
                    case ("Словарь"):
                        messageHandler.showVocabularyKeyboard(message);
                        break;
                    case ("Упражнения"):
                        messageHandler.showExerciseKeyboard(message);
                        break;
                    case ("/info"):
                        response.setText("Begin with /start or /menu command");
                        messageHandler.sendAnswerMessage(this, response);
                        break;
                    default:
                        response.setText("Unknown command, type /info to get some information");
                        messageHandler.sendAnswerMessage(this, response);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage callMessage = new SendMessage();
                callMessage.setText(update.getCallbackQuery().getData());
                callMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                execute(callMessage);
            } catch (TelegramApiException e) {
                log.error(e);
            }
        }

    }

//    public SendMessage sendKeyBoardMainMenuMessage(long chatID) {
//
//        InlineKeyboardMarkup keyboardMarkupMainMenu = new InlineKeyboardMarkup();
//
//        InlineKeyboardButton vocabularyButton = new InlineKeyboardButton();
//        vocabularyButton.setText("Словарь");
//        vocabularyButton.setCallbackData("Вы вошли в Словарь!");
//
//        InlineKeyboardButton exerciseButton = new InlineKeyboardButton();
//        exerciseButton.setText("Упражнения");
//        exerciseButton.setCallbackData("Вы вошли в Упражнения!");
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(vocabularyButton);
//        keyboardButtonsRow1.add(exerciseButton);
//
//        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
//        buttons.add(keyboardButtonsRow1);
//
//        keyboardMarkupMainMenu.setKeyboard(buttons);
//
//        SendMessage message = new SendMessage();
//        message.setChatId(chatID);
//        message.setText("Menu");
//        message.setReplyMarkup(keyboardMarkupMainMenu);
//
//        return message;
//
//    }

}
