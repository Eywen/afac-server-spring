package com.tfm.afac.data.daos;

import com.tfm.afac.TestConfig;
import com.tfm.afac.data.model.Role;
import com.tfm.afac.data.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmailTest(){
        Optional<User> optionalUser = this.userRepository.findByEmail("user1@prueba.com");
        User userentity = optionalUser.get();
        assertTrue(optionalUser.isPresent());
        assertEquals( "user1@prueba.com",userentity.getEmail());
        assertEquals( "user1",userentity.getFirstName());
    }

    @Test
    void findByRoleInTest(){
        Collection roles = new ArrayList<>();
        roles.add(Role.ADMIN);
        List<User> listUserByRole = this.userRepository.findByRoleIn(roles);

        assertFalse(listUserByRole.isEmpty());
        assertEquals( "user1@prueba.com",listUserByRole.get(0).getEmail());
        assertEquals( "user1",listUserByRole.get(0).getFirstName());
    }


}
