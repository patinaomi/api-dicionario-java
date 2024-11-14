package br.com.tgid.spring.service.exception;

public class DataIntegrityException extends RuntimeException {

        public DataIntegrityException(String message) {
            super(message);
        }
}
