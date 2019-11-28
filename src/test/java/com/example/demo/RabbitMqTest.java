package com.example.demo;

import com.example.demo.message.FanoutSender;
import com.example.demo.message.HelloSender;
import com.example.demo.message.TopicSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {

    @Autowired
    private HelloSender helloSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private FanoutSender fanoutSender;

    @Test
    public void hello() {
        helloSender.send();
    }

    @Test
    public void topic() {

        topicSender.send1();
        topicSender.send2();
    }

    @Test
    public void fanout() {
        fanoutSender.send();
    }
}