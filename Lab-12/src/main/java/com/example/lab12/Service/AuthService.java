package com.example.lab12.Service;

import com.example.lab12.Api.ApiException;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }

    public void register(User user) {
        user.setRole("USER");
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);
    }

    public void updateUser(Integer userId,User user) {
        User oldUser = authRepository.findUserById(userId);
        if (oldUser==null){
            throw new ApiException("User not found");
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        authRepository.save(oldUser);
    }

    public void deleteUser(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        authRepository.deleteById(userId);
    }
}
