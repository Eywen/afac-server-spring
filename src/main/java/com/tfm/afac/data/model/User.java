package com.tfm.afac.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Table(name = "user_app") // conflict with user table
public class User {

    @Id
    @GeneratedValue
    private int id;
    @NonNull
    //@Column(unique = true, nullable = false)
    private String email;
    @NotNull
    @NotBlank
    @Column(name = "first_name")
    private String firstName;
    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_user")
    private Role role;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    private Boolean active;
}
