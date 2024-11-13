package br.com.tgid.spring.service.exception;

public class ObjectNotFoundException extends RuntimeException {

        public ObjectNotFoundException(String message) {
            super(message);
        }
}
