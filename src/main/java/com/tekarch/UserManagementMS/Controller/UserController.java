package com.tekarch.UserManagementMS.Controller;

import com.tekarch.UserManagementMS.Model.User;
import com.tekarch.UserManagementMS.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create User Profile
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Retrieve All User Profiles
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Retrieve User by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        validateUserId(userId); // Ensure userId is valid
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
    }

    // Update User Profile
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        validateUserId(userId);
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Delete User Profile
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        validateUserId(userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Add Personal Info
    @PutMapping("/{userId}/personal-info")
    public ResponseEntity<Void> addPersonalInfo(@PathVariable Long userId, @RequestBody User updatedInfo) {
        validateUserId(userId);
        userService.addPersonalInfo(userId, updatedInfo);
        return ResponseEntity.ok().build();
    }

    // Retrieve Personal Info
    @GetMapping("/{userId}/personal-info")
    public ResponseEntity<Map<String, String>> getPersonalInfo(@PathVariable Long userId) {
        validateUserId(userId);
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
        Map<String, String> personalInfo = new HashMap<>();
        personalInfo.put("address", user.getAddress());
        personalInfo.put("dob", user.getDob());
        personalInfo.put("gender", user.getGender());
        return ResponseEntity.ok(personalInfo);
    }

    // Submit KYC
    @PostMapping("/{userId}/kyc")
    public ResponseEntity<String> submitKYC(@PathVariable Long userId) {
        validateUserId(userId);
        String response = userService.submitKYC(userId);
        return ResponseEntity.ok("KYC submitted successfully for User ID: " + userId);
    }

    // Retrieve KYC Status
    @GetMapping("/{userId}/kyc")
    public ResponseEntity<String> getKYCStatus(@PathVariable Long userId) {
        validateUserId(userId);
        String status = userService.getKYCStatus(userId);
        return ResponseEntity.ok(status);
    }

    // Update KYC Status
    @PutMapping("/{userId}/kyc")
    public ResponseEntity<String> updateKYC(@PathVariable Long userId, @RequestBody String kycStatus) {
        validateUserId(userId);
        String response = userService.updateKYC(userId, kycStatus);
        return ResponseEntity.ok(response);
    }

    // Delete KYC
    @DeleteMapping("/{userId}/kyc")
    public ResponseEntity<Void> deleteKYC(@PathVariable Long userId) {
        validateUserId(userId);
        userService.deleteKYC(userId);
        return ResponseEntity.noContent().build();
    }

    // Helper Method to Validate User ID
    private void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Invalid User ID: " + userId);
        }
    }
}