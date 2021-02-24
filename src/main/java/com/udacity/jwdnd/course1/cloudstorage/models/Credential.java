package com.udacity.jwdnd.course1.cloudstorage.models;

public class Credential {
    private Integer credential_id;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer user_id;

    public Credential(Integer credential_id, String url, String username, String key, String password, Integer user_id) {
        this.credential_id = credential_id;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.user_id = user_id;
    }

    public Integer getCredential_id() {
        return credential_id;
    }

    public void setCredential_id(Integer credential_id) {
        this.credential_id = credential_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


}
