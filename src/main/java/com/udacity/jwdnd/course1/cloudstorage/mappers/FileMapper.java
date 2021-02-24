package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAll(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userid}")
    File getFileByFilename(String filename, Integer userid);

    @Select("SELECT * FROM FILES WHERE fileid = #{file_id} AND userid = #{userid}")
    File getFileByFileId(Integer file_id, Integer userid);

    @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata)" +
                    "VALUES(#{filename},#{content_type},#{file_size},#{user_id},#{file_data})")
    @Options(useGeneratedKeys = true, keyProperty = "file_id")
    Integer insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{file_id}")
    Integer deleteFile(Integer file_id);
}
