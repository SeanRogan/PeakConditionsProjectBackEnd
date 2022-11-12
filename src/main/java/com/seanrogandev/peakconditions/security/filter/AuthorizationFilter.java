package com.seanrogandev.peakconditions.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //if a request comes in from the login endpoint or the refresh token endpoint..
        if(request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
            //continue application/go to the next filter in the chain(authentication)
            filterChain.doFilter(request, response);
        } else {
            //otherwise check for an authorization token header
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            //if there is a bearer token header
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    log.info("validating authorization bearer token");
                    //save token as a string
                    String token = authorizationHeader.substring("Bearer ".length());
                    //declare the algo to verify the token, using the client secret code as an argument. 'secret' used here for demo
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    //declare a verifier object to use the algorithm
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    //verify the token and save the decoded version
                    DecodedJWT decodedJWT = verifier.verify(token);
                    //get the username from the decoded jwt
                    String username = decodedJWT.getSubject();
                    //get the user roles from the decoded jwt and save them as an array of strings representing the type of role
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    //declare a collection to store the roles as simpleGrantedAuthority(SGA) objects.
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    //loop through the roles and create/add SGA to collection
                     Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
                    //create an authentication token from the user details and pass it to the security context for authentication
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //move to the next filter in the chain
                    filterChain.doFilter(request, response);
                }catch (Exception exception) {
                    log.error("Error logging in: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
}}
