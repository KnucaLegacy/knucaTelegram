package com.theopus.knucaTelegram.controller.exceptions.utill;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadDateFormatException extends RuntimeException {

    public BadDateFormatException(String date, String example) {
        super("Unparseble date:\"" + date + "\".Try to use:\"" +example +"\".Or date in milliseconds.");
    }
}
