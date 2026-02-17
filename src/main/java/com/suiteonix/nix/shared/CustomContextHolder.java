package com.suiteonix.nix.shared;

import lombok.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CustomContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;
    private static ApplicationEventPublisher applicationEventPublisher;

    public CustomContextHolder(ApplicationEventPublisher publisher) {
        CustomContextHolder.applicationEventPublisher = publisher;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext context() {
        return context;
    }

    public static <T> T bean(Class<T> clazz) {
        return context.getBean(clazz);
    }


    public static void publishEvent(Object event) {
        if (applicationEventPublisher == null) {
            throw new IllegalStateException("ApplicationEventPublisher not initialized");
        }
        applicationEventPublisher.publishEvent(event);
    }

}