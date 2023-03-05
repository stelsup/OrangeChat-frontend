package com.maximus.chatclientjavafx;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder {

    // Контекст Spring
    private static ApplicationContext ctx;

    public ApplicationContextHolder(ApplicationContext applicationContext) {
        ApplicationContextHolder.ctx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
