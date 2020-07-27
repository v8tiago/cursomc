package com.tiagoarruda.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagoarruda.cursomc.domain.Pedido;
import com.tiagoarruda.cursomc.exceptions.ObjectNotFoundException;
import com.tiagoarruda.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id)
	{
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: "+id+", Tipo:"+Pedido.class.getName()));
	}
}