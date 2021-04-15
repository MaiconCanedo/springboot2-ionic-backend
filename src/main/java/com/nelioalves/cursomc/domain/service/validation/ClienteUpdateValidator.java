package com.nelioalves.cursomc.domain.service.validation;

import com.nelioalves.cursomc.domain.entity.Cliente;
import com.nelioalves.cursomc.api.model.ClienteModel;
import com.nelioalves.cursomc.domain.repository.ClienteRepository;
import com.nelioalves.cursomc.api.controller.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteModel> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {
    }

    @Override
    public boolean isValid(ClienteModel clienteModel, ConstraintValidatorContext context) {
        List<FieldMessage> messages = new ArrayList<>();

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.valueOf(map.get("id"));

        Cliente aux = repository.findByEmail(clienteModel.getEmail());
        if (aux.getEmail() != null && !aux.getId().equals(uriId)) {
            messages.add(new FieldMessage("email", "E-mail já existente"));
        }

        for (FieldMessage e : messages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return messages.isEmpty();
    }
}