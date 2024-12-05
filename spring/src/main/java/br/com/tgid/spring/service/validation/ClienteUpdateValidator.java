package br.com.tgid.spring.service.validation;

import br.com.tgid.spring.domains.Cliente;
import br.com.tgid.spring.domains.enums.TipoCliente;
import br.com.tgid.spring.gateways.dtos.ClienteDTO;
import br.com.tgid.spring.gateways.dtos.ClienteNewDTO;
import br.com.tgid.spring.gateways.repositories.ClienteRepository;
import br.com.tgid.spring.service.exception.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.com.tgid.spring.utils.ValidationRegistry.isValidCNPJ;
import static br.com.tgid.spring.utils.ValidationRegistry.isValidCPF;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteInsert, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repository.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty(); // se a lista retornar vazia, significa que não houve erro de validação
    }
}
