package com.telegram.englishvocabulary.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainMenuKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Словарь"));
        keyboardRow.add(new KeyboardButton("Упражнения"));

        return getReplyKeyboardMarkup(keyboardRow);

    }

    public ReplyKeyboardMarkup getVocabularyMenuKeyBoard() {

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Добавить новое слово"));
        keyboardRow.add(new KeyboardButton("Добавить случайное слово"));

        return getReplyKeyboardMarkup(keyboardRow);

    }

    public ReplyKeyboardMarkup getExerciseMenuKeyBoard() {

        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Получить случайное слово"));
        keyboardRow.add(new KeyboardButton("Получить 10 слов"));

        return getReplyKeyboardMarkup(keyboardRow);

    }

    private ReplyKeyboardMarkup getReplyKeyboardMarkup(KeyboardRow keyboardRow) {
        List<KeyboardRow> list = new ArrayList<>();
        list.add(keyboardRow);

        final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(list);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        return keyboardMarkup;
    }

}
