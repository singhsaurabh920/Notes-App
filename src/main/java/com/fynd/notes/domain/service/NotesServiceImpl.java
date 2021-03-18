package com.fynd.notes.domain.service;

import com.fynd.notes.domain.entity.Notes;
import com.fynd.notes.domain.entity.NotesTrash;
import com.fynd.notes.domain.repo.NotesRepository;
import com.fynd.notes.domain.repo.NotesTrashRepository;
import com.fynd.notes.modal.NotesRequestDto;
import com.mongodb.client.result.UpdateResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Service
public class NotesServiceImpl implements NotesService{

    private final MongoTemplate mongoTemplate;
    private final NotesRepository notesRepository;
    private final NotesTrashRepository notesTrashRepository;

    @Override
    public Optional<Notes> getNote(String id) {
        return notesRepository.findById(id);
    }

    @Override
    public List<Notes> getNoteList(String createdBy) {
        return notesRepository.findByCreatedByAndArchive(createdBy,false);
    }

    @Override
    public Notes saveNote(NotesRequestDto notesRequestDto,String createdBy) {
        Notes notes=new Notes();
        notes.setName(notesRequestDto.getName());
        notes.setDescription(notesRequestDto.getDescription());
        notes.setCreatedBy(createdBy);
        return notesRepository.save(notes);
    }

    @Override
    public void softDeleteNote(String id) {
        Notes notes=notesRepository.findById(id).get();
        log.info("Moved to trash {}",notes);
        NotesTrash notesTrash=new NotesTrash(notes,new Date());
        mongoTemplate.save(notesTrash);
        notesRepository.delete(notes);
    }

    @Override
    public void permanentDeleteNote(String id) {
        notesTrashRepository.deleteById(id);
    }

    @Override
    public void emptyTrash(String createdBy) {
        notesTrashRepository.deleteByCreatedBy(createdBy);
    }

    @Override
    public String restoreNote(String id) {
        Optional<NotesTrash> notesTrashOptional=notesTrashRepository.findById(id);
        if(notesTrashOptional.isPresent()){
            NotesTrash notesTrash=notesTrashOptional.get();
            notesTrash.setDeletedDate(null);
            mongoTemplate.save(notesTrash,"notes");
            notesTrashRepository.delete(notesTrash);
            return "Data Restored";
        }else {
            return "No data found to Restore";
        }
    }

    @Override
    public String archiveNote(String id) {
        Query query=Query.query(Criteria.where("id").is(id));
        Update update=new Update();
        update.set("archive",true);
        UpdateResult updateResult=mongoTemplate.updateFirst(query,update,Notes.class);
        log.info(updateResult.toString());
        return "Marked archive";
    }
}
