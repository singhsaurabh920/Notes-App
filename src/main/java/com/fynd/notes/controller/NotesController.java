package com.fynd.notes.controller;

import com.fynd.notes.domain.entity.Notes;
import com.fynd.notes.domain.service.NotesService;
import com.fynd.notes.modal.NotesRequestDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@RestController
public class NotesController {

    private final NotesService notesService;

    @RequestMapping(value = "/note/{id}",method = RequestMethod.GET)
    public ResponseEntity<Notes> getNote(@PathVariable String id, Authentication authentication){
        String user=authentication.getName();
        log.info("User ==>> {}",user);
        Optional<Notes> notes=notesService.getNote(id);
        if(notes.isPresent()){
            return ResponseEntity.ok(notes.get());
        }else {
            return ResponseEntity.noContent().varyBy("No data found").build();
        }
    }

    @RequestMapping(value = "/note/list",method = RequestMethod.GET)
    public List<Notes> getNoteList(Authentication authentication){
        String createdBy=authentication.getName();
        log.info("User ==>> {}",createdBy);
        return notesService.getNoteList(createdBy);
    }

    @RequestMapping(value = "/note",method = RequestMethod.POST)
    public Notes saveNote(@RequestBody NotesRequestDto notesRequestDto, Authentication authentication){
        String createdBy=authentication.getName();
        log.info("User ==>> {}",createdBy);
        log.info(notesRequestDto.toString());
        return notesService.saveNote(notesRequestDto,createdBy);
    }

    @RequestMapping(value = "/note/{id}",method = RequestMethod.DELETE)
    public String softDeleteNote(@PathVariable String id, Authentication authentication){
        String user=authentication.getName();
        log.info("User ==>> {}",user);
        notesService.softDeleteNote(id);
        return "Moved to Bin";
    }

    @RequestMapping(value = "/note/permanent/delete/{id}",method = RequestMethod.DELETE)
    public String hardDeleteNote(@PathVariable String id, Authentication authentication){
        notesService.permanentDeleteNote(id);
        return "Permanently Deleted";
    }

    @RequestMapping(value = "/note/permanent/delete",method = RequestMethod.DELETE)
    public String hardDeleteNote(Authentication authentication){
        String user=authentication.getName();
        notesService.emptyTrash(user);
        return "Empty trash";
    }

    @RequestMapping(value = "/note/restore/{id}",method = RequestMethod.PUT)
    public String restoreNote(@PathVariable String id, Authentication authentication){
        String user=authentication.getName();
        return notesService.restoreNote(id);
    }

    @RequestMapping(value = "/note/archive/{id}",method = RequestMethod.PUT)
    public String archiveNote(@PathVariable String id, Authentication authentication){
        String user=authentication.getName();
        return notesService.archiveNote(id);
    }
}
