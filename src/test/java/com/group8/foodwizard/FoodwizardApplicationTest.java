package com.group8.foodwizard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FoodwizardApplicationTest {
    /**
     * Tests if the Spring application context loads successfully.
     * This is a basic test to ensure that the application can start
     * without any issues.
     */
    @Test
    public void applicationStarts() {
        // Verify the main method can be called without exceptions
        FoodwizardApplication.main(new String[] {});
    }
}
