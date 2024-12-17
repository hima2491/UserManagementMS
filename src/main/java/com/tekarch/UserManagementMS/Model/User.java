package com.tekarch.UserManagementMS.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        @Column(unique = true, nullable = false, length = 50)
        private String username;

        @Column(unique = true, nullable = false, length = 100)
        private String email;

        @Column(nullable = false)
        private String passwordHash;

        @Column(unique = true, length = 15)
        private String phoneNumber;

        @Builder.Default
        @Column(nullable = false)
        private boolean twoFactorEnabled = false;

        @Builder.Default
        @Column(nullable = false, length = 20)
        private String kycStatus = "pending";

        @Column(length = 255)
        private String address;

        @Column(length = 10)
        private String dob;

        @Column(length = 10)
        private String gender;

        @Builder.Default
        private LocalDateTime createdAt = LocalDateTime.now();

        @Builder.Default
        private LocalDateTime updatedAt = LocalDateTime.now();

        @Builder.Default
        @ElementCollection
        @CollectionTable(
                name = "user_linked_accounts",
                joinColumns = @JoinColumn(name = "user_id")
        )
        @Column(name = "account_id")
        private List<Long> linkedAccountIds = new ArrayList<>();
}
