package com.example.demo.model;

import lombok.*;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ali akbar azizkhani
 */
@Entity
@Table(name = "post")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Filter(name = "filter",condition = "created_By=:user")
@FilterDef(name = "filter", defaultCondition = "deleted=0",parameters = {
        @ParamDef(name = "user",type = "string")
})
public class Post extends MappedAuditableBase {

    @Column(name = "title")
    String title;

    @Column(name = "deleted")
    boolean deleted = false;

    @OneToMany(mappedBy = "post")
    Set<PostComment> commentList = new HashSet<>();

}
