package org.example.context;

import org.example.annotation.Component;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Map;
import java.util.Set;

public class ApplicationContextImpl implements ApplicationContext {
    
    public ApplicationContextImpl(String packageToScan) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageToScan));
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        // todo: Create instance of component class and save it
    }

    @Override
    public <T> T getBean(Class<T> classType) {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> classType) {
        return null;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> classType) {
        return null;
    }
}
