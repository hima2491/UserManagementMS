package com.tekarch.UserManagementMS.Services;

import com.tekarch.UserManagementMS.Model.User;
import com.tekarch.UserManagementMS.Repository.UserRepository;
import com.tekarch.UserManagementMS.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // Helper Method to Get User by ID
    private User getUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with ID " + userId + " not found"));
    }

    // Create User Profile
    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null");
        }
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Retrieve All User Profiles
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Retrieve User by ID
    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    // Update User Profile
    @Override
    public User updateUser(Long userId, User updatedUser) {
        User user = getUserByIdOrThrow(userId);

        if (updatedUser.getUsername() != null) user.setUsername(updatedUser.getUsername());
        if (updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPhoneNumber() != null) user.setPhoneNumber(updatedUser.getPhoneNumber());
        if (updatedUser.getPasswordHash() != null) user.setPasswordHash(updatedUser.getPasswordHash());
        user.setTwoFactorEnabled(updatedUser.isTwoFactorEnabled());
        if (updatedUser.getKycStatus() != null) user.setKycStatus(updatedUser.getKycStatus());

        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Delete User Profile
    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchElementException("User with ID " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    // Add Personal Info
    @Override
    public void addPersonalInfo(Long userId, User updatedInfo) {
        User user = getUserByIdOrThrow(userId);

        if (updatedInfo.getAddress() != null) user.setAddress(updatedInfo.getAddress());
        if (updatedInfo.getDob() != null) user.setDob(updatedInfo.getDob());
        if (updatedInfo.getGender() != null) user.setGender(updatedInfo.getGender());

        userRepository.save(user);
    }

    // Retrieve Personal Info
    @Override
    public String getPersonalInfo(Long userId) {
        User user = getUserByIdOrThrow(userId);
        return String.format("Address: %s, DOB: %s, Gender: %s",
                user.getAddress(), user.getDob(), user.getGender());
    }

    // Submit KYC
    @Override
    public String submitKYC(Long userId) {
        User user = getUserByIdOrThrow(userId);
        user.setKycStatus("SUBMITTED");
        userRepository.save(user);
        return "KYC has been submitted successfully";
    }

    // Retrieve KYC Status
    @Override
    public String getKYCStatus(Long userId) {
        User user = getUserByIdOrThrow(userId);
        return user.getKycStatus();
    }

    // Update KYC Status
    @Override
    public String updateKYC(Long userId, String kycStatus) {
        if (kycStatus == null || kycStatus.length() > 50) {
            throw new IllegalArgumentException("Invalid KYC status");
        }

        User user = getUserByIdOrThrow(userId);
        user.setKycStatus(kycStatus);
        userRepository.save(user);
        return "KYC status has been updated successfully";
    }

    // Delete KYC
    @Override
    public String deleteKYC(Long userId) {
        User user = getUserByIdOrThrow(userId);
        user.setKycStatus("DELETED");
        userRepository.save(user);
        return "KYC status has been deleted successfully";
    }
}
