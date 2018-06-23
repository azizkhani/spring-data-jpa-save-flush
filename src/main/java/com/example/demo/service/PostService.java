package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ali akbar azizkhani
 */
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post save(Post p) {
        Post post = postRepository.saveAndFlush(p);
        post.getCommentList().forEach(pc -> System.out.println(pc.getReview()));
        return post;
    }
}
