package com.flproject.taskdvclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flproject.taskdvclient.dto.ClientDTO;
import com.flproject.taskdvclient.entities.Client;
import com.flproject.taskdvclient.repositories.ClientRepository;
import com.flproject.taskdvclient.services.exceptions.ClientNotFoundException;

import jakarta.persistence.EntityNotFoundException;


@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(Pageable pageable) {
		Page<Client> clients = repository.findAll(pageable);
		return clients.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client client = obj.orElseThrow(() -> new ClientNotFoundException("id "+id+" não encontrado"));
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO addClient(ClientDTO clientDto) {
		Client c = new Client();
		atribuirDtoParaEntity(clientDto,c);
		c = repository.save(c);
		return new ClientDTO(c);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDto) {
		try {
			Client c = repository.getReferenceById(id);
			atribuirDtoParaEntity(clientDto,c);
			c = repository.save(c);
			return new ClientDTO(c);
		}
		catch(EntityNotFoundException e) {
			throw new ClientNotFoundException("id não encontrado");
		}
		
	}
	
	@Transactional
	public void delete(Long id) {
		try{
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ClientNotFoundException("id não encontrado");
		}
	}

	private void atribuirDtoParaEntity(ClientDTO clientDto,Client c) {
		c.setName(clientDto.getName());
		c.setCpf(clientDto.getCpf());
		c.setIncome(clientDto.getIncome());
		c.setBirthDate(clientDto.getBirthDate());
		c.setChildren(clientDto.getChildren());
	}
}
