package com.tfm.afac.data.daos;

import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer > {
}
