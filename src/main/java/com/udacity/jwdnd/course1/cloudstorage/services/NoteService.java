package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private final UserService userService;
    private final NoteMapper noteMapper;

    public NoteService(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    public List<Note> findAll(String username){
        User user = userService.findUserByUsername(username);
        return noteMapper.getAll(user.getUser_id());
    }

    public Note findByNoteId(Integer note_id,String username){
        User user = userService.findUserByUsername(username);
        return noteMapper.getNote(note_id,user.getUser_id());
    }

    public Integer saveNote(Note note, String username){
        User user = userService.findUserByUsername(username);
        return noteMapper.insertNote(new Note(
                null,
                note.getNote_title(),
                note.getNote_description(),
                user.getUser_id()
        ));
    }
    public Integer updateNote(Note note, String username){
        User user = userService.findUserByUsername(username);
        return noteMapper.updateNote(new Note(
                note.getNote_id(),
                note.getNote_title(),
                note.getNote_description(),
                user.getUser_id()
        ));
    }
    public Integer deleteNote(Integer note_id, String username){
        User user = userService.findUserByUsername(username);
        return noteMapper.deleteNote(note_id,user.getUser_id());
    }
}
