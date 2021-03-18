package com.fynd.notes.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "notes")
public class Notes {

    @Id
    protected String id;
    protected String name;
    protected String description;
    protected String createdBy;
    protected boolean archive;
    @CreatedDate
    protected Date createdDate;
    @LastModifiedDate
    protected Date modifiedDate;

}
