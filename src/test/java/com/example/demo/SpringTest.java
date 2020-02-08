package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Spring Junit
 */
@ActiveProfiles(profiles = "development")
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-unitTest.xml"})
@TestPropertySource(locations = {"classpath:config.properties"})
@WebAppConfiguration()
public class SpringTest {

    @Before
    public void init() {

    }

    @Test
    public void addSnapshotTest(){
    }

}
