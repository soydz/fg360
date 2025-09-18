package com.fleetguard360.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "app_user")
public class User {
    @Id
    private Long id;
    @Column(nullable = false)
    private String name;
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(name = "is_enable", nullable = false)
    private boolean isEnable;
    @Column(name = "account_no_expired", nullable = false)
    private boolean accountNoExpired;
    @Column(name = "account_no_locked", nullable = false)
    private boolean accountNoLocked;
    @Column(name = "credential_no_expired", nullable = false)
    private boolean credentialNoExpired;

    @OneToOne
    @JoinColumn(name = "type_of_id", referencedColumnName = "id")
    private UserIdType idType;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
