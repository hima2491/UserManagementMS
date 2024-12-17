package com.tekarch.UserManagementMS.Repository;

import com.tekarch.UserManagementMS.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


    @Repository
    public interface UserRepository extends JpaRepository<User, Long> {
    }

