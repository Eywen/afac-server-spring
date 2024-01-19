package com.tfm.afac.data.daos;

import com.tfm.afac.data.model.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer >,
        JpaSpecificationExecutor<CustomerEntity> {

    Optional< CustomerEntity > findByCustomerName(String name);

    //Page<CustomerEntity> findByactivate(Pageable pageable,boolean activate);
    List<CustomerEntity> findByactivate(boolean activate);
}
