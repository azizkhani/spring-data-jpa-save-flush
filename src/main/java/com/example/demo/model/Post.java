package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ali akbar azizkhani
 */
@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "title")
    String title;

    @OneToMany(mappedBy = "post")
    Set<PostComment> commentList=new HashSet<>();

}
