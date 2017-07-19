package com.theopus.knucaTelegram.bot.action.facrory;

import com.theopus.knucaTelegram.GlobalMocks;
import com.theopus.knucaTelegram.data.entity.Group;
import com.theopus.knucaTelegram.data.service.GroupService;
import com.theopus.knucaTelegram.generictestclasses.GenericDBWithDBCheck;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@ContextConfiguration(classes = {BaseDataActionFactory.class})
public class BaseDataActionFactoryTest extends GenericDBWithDBCheck{

    @Resource
    private BaseDataActionFactory baseDataActionFactory;
    @Resource
    private GroupService groupService;


    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testGetGroupDayInfo() throws Exception {
        Group group = groupService.getById(1);
        baseDataActionFactory.sendExactDayDataAction(group,1, GlobalMocks.DATE_MOCK, 0)
                .buildMessage().forEach(message -> {
            System.out.println(message.getText());
        });
    }

    @Test
    public void testGetGroupWeekInfo() throws Exception {
        Group group = groupService.getById(1);
        baseDataActionFactory.sendWeekDataAction(group,1, GlobalMocks.DATE_MOCK, 0)
                .buildMessage().forEach(message -> {
            System.out.println(message.getText());
        });
    }

    @Test
    public void testBadRequestInfo() throws Exception {
        String string = "ns huiy";
        baseDataActionFactory.sendBadRequest(string,1).buildMessage().forEach(message -> {
            System.out.println(message.getText());
        });
    }
}