package org.example.spring;

import org.example.spring.beans.PersonService;
import org.example.spring.context.ApplicationContext;
import org.example.spring.context.ApplicationContextImpl;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl("org.example.beans");
        PersonService personService = context.getBean(PersonService.class);
        personService.getLoggerService().printLog("YEA!");
    }
}