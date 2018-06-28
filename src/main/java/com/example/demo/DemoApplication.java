package com.example.demo;

import com.example.demo.model.Post;
import com.example.demo.model.PostComment;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication
@EnableJpaAuditing
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
                        Post post = new Post(p, false, null);
                        em.persist(post);

                        PostComment review = new PostComment("review", post);
                        em.persist(review);
                    });

            return null;
        });
    }
}

@Aspect
@Component
class EnableFilterAspect {

    private final String user;

    EnableFilterAspect(@Value("${user.name}") String user) {
        this.user = user;
    }


    @AfterReturning(
            pointcut = "bean(entityManagerFactory) && execution(* createEntityManager(..))",
            returning = "retVal")
    public void getSessionAfter(JoinPoint joinPoint, Object retVal) {
        if (retVal != null && EntityManager.class.isInstance(retVal)) {
            Session session = ((EntityManager) retVal).unwrap(Session.class);
            session.enableFilter("filter").setParameter("user", this.user);
        }
    }

}

@Component
class Auditor implements AuditorAware<String> {

    private final String user;

    Auditor(@Value("${user.name}") String user) {
        this.user = user;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(this.user);
    }
}