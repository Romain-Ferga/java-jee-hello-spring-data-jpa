package com.spring.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.data.jpa.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

}
