package com.flproject.taskdvclient.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flproject.taskdvclient.dto.ClientDTO;
import com.flproject.taskdvclient.entities.Client;
import com.flproject.taskdvclient.repositories.ClientRepository;
import com.flproject.taskdvclient.services.exceptions.ClientNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;

	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> clients = repository.findAll(pageRequest);
		return clients.map(x -> new ClientDTO(x));
	}

	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client client = obj.orElseThrow(() -> new ClientNotFoundException("id "+id+" não encontrado"));
		return new ClientDTO(client);
	}
	
	
}
