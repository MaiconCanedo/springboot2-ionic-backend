package com.nelioalves.cursomc.core.validation;

import com.nelioalves.cursomc.domain.entity.enums.TipoCliente;
import com.nelioalves.cursomc.api.model.input.ClienteInput;
import com.nelioalves.cursomc.domain.repository.ClienteRepository;
import com.nelioalves.cursomc.api.controller.exceptions.FieldMessage;
import com.nelioalves.cursomc.core.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteInput> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert clienteInsert) {
    }

    @Override
    public boolean isValid(ClienteInput clienteInput, ConstraintValidatorContext context) {
        List<FieldMessage> messages = new ArrayList<>();
        //inclua os testes aqui, inserindo erros na lista
        if (clienteInput.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(clienteInput.getCpfOuCnpj())) {
            messages.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }
        if (clienteInput.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(clienteInput.getCpfOuCnpj())) {
            messages.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }
        if (repository.findByEmail(clienteInput.getEmail()) != null) {
            messages.add(new FieldMessage("email", "E-mail já existente"));
        }
        for (FieldMessage e : messages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return messages.isEmpty();
    }
}