package com.example.redit.service;

import com.example.redit.controller.AuthController;
import com.example.redit.dto.CommentsDto;
import com.example.redit.exceptions.PostNotFoundException;
import com.example.redit.mapper.CommentMapper;
import com.example.redit.model.Comment;
import com.example.redit.model.NotificationEmail;
import com.example.redit.model.Post;
import com.example.redit.model.User;
import com.example.redit.repository.CommentRepository;
import com.example.redit.repository.PostRepository;
import com.example.redit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private MailService mailService;

    public void save(CommentsDto commentsDto){
        Post post=postRepository.findById(commentsDto.getId()).orElseThrow(()->new PostNotFoundException(commentsDto.getId().toString()));
        Comment comment = commentMapper.map(commentsDto,post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message=mailContentBuilder.build(post.getUser().getUsername()+"posted a comment on"+ POST_URL);
        sendCommentNotification(message,post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.Sendmail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName){
        User user=userRepository.findByUsername(userName)
                .orElseThrow(()->new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }
}
