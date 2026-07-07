package com.daosicoder.todoapp.core.startup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        String port = env.getProperty("server.port", "8080");

        String RESET = "\u001B[0m";
        String BOLD = "\u001B[1m";
        String GREEN = "\u001B[32m";
        String CYAN = "\u001B[36m";
        String YELLOW = "\u001B[33m";
        String PURPLE = "\u001B[35m";

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println(PURPLE + "╭──────────────────────────────────────────────────────────╮" + RESET);
        printLine(PURPLE, BOLD + CYAN + " ☯ TODO APP - run successfully" + RESET, 58);
        System.out.println(PURPLE + "├──────────────────────────────────────────────────────────┤" + RESET);
        printLine(PURPLE, " Environment  : " + CYAN + "Local (Docker PostgreSQL)" + RESET, 58);
        printLine(PURPLE, " Port : " + YELLOW + "http://localhost:" + port + RESET, 58);
        printLine(PURPLE, " run at   : " + timestamp + RESET, 58);
        System.out.println(PURPLE + "╰──────────────────────────────────────────────────────────╯" + RESET);
    }

    private void printLine(String borderColor, String content, int width) {
        String RESET = "\u001B[0m";
        String visibleContent = content.replaceAll("\u001B\\[[;\\d]*m", "");
        int padding = width - visibleContent.length();

        System.out.print(borderColor + "│" + RESET);
        System.out.print(content);
        System.out.print(" ".repeat(Math.max(0, padding)));
        System.out.println(borderColor + "│" + RESET);
    }
}