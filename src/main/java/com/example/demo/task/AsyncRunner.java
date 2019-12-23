package com.example.demo.task;

import com.example.demo.service.AsyncService;
import com.example.demo.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AsyncRunner.class);

    private final AsyncService asyncService;

    public AsyncRunner(AsyncService gitHubLookupService) {
        this.asyncService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        Future<User> page1 = asyncService.findUser("PivotalSoftware");
        Future<User> page2 = asyncService.findUser("CloudFoundry");
        Future<User> page3 = asyncService.findUser("Spring-Projects");

        // Wait until they are all done
        while (!(page1.isDone() && page2.isDone() && page3.isDone())) {
            Thread.sleep(10); //10-millisecond pause between each check
        }

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        logger.info("--> " + page1.get());
        logger.info("--> " + page2.get());
        logger.info("--> " + page3.get());
    }
}
