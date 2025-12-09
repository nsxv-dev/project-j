package com.devCircle.devCircle.controller;

import com.devCircle.devCircle.dto.UserDto;
import com.devCircle.devCircle.dto.UserProfileDto;
import com.devCircle.devCircle.dto.UserProfileUpdateDto;
import com.devCircle.devCircle.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(userService.create(user));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserProfileById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PatchMapping("/me")
    public ResponseEntity<UserProfileDto> updateCurrentUserProfile(
            @RequestBody UserProfileUpdateDto updateDto
    ) {
        return ResponseEntity.ok(userService.updateCurrentUserProfile(updateDto));
    }
}
