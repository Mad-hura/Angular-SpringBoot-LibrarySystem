package com.example.LoginPage.controller;


import com.example.LoginPage.dao.UserRepo;
import com.example.LoginPage.model.User;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@Builder
public class loginController {

    private final UserRepo userRepo;

    @Autowired
    public loginController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User userData) {
        User user = (User) userRepo.findByUserId(userData.getUserId());
        if (user.getPassword().equals(userData.getPassword())) {
            return ResponseEntity.ok(user);
        }
        return (ResponseEntity<?>) ResponseEntity.internalServerError();

    }
}
