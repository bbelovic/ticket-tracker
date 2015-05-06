package org.bbelovic.tickettracker.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ActiveProfiles("development")
@ContextConfiguration(classes = {WebConfiguration.class, TestDataSourceConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SpringContextTest {
    @Test
    public void context_loads() {
    }
}
