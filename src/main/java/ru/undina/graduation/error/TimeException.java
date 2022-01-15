package ru.undina.graduation.error;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;
public class TimeException extends AppException{


    public TimeException( String message) {

        super(HttpStatus.LOCKED, message, ErrorAttributeOptions.of(MESSAGE));
    }
}
