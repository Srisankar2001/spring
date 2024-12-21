package com.example.curd.service;

import com.example.curd.dto.MessageResponse;
import com.example.curd.dto.UserRequest;
import com.example.curd.entity.User;
import com.example.curd.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserRequest format(UserRequest userRequest){
        userRequest.setName(userRequest.getName().trim().substring(0,1).toUpperCase()+userRequest.getName().trim().substring(1).toLowerCase());
        userRequest.setEmail(userRequest.getEmail().trim().toLowerCase());
        return userRequest;
    }

    public User getUserById(int id){
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with this id : "+id));
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    public boolean isEmailExist(String email){
        return userRepo.existsByEmail(email);
    }
    public ResponseEntity<?> get(int id) {
        User user = getUserById(id);
        return ResponseEntity.ok().body(
                MessageResponse.builder()
                        .status(200)
                        .message("User fetched successfully")
                        .data(user)
                        .build());
    }

    public ResponseEntity<?> getAll() {
        List<User> users = getAllUsers();
        String message = users.isEmpty() ? "No users available" : "Users fetched successfully";
        return ResponseEntity.ok().body(
                MessageResponse.builder()
                        .status(200)
                        .message(message)
                        .data(users)
                        .build());
    }

    public ResponseEntity<?> save(UserRequest userRequest) {
        UserRequest request = format(userRequest);
        if(isEmailExist(request.getEmail())){
           throw new IllegalArgumentException("Email is already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();

        User savedUser = userRepo.save(user);
        return ResponseEntity.ok().body(
                MessageResponse.builder()
                        .status(200)
                        .message("User saved successfully")
                        .data(savedUser)
                        .build());
    }

    public ResponseEntity<?> update(int id, UserRequest userRequest) {
        User user = getUserById(id);
        UserRequest request = format(userRequest);

        if(!user.getName().equals(request.getName())){
            user.setName(request.getName());
        }
        if(!user.getEmail().equals(request.getEmail())){
            if(isEmailExist(request.getEmail())){
                throw new IllegalArgumentException("Email is already exists");
            }else{
                user.setEmail(request.getEmail());
            }
        }

        User updatedUser = userRepo.save(user);
        return ResponseEntity.ok().body(
                MessageResponse.builder()
                        .status(200)
                        .message("User updated successfully")
                        .data(updatedUser)
                        .build());
    }

    public ResponseEntity<?> delete(int id) {
        User user = getUserById(id);
        userRepo.delete(user);
        return ResponseEntity.ok().body(
                MessageResponse.builder()
                        .status(200)
                        .message("User deleted successfully")
                        .data(user)
                        .build());
    }
}
