package com.theopus.knucaTelegram.bot.util;

import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeyboardBuilder {

    public static InlineKeyboardMarkup gridInlineKeyBoard(Map<String, String> strings, int columns){
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();


            int i = 0;
            for (Map.Entry<String, String> pair: strings.entrySet()) {
                if (i % columns == 0){
                    rowsInline.add(rowInline);
                    rowInline = new ArrayList<>();
                }
                rowInline.add(new InlineKeyboardButton().setText(pair.getKey()).setCallbackData(pair.getValue()));
                i++;
            }
            rowsInline.add(rowInline);
            markupInline.setKeyboard(rowsInline);

            return markupInline;
    }
}
