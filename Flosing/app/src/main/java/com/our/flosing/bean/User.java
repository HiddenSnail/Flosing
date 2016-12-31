package com.our.flosing.bean;

/**
 * Created by huangrui on 2016/12/27.
 */

public class User {
    private String id;
    private String username;
    private String email;
    private String password;

    public String getId() { return this.id; }
    public void setId(String uid) { this.id = id; }

    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return this.username; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
}
