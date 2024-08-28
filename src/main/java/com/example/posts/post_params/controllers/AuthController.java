package com.example.posts.post_params.controllers;

import com.example.posts.post_params.domain.user.AuthDTO;
import com.example.posts.post_params.domain.user.LoginResponseDTO;
import com.example.posts.post_params.domain.user.RegisterDTO;
import com.example.posts.post_params.domain.user.User;
import com.example.posts.post_params.infra.security.TokenService;
import com.example.posts.post_params.respositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth =  this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.gerenateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByLogin(data.login()) != null ) return ResponseEntity.badRequest().build();

        String ecryptedPassword =  new BCryptPasswordEncoder().encode(data.password());

        this.userRepository.save(new User(data.login(), ecryptedPassword, data.role()));

        return ResponseEntity.ok().build();
    }

}
