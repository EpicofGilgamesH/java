package com.epic.concurrency.dubboconsumer.Service.serviceImpl

import com.epic.concurrency.dubboconsumer.DubboConsumerApplicationTests
import com.epic.concurrency.dubboconsumer.Service.TestService
import groovy.util.logging.Slf4j
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
public class TestServieImplTest extends DubboConsumerApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    public void testTest() {
        String reslut = testService.test("abc");
    }

    @Test
    public void testSyncTest() {
        String result = testService.syncTest("abc");
    }
}
