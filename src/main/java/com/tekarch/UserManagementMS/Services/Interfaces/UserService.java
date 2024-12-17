package com.tekarch.UserManagementMS.Services.Interfaces;


import com.tekarch.UserManagementMS.Model.User;

import java.util.List;
import java.util.Optional;

    public interface UserService {
        User createUser(User user);
        List<User> getAllUsers();
        Optional<User> getUserById(Long userId);
        User updateUser(Long userId, User updatedUser);
        void deleteUser(Long userId);
        void addPersonalInfo(Long userId, User updatedInfo);
        String getPersonalInfo(Long userId);
        String submitKYC(Long userId);
        String getKYCStatus(Long userId);
        String updateKYC(Long userId, String kycStatus);
        String deleteKYC(Long userId);
    }


