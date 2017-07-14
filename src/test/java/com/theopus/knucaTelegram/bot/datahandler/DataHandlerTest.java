package com.theopus.knucaTelegram.bot.datahandler;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DataHandlerTest {

    @Test
    public void name() throws Exception {
        Set<Integer> it = new LinkedHashSet<>();

        for (int i = 0; i < 500; i++) {
            it.add(i);
        }

        Collection<Integer> integers = it;

        integers.forEach(System.out::println);

    }
}