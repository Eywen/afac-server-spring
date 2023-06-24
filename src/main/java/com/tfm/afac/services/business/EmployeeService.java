package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    EmployeeDto create(EmployeeDto employee);

    EmployeeDto update(EmployeeDto employee);

    EmployeeDto findById(int id);

    List<EmployeeDto> readAll();
}
