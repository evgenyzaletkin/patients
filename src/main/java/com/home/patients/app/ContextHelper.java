package com.home.patients.app;

import com.home.patients.app.configs.Config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ContextHelper {
    private static volatile ApplicationContext applicationContext;

    public static void initContext() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        ContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }
}
