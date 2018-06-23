package com.example.demo.repository;

import com.example.demo.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ali akbar azizkhani
 */
@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
}
