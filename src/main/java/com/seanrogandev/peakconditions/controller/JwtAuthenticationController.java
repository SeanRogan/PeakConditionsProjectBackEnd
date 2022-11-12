package com.seanrogandev.peakconditions.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seanrogandev.peakconditions.dao.Member;
import com.seanrogandev.peakconditions.dao.Role;
import com.seanrogandev.peakconditions.service.impl.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api")
@Slf4j
public class JwtAuthenticationController {
    @Autowired
    JwtAuthenticationController(MemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    private final MemberServiceImpl memberService;

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get the authorization headers from the request, if there is a refresh token it would be there
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        //if the authorization header isnt null and is a bearer token, attempt to authenticate the token
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            try{
                //save the provided token as a string
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                //instantiate the algorithm that will decode the JWT
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                //instantiate the jwtVerifier class which will do the work of decoding the Jwt
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                //decode the jwt with the verifier
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                //extract the username from the jwt and save as a string for looking up the user in the database by their username
                String username = decodedJWT.getSubject();
                //find the member/user in the database
                Member member = memberService.getMember(username);
                log.info("refresh_token provided creating new token pair for " + username);
                //create a new access token
                String access_token = JWT.create()
                        .withSubject(member.getUserName())
                        //token expires in 10 minutes. change the 10 below to change the expiration time.
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", member.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                //init a map to hold the tokens
                Map<String, String> tokens = new HashMap<>();
                //put the new access token and provided refresh token into the map
                tokens.put("access_token" , access_token);
                tokens.put("refresh_token", refresh_token);
                //set response content type to json
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
        }

    }


