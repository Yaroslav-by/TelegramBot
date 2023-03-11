package com.telegram.englishvocabulary.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    void showMainMenuKeyboard(Message message);
    void showVocabularyKeyboard(Message message);
    void showExerciseKeyboard(Message message);

}
