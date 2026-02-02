package com.example.demo.controllers;

import com.example.demo.dtos.responce.EmployeeResponceDTO;
import com.example.demo.entites.Employee;
import com.example.demo.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    String token="iiicwkjbenoiwbifiewnfi";

    @GetMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse responce) {
        ResponseCookie cookie = ResponseCookie.from("AUTH_TOKEN", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofMinutes(90))
                .build();

        responce.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok( "Logged in");
    }
}





