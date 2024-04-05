package org.example;

import org.example.beans.MessageService;
import org.example.context.ApplicationContext;
import org.example.context.ApplicationContextImpl;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContextImpl("org.example.beans");
        MessageService messageService = context.getBean("MessageService", MessageService.class);
        System.out.println(messageService.getMessage());
    }
}