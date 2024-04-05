package org.example.spring.beans;

import org.example.spring.annotation.Autowired;
import org.example.spring.annotation.Component;
import org.example.spring.annotation.Primary;

@Primary
@Component("prettyLoggerService")
public class PrettyLoggerService implements LoggerService {

    @Autowired
    private MessageService messageService;

    @Override
    public void printLog(String message) {
        System.out.printf("%s\n ***%s***\n%s%n",
                messageService.startLog(),
                message,
                messageService.endLog());
    }
}
