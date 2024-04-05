package org.example.beans;

import lombok.Data;
import org.example.annotation.Component;

@Component
@Data
public class MessageService {
    private String message = "Default message";
}
