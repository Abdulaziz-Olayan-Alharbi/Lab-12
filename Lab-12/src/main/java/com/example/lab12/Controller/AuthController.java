package com.example.lab12.Controller;

import com.example.lab12.Api.ApiResponse;
import com.example.lab12.Model.User;
import com.example.lab12.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(authService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user){
        authService.register(user);
        return ResponseEntity.status(200).body(new ApiResponse("User registered successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal User user , @Valid @RequestBody User newUser){
        authService.updateUser(user.getId(), newUser);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId){
        authService.deleteUser(userId);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }
}
