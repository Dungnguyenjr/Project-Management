package com.practice.controller;

import com.practice.dto.AuthLoginDTO;
import com.practice.entity.Account;
import com.practice.configuration.JwtProvider;
import com.practice.req.LoginReq;
import com.practice.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@Tag(name = "Auth Controller")
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin (@RequestBody LoginReq loginReq) {
        try {
            Account account = accountService.findByUsername(loginReq.getUsername());
            Authentication authen = authenticate(account.getUsername(), loginReq.getPassword());
            String jwt = jwtProvider.genToken(authen);
            String message = "Login successfully";

            AuthLoginDTO authLoginDTO = new AuthLoginDTO(message, jwt, account.getRole());
            return new ResponseEntity<>(authLoginDTO, HttpStatus.OK);
        } catch (Exception e){
            AuthLoginDTO authLoginDTO = new AuthLoginDTO("Wrong username or password!", null, null);

            return new ResponseEntity<>(authLoginDTO, HttpStatus.BAD_REQUEST);
        }
    }

    private Authentication authenticate(String username , String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null){
            throw new BadCredentialsException("Invalid username......");
        }
        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password......");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
