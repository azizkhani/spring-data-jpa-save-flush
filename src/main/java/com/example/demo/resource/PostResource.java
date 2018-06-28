package com.example.demo.resource;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author ali akbar azizkhani
 */
@RestController()
@RequestMapping("/post")
@Log4j2
public class PostResource {

    private final PostRepository postRepository;

    private final PostService postService;

    public PostResource(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }


    @GetMapping("/all")
    public List<Post> list() {
        List<Post> posts = postRepository.findAll();
        posts.forEach(p -> {
            log.info("post is " + ToStringBuilder.reflectionToString(p));
        });
        return posts;
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable Long id) {
        return postRepository.findById(id);
    }

    @PutMapping()
    public Post update(@RequestBody Post post) {
        Post persistPost = postRepository.save(post);
        //postRepository.findById(post.getId());
        persistPost.getCommentList().forEach(pc -> System.out.println(pc.getReview()));
        return persistPost;
    }

    @PutMapping("/service")
    public Post updateUsingService(@RequestBody Post post) {
        Post persistPost = postService.save(post);
        return persistPost;
    }

    @PostMapping()
    public Post insert(@RequestBody Post post) {
        return postRepository.save(post);
    }
}
