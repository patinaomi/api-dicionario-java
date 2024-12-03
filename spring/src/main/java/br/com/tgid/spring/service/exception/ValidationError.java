package br.com.tgid.spring.service.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();
    //Evita Retornar Apenas o Primeiro Erro
    //Permite que o cliente saiba todos os erros de uma vez, sem precisar corrigir um por um, por isso é uma lista!

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    public ValidationError(Integer status, String message, Long timeStamp) {
        super(status, message, timeStamp);
    }
}
