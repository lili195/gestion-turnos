package com.TurnsManagement.keycloak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/token")
@CrossOrigin
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello-1")
    public String helloAdmin(Authentication authentication) {
        String username = getUsernameFromAuthentication(authentication);
        String email = getEmailFromAuthentication(authentication);

        // Create the request body
        String requestBody = String.format("{\"name\":\"%s\",\"email\":\"%s\"}", username, email);

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HttpEntity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8086/api/user/addUser",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        return "Hello Spring Boot With Keycloak - USER. User: " + username + " Email: " + email;
    }


    private String getUsernameFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String claim = jwt.getClaim("preferred_username");
            System.out.print("\n"+ claim);
            return jwt.getClaim("preferred_username");
        }
        return "Unknown name";
    }

    private String getEmailFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String claim = jwt.getClaim("email");
            System.out.print(claim);
            return jwt.getClaim("email");
        }
        return "Unknown email";
    }

    @Configuration
    public static class RestTemplateConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}
