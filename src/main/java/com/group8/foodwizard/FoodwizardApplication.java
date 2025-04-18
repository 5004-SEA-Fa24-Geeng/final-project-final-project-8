package com.group8.foodwizard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Food Wizard Spring Boot application.
 * <p>
 * This class bootstraps the application using
 * {@link SpringApplication#run(Class, String...)}.
 * It also enables component scanning, auto-configuration, and Spring Boot's
 * configuration support
 * via the {@link SpringBootApplication} annotation.
 */
@SpringBootApplication
public final class FoodwizardApplication {
    /**
     * Private constructor to prevent instantiation.
     */
    private FoodwizardApplication() {
    }

    /**
     * Main method that starts the Food Wizard application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(FoodwizardApplication.class, args);
    }
}
