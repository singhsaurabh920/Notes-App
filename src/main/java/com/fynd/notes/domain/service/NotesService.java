package com.fynd.notes.domain.service;

import com.fynd.notes.domain.entity.Notes;
import com.fynd.notes.modal.NotesRequestDto;

import java.util.List;
import java.util.Optional;

public interface NotesService {

    Optional<Notes> getNote(String id);
    
    List<Notes> getNoteList(String createdBy);

    Notes saveNote(NotesRequestDto notesRequestDto,String createdBy);

    void softDeleteNote(String id);

    void permanentDeleteNote(String id);

    void emptyTrash(String createdBy);

    String restoreNote(String id);

    String archiveNote(String id);
}
