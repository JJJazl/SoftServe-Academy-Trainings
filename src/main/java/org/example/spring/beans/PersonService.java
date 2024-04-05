package org.example.spring.beans;

import lombok.Data;
import org.example.spring.annotation.Autowired;
import org.example.spring.annotation.Component;
import org.example.spring.annotation.Qualifier;

@Component
@Data
public class PersonService {

    @Autowired
    @Qualifier("DefaultLoggerService")
    private LoggerService loggerService;

}

