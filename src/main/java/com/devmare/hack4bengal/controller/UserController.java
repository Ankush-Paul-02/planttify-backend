package com.devmare.hack4bengal.controller;

import com.devmare.hack4bengal.business.domain.DefaultResponse;
import com.devmare.hack4bengal.business.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<DefaultResponse> getAllUsers() {
        return ResponseEntity.ok(
                new DefaultResponse(
                        DefaultResponse.Status.SUCCESS,
                        Map.of("users", userService.getAllUsers()),
                        "Users fetched successfully"
                )
        );
    }
}
