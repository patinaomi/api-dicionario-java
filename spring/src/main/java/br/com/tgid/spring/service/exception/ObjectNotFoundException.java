package br.com.tgid.spring.service.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class ObjectNotFoundException extends RuntimeException {

        public ObjectNotFoundException(String message) {
            super(message);
        }
}
