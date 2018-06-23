package com.example.demo;

import com.example.demo.model.Post;
import com.example.demo.model.PostComment;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Component
class JpaApplicationWrite implements ApplicationRunner {

    private final EntityManager em;
    private final TransactionTemplate transactionTemplate;

    JpaApplicationWrite(EntityManager em, TransactionTemplate transactionTemplate) {
        this.em = em;
        this.transactionTemplate = transactionTemplate;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        transactionTemplate.execute(status -> {
            Stream.of("simple test;why dose;not work;transaction;readonly ".split(";"))
                    .forEach(p -> {
                        Post post = new Post(null, p, null);
                        em.persist(post);

                        PostComment review = new PostComment(null, "review", post);
                        em.persist(review);
                    });

            return null;
        });
    }
}