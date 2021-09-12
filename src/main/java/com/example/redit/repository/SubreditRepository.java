package com.example.redit.repository;

import com.example.redit.model.Subredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.Subredit;

import java.util.Optional;

@Repository
public interface SubreditRepository extends JpaRepository<Subredit, Long> {

	Optional<Subredit> findByname(String subredditName);
}