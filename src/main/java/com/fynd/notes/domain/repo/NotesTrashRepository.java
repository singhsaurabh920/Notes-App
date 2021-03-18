package com.fynd.notes.domain.repo;

import com.fynd.notes.domain.entity.NotesTrash;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesTrashRepository extends MongoRepository<NotesTrash,String> {

    void deleteByCreatedBy(String user);
}
