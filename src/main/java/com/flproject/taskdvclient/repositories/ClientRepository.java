package com.flproject.taskdvclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flproject.taskdvclient.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
}
