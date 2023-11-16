package com.tfm.afac.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tfm.afac.data.model.Role;
import com.tfm.afac.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @NotNull
    @NotBlank
    //@Pattern(regexp = Validations.NINE_DIGITS)
    private String email;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String password;
    private Role role;
    private Boolean active;
    private LocalDateTime registrationDate;
    private int id;

    public UserDto(User user) {
        BeanUtils.copyProperties(user, this);
        this.password = "secret";
    }

    public static UserDto ofMobileFirstName(User user) {
        return UserDto.builder().email(user.getEmail()).firstName(user.getFirstName()).build();
    }

    public void doDefault() {
        if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }

    public User toUser() {
        this.doDefault();
        User user = new User();
        BeanUtils.copyProperties(this, user);
        user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return user;
    }
}
