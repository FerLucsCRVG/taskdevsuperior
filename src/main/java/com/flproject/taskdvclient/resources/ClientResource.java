package com.flproject.taskdvclient.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flproject.taskdvclient.dto.ClientDTO;
import com.flproject.taskdvclient.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(
			@RequestParam(value = "page",defaultValue="0") Integer page,
			@RequestParam(value = "linesPerPage",defaultValue="3") Integer linesPerPage,
			@RequestParam(value = "direction",defaultValue="ASC") String direction,
			@RequestParam(value = "orderBy",defaultValue="id") String orderBy
			){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		Page<ClientDTO> clientDto = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(clientDto);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
		ClientDTO clientDto = service.findById(id);
		return ResponseEntity.ok().body(clientDto);
	}
}
