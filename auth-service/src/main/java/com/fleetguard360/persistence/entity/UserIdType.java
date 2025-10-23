package com.fleetguard360.persistence.entity;

import com.fleetguard360.persistence.entity.enums.UserIdTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "type_of_id")
public class UserIdType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id_type")
    @Enumerated(EnumType.STRING)
    private UserIdTypeEnum userIdType;

    @OneToMany(mappedBy = "idType")
    private List<User> users;
}
