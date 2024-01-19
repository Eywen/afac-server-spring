package com.tfm.afac.data.daos;

import com.tfm.afac.data.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer >, JpaSpecificationExecutor<EmployeeEntity> {

    Optional< EmployeeEntity > findByCedula(long cedula);

    List<EmployeeEntity> findByactivate(boolean activate);
}
