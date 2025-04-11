package com.group8.foodwizard;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FoodwizardApplicationTest {
    @Test
    public void applicationStarts() {
        // Verify the main method can be called without exceptions
        FoodwizardApplication.main(new String[] {});
    }
}
