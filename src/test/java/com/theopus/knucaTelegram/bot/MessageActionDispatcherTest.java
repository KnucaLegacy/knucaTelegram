package com.theopus.knucaTelegram.bot;

import com.theopus.knucaTelegram.bot.command.TelegramMessageFormater;
import com.theopus.knucaTelegram.bot.action.Action;
import com.theopus.knucaTelegram.config.TestPersistenceConfig;
import com.theopus.knucaTelegram.data.service.GroupService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TestPersistenceConfig.class,
        MessageActionDispatcher.class,
        ResponseAction.class,
        TelegramMessageFormater.class})
//@SpringBootTest
public class MessageActionDispatcherTest {


    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;


    @Qualifier("messageActionDispatcher")
    @Autowired
    private MessageActionDispatcher messageActionDispatcher;





    private Date dateMock = new GregorianCalendar(2017, 4 - 1, 17).getTime();
    @Autowired
    private GroupService groupService;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @Test
    public void testGroupCase() throws Exception {
        messageActionDispatcher.handleMessage("иусхуй", 7357).execute();
        messageActionDispatcher.handleMessage("іуст32", 7357).execute();
    }

    @Test
    public void test() throws Exception {
        System.out.println(messageActionDispatcher.groupsList.contains("ІУС"));
    }

    @Test
    public void name() throws Exception {
        messageActionDispatcher.notExactGroupMatching("блядинаебаная").execute();
    }

    @Test
    public void threadSafeTest() throws Exception {
        List<Action> list = new ArrayList<>();
        List<Thread> threads = new LinkedList<>();
        System.out.println("Creating threads");

        int count = 10;
        for (int i = 0; i < count; i++) {
            int finalI = i;
            threads.add(new Thread(() -> {
                list.add(messageActionDispatcher.handleMessage("іуст-31", finalI));
            }));
        }
        System.out.println("StrtingTgreads");
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Set<Long> set = new HashSet<>();
        for (Action e: list) {
            set.add(e.getChatId());
        };

        System.out.println(set);
        Assert.assertEquals(count, set.size());
    }

    @Test
    public void threadSafeViaBD() throws Exception {
        List<Action> list = new ArrayList<>();
        List<Thread> threads = new LinkedList<>();
        System.out.println("Creating threads");

        int count = 300;
        for (int i = 0; i < count; i++) {
            int finalI = i;
            System.out.println(i);
            threads.add(new Thread(() -> {
                list.add(messageActionDispatcher.handleMessage("іуст-", finalI));
            }));
        }
        System.out.println("StrtingTgreads");
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread.sleep(5000);
        Set<Long> set = new HashSet<>();
        for (Action e: list) {
            set.add(e.getChatId());
        };

        System.out.println(set);
        Assert.assertEquals(count, set.size());
    }
}