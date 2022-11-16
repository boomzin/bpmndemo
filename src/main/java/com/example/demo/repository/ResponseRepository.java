package com.example.demo.repository;

import com.example.demo.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Integer> {

    @Query("SELECT r FROM Response r WHERE r.uniqueId = :uniqueId")
    Optional<Response> getResponseByUniqueId(String uniqueId);
}
