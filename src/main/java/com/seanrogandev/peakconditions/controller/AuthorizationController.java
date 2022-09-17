package com.seanrogandev.peakconditions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthorizationController {
    //todo build this whole thing
    @GetMapping("/authorize")
    public ResponseEntity<?> authorize(String userName, String password) {

        return ResponseEntity.ok().build();
    }

}
