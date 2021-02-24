package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE userid = #{user_id}")
    List<Note> getAll(Integer user_id);

    @Select("SELECT * FROM NOTES WHERE noteid = #{note_id} AND userid = #{user_id}")
    Note getNote(Integer note_id, Integer user_id);

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid)" +
            "VALUES(#{note_title},#{note_description},#{user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "note_id")
    Integer insertNote(Note note);

    @Update("UPDATE NOTES " +
            "SET notetitle = #{note_title}, notedescription = #{note_description}" +
            "WHERE noteid = #{note_id} AND userid = #{user_id}")
    Integer updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{note_id} AND userid = #{user_id}")
    Integer deleteNote(Integer note_id, Integer user_id);
}
