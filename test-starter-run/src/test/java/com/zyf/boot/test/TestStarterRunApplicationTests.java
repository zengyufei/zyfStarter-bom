package com.zyf.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStarterRunApplicationTests {

    @Autowired
    private PersonService personService;

    @Test
    public void testHelloWorld() {
        personService.sayHello();
    }

}
