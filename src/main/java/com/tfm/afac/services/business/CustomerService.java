package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.CustomerDto;
import com.tfm.afac.data.model.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    CustomerDto create(CustomerDto Customer);

    CustomerDto update(CustomerDto customer);

    CustomerDto findById(int id);

    List<CustomerDto> readAll();

    Page<CustomerEntity> readAllPageable(Pageable pageable);
    Page<CustomerEntity> readAllActive(Pageable pageable, boolean activate);
}
