package com.example.redit.repository;

import java.util.List;

import com.example.redit.model.Post;
import com.example.redit.model.Subredit;
import com.example.redit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.redit.model.Post;
import com.example.redit.model.Subredit;
import com.example.redit.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubredit(Subredit subredit);

    List<Post> findByUser(User user);
}