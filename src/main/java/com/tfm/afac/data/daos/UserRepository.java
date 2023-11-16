package com.tfm.afac.data.daos;

import com.tfm.afac.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tfm.afac.data.model.Role;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer > {
    Optional< User > findByEmail(String email);

    List< User > findByRoleIn(Collection< Role > roles);

}
