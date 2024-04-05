package org.example.spring.beans;

import lombok.Data;
import org.example.spring.annotation.Component;

@Component("customMessageService")
@Data
public class MessageService {
    private String message = "Default message";

    public String startLog() {
        return "Start log";
    }

    public String endLog() {
        return "End log";
    }
}
