package com.example.curd.controller;

import com.example.curd.dto.UserRequest;
import com.example.curd.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id){
        return userService.get(id);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        return userService.getAll();
    }
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserRequest userRequest){
        return userService.save(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody UserRequest userRequest){
        return userService.update(id,userRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
        return userService.delete(id);
    }
}
