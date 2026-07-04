package behavioral.observer.tests;

import behavioral.observer.spring.SpringEmailTracker;
import behavioral.observer.spring.SpringOrder;
import behavioral.observer.spring.SpringOrderService;
import behavioral.observer.spring.SpringSmsTracker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringOrderObserverTest.TestConfig.class)
class SpringOrderObserverTest {

    @Configuration
    static class TestConfig {
        @Bean
        public SpringEmailTracker springEmailTracker() {
            return new SpringEmailTracker();
        }

        @Bean
        public SpringSmsTracker springSmsTracker() {
            return new SpringSmsTracker();
        }

        @Bean
        public SpringOrderService springOrderService(org.springframework.context.ApplicationEventPublisher publisher) {
            return new SpringOrderService(publisher);
        }
    }

    @Autowired
    private SpringOrderService springOrderService;

    @Test
    void testSpringEventPublishing() {
        SpringOrder order = new SpringOrder("SP-123", "NEW", "test@spring.com", "0987654321");
        
        springOrderService.updateOrderStatus(order, "PROCESSING");
        
        assertEquals("PROCESSING", order.getStatus());
    }
}
