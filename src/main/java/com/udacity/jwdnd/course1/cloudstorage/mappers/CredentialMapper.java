package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{user_id}")
    List<Credential> getAll(Integer user_id);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credential_id} AND userid = #{user_id}")
    Credential getCredential(Integer credential_id, Integer user_id);

    @Insert("INSERT INTO CREDENTIALS(url,username,key,password,userid)" +
            "VALUES(#{url},#{username},#{key},#{password},#{user_id})")
    @Options(useGeneratedKeys = true, keyProperty = "credential_id")
    Integer insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS " +
            "SET url = #{url}, username = #{username}, key = #{key}, password = #{password}" +
            "WHERE credentialid = #{credential_id} AND userid = #{user_id}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credential_id} AND userid = #{user_id}")
    Integer deleteCredential(Integer credential_id, Integer user_id);
}
