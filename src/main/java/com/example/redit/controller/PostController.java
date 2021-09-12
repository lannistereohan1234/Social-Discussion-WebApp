package com.example.redit.controller;

import java.util.List;
import com.example.redit.dto.PostRequest;
import com.example.redit.dto.PostResponse;
import com.example.redit.service.PostService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.redit.dto.PostRequest;
import com.example.redit.dto.PostResponse;
import com.example.redit.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
@Slf4j
public class PostController {
	@Autowired
	private PostService postService;

	@PostMapping
	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		postService.save(postRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return status(HttpStatus.OK).body(postService.getPost(id));
	}

	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubredit(Long id) {
		return status(HttpStatus.OK).body(postService.getPostsBySubredit(id));
	}

	@GetMapping("by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
		return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
	}
}