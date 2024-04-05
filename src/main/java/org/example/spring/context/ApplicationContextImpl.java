package org.example.spring.context;

import lombok.SneakyThrows;
import org.example.spring.annotation.Autowired;
import org.example.spring.annotation.Component;
import org.example.spring.annotation.Primary;
import org.example.spring.annotation.Qualifier;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContextImpl implements ApplicationContext {

    private final Map<String, Object> beanMap = new HashMap<>();

    public ApplicationContextImpl(String packageToScan) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(packageToScan));
        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);
        componentClasses
                .forEach(this::createBean);
        beanMap.entrySet()
                .forEach(this::injectDependency);
    }

    @SneakyThrows
    private void injectDependency(Map.Entry<String, Object> entry) {
        Object bean = entry.getValue();
        Class<?> beanType = bean.getClass();
        for (Field field : beanType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Object dependency = getBean(field, field.getType());
                field.setAccessible(true);
                field.set(bean, dependency);
            }
        }
    }

    private Object getBean(Field field, Class<?> beanType) {
        Qualifier qualifier = field.getAnnotation(Qualifier.class);
        if (qualifier != null) {
            return getBean(qualifier.value(), beanType);
        }
        return getBean(beanType);
    }

    @SneakyThrows
    private void createBean(Class<?> beanType) {
        Constructor<?> constructor = beanType.getDeclaredConstructor();
        Object bean = constructor.newInstance();

        String beanName = resolveBeanName(beanType);
        beanMap.put(beanName, bean);
    }

    private String resolveBeanName(Class<?> beanType) {
        Component component = beanType.getAnnotation(Component.class);
        return component.value().isEmpty()
                ? beanType.getSimpleName()
                : component.value();
    }

    @Override
    public <T> T getBean(Class<T> classType) {
        var beansOfType = getBeansOfType(classType).values();
        if (beansOfType.size() > 1) {
            var primaryBeans = beansOfType.stream()
                    .filter(bean -> bean.getClass().isAnnotationPresent(Primary.class))
                    .toList();
            if (primaryBeans.size() > 1) {
                throw new RuntimeException("No unique bean");
            }
            return primaryBeans.get(0);
        }
        return beansOfType.stream().findAny().orElseThrow();
    }

    @Override
    public <T> T getBean(String name, Class<T> classType) {
        return classType.cast(beanMap.get(name));
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> classType) {
        return beanMap.entrySet()
                .stream()
                .filter(entry -> classType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> classType.cast(entry.getValue())));
    }
}
