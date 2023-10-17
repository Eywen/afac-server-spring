package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.model.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    EmployeeDto create(EmployeeDto employee);

    EmployeeDto update(EmployeeDto employee);

    EmployeeDto findById(int id);

    List<EmployeeDto> readAll();

    Page<EmployeeEntity> readAllPageable(Pageable pageable);
}
