package com.fynd.notes.domain.repo;

import com.fynd.notes.domain.entity.Notes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends MongoRepository<Notes,String> {

    List<Notes> findByCreatedBy(String createdBy);

    List<Notes> findByCreatedByAndArchive(String createdBy, boolean b);
}
