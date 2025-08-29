package com.example.sqlsolver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final WebhookService webhookService;

    public AppRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 AppRunner started...");
        webhookService.process();
    }
}
