package ru.undina.topjava2.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

public class TimeException extends Exception{


    public TimeException( String message) {
        super(message);
    }
}
