package com.theopus.knucaTelegram.controller.exceptions.ex404;

public class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(long id) {
        super("Not found group with id = " + id + ".");
    }
}
