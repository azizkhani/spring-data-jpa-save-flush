package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author ali akbar azizkhani
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class MappedAuditableBase {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    @JsonProperty
    private Date createdDate;

    @LastModifiedDate
    @JsonProperty
    private Date updatedDate;

    @CreatedBy
    @JsonProperty
    private String createdBy;

    @LastModifiedBy
    @JsonProperty
    private String updatedBy;

}
