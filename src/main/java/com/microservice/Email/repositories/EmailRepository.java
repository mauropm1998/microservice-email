package com.microservice.Email.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.Email.models.Email;

public interface EmailRepository extends JpaRepository<Email, UUID> {

}
