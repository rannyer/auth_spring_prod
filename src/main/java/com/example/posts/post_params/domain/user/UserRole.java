package com.example.posts.post_params.domain.user;

public enum UserRole {

    ADMIN("admin"),

    USER("user");



    private String role;

    UserRole(String role){
        this.role = role;
    }

    String getRole(){
        return this.role;
    }
}
