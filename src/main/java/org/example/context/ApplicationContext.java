package org.example.context;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> classType);
    <T> T getBean(String name, Class<T> classType);
    <T> Map<String, T> getBeansOfType(Class<T> classType);
}
