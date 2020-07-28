package com.tiagoarruda.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.tiagoarruda.cursomc.domain.Cliente;
import com.tiagoarruda.cursomc.domain.enums.TipoCliente;
import com.tiagoarruda.cursomc.dto.ClienteNewDTO;
import com.tiagoarruda.cursomc.repositories.ClienteRepository;
import com.tiagoarruda.cursomc.resources.exception.FieldsMessage;
import com.tiagoarruda.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldsMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
			list.add(new FieldsMessage("cpfOuCnpj","CPF inválido"));
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()))
			list.add(new FieldsMessage("cpfOuCnpj","CNPJ inválido"));
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null)
			list.add(new FieldsMessage("email","Email já existente"));
		
		for (FieldsMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
