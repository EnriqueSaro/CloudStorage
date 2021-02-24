package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/note")
public class NoteController {
    NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }
    @PostMapping(path = "/new-note")
    public String createNote(@ModelAttribute Note note, Authentication auth, RedirectAttributes redirAttr){
        String note_error = null;
        Integer rows_modified = null;

        if (note.getNote_id() == null)
            rows_modified = noteService.saveNote(note, auth.getName());
        else
            rows_modified = noteService.updateNote(note,auth.getName());

        if (rows_modified < 0)
            note_error = "Somethimg happened, try again";

        if (note_error == null)
            redirAttr.addFlashAttribute("noteError",note_error);

        redirAttr.addFlashAttribute("isNotesActive",true);

        return "redirect:/home";
    }
    @GetMapping(path = "/delete-note/{note_id}")
    public String deleteNote(@PathVariable Integer note_id, Authentication auth, RedirectAttributes redirAttr){
        String note_error = null;
        Integer rows_deleted = noteService.deleteNote(note_id, auth.getName());
        if (rows_deleted < 0)
            note_error = "Somethimg happened, try again";

        redirAttr.addFlashAttribute("isNotesActive",true);
        return "redirect:/home";
    }

    @GetMapping(path = "/{note_id}")
    public String findNote(@PathVariable Integer note_id,
                           Authentication auth,
                           RedirectAttributes redir_Attr){
        String noteError = null;
        Note note = noteService.findByNoteId(note_id, auth.getName());
        redir_Attr.addFlashAttribute("Note", note);
        return "redirect:/home";
    }
}
