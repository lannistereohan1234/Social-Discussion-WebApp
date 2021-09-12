package com.example.redit.service;

import java.util.List;
import com.example.redit.dto.PostRequest;
import com.example.redit.dto.PostResponse;
import com.example.redit.exceptions.PostNotFoundException;
import com.example.redit.exceptions.SubreditNotFoundException;
import com.example.redit.mapper.PostMapper;
import com.example.redit.model.Post;
import com.example.redit.model.Subredit;
import com.example.redit.model.User;
import com.example.redit.repository.PostRepository;
import com.example.redit.repository.SubreditRepository;
import com.example.redit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.toList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class PostService {
	@Autowired
	private SubreditRepository subreditRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private PostMapper postMapper;

	public void save(PostRequest postRequest){
		Subredit subredit = subreditRepository.findByname(postRequest.getSubreditName())
				.orElseThrow(()-> new SubreditNotFoundException(postRequest.getSubreditName()));

		postRepository.save(postMapper.map(postRequest,subredit,authService.getCurrentUser()));
	}

	@Transactional(readOnly=true)
	public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
   	}

	@Transactional(readOnly=true)
	public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());

	}

	@Transactional(readOnly=true)
	public List<PostResponse> getPostsBySubredit(Long id) {
        Subredit subredit = subreditRepository.findById(id)
                .orElseThrow(() -> new SubreditNotFoundException(id.toString()));
        List<Post> posts = postRepository.findAllBySubredit(subredit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
	}
	
	@Transactional(readOnly=true)
	public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
	}
}
