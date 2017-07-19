package com.theopus.knucaTelegram.generictestclasses;

import com.theopus.knucaTelegram.config.PersistenceConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
public class GenericDBWithDBCheck {

    @Resource
    private EntityManagerFactory emf;
    protected EntityManager em;

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }
}
