package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.TokenDto;
import com.tfm.afac.api.dtos.UserDto;
import com.tfm.afac.data.model.Role;
import com.tfm.afac.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String MOBILE_ID = "/{mobile}";
    public static final String SEARCH = "/search";

    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping(value = TOKEN)
    public Optional<TokenDto> login(@AuthenticationPrincipal User activeUser) {
        Optional<String> login = userService.login(activeUser.getUsername());
        Optional<TokenDto> tokenDto = login.map(TokenDto::new);
        System.out.println("login: " + login);
        System.out.println("tokenDto: " + tokenDto);
        return userService.login(activeUser.getUsername())
                .map(TokenDto::new);
    }

}
