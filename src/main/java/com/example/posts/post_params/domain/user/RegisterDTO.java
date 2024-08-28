package com.example.posts.post_params.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
