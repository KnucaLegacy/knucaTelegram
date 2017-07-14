package com.theopus.knucaTelegram.bot.datahandler;

import java.util.Collection;

public abstract class DataHandler {

    protected String header;
    protected Collection<String> stringCollection;
    protected String title;

    public DataHandler() {
    }

    public DataHandler(String header, Collection<String> stringCollection, String title) {
        this.header = header;
        this.stringCollection = stringCollection;
        this.title = title;
    }

    protected String getHeader() {
        return header;
    }

    protected void setHeader(String header) {
        this.header = header;
    }

    protected Collection<String> getStringCollection() {
        return stringCollection;
    }

    protected void setStringCollection(Collection<String> stringCollection) {
        this.stringCollection = stringCollection;
    }

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }
}
