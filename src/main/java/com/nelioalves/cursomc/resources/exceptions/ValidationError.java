package com.nelioalves.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError(Integer status, String message, Long timeStamp) {
        super(status, message, timeStamp);
    }

    public List<FieldMessage> getErros() {
        return erros;
    }

    public void addError(FieldMessage fieldMessage) {
        this.erros.add(fieldMessage);
    }

    public void addError(String fieldName, String message) {
        this.erros.add(new FieldMessage(fieldName, message));
    }
}
