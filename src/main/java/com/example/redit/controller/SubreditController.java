package com.example.redit.controller;

import java.util.List;

import com.example.redit.dto.SubreditDto;
import com.example.redit.service.SubreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.redit.dto.SubreditDto;
import com.example.redit.service.SubreditService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/subredit")
@AllArgsConstructor
@Slf4j
public class SubreditController {
	@Autowired
	private SubreditService subredditService;
	
	@PostMapping
	public ResponseEntity<SubreditDto> createSubreddit(@RequestBody SubreditDto subredditDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));		
	}
	
	@GetMapping
	public ResponseEntity<List<SubreditDto>> getAllSubreddit() {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<SubreditDto> getSubreddit(@PathVariable Long id) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(subredditService.getSubredit(id));
	}

}
