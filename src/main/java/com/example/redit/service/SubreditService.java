package com.example.redit.service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.redit.mapper.SubreditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.redit.controller.SubreditController;
import com.example.redit.dto.SubreditDto;
import com.example.redit.exceptions.SpringRedditException;
import com.example.redit.model.Subredit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubreditService {
	@Autowired
	private com.example.redit.repository.SubreditRepository subreditRepository;

	@Autowired
	private SubreditMapper subreditMapper;

	@Transactional
	public SubreditDto save(SubreditDto subreditDto) {
		Subredit saved = subreditRepository.save(subreditMapper.mapDtoToSubredit(subreditDto));
		subreditDto.setId(saved.getId());
		return subreditDto;
	}

	@Transactional(readOnly=true)
	public List<SubreditDto> getAll() {
		return subreditRepository.findAll()
				.stream()
				.map(subreditMapper::mapSubreditToDto)
				.collect(Collectors.toList());
	}

	public SubreditDto getSubredit(Long id) {
		Subredit subredit = subreditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
		return subreditMapper.mapSubreditToDto(subredit);
	}

}
