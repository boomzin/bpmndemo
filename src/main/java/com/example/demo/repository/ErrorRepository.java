package com.example.demo.repository;

import com.example.demo.model.ErrorFetching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ErrorRepository extends JpaRepository<ErrorFetching, Integer> {

    @Query("SELECT e FROM ErrorFetching e WHERE e.uniqueId = :uniqueId")
    Optional<ErrorFetching> getErrorByUniqueId(String uniqueId);
}
