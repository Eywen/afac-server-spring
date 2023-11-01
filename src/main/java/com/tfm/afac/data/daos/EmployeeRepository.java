package com.tfm.afac.data.daos;

import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer >, JpaSpecificationExecutor<EmployeeEntity> {

    Optional< EmployeeEntity > findByCedula(long cedula);

    Page<EmployeeEntity> findByactivate(Pageable pageable,boolean activate);
}
