package com.fynd.notes.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "notes_trash")
public class NotesTrash  extends Notes{

    public NotesTrash(Notes notes,Date deletedDate){
        this.id=notes.getId();
        this.name=notes.getName();
        this.description=notes.getDescription();
        this.createdBy=notes.getCreatedBy();
        this.createdDate=notes.getCreatedDate();
        this.modifiedDate=notes.getModifiedDate();
        this.deletedDate=deletedDate;
    }

    public NotesTrash() {
        super();
    }

    @Indexed(expireAfterSeconds = 60*2)
    private Date deletedDate;
}
