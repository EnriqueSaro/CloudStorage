package com.udacity.jwdnd.course1.cloudstorage.models;


public class File {
    private Integer file_id;
    private String filename;
    private String content_type;
    private Long file_size;
    private Integer user_id;
    private byte[] file_data;


    public File(Integer file_id, String filename, String content_type, Long file_size, Integer user_id, byte[] file_data) {
        this.file_id = file_id;
        this.filename = filename;
        this.content_type = content_type;
        this.file_size = file_size;
        this.user_id = user_id;
        this.file_data = file_data;
    }

    public Integer getFile_id() {
        return file_id;
    }

    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public Long getFile_size() {
        return file_size;
    }

    public void setFile_size(Long file_size) {
        this.file_size = file_size;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public byte[] getFile_data() {
        return file_data;
    }

    public void setFile_data(byte[] file_data) {
        this.file_data = file_data;
    }
}
