package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.daos.EmployeeRepository;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto create(EmployeeDto employee) {
        EmployeeEntity employeeEntity = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employee);
        employeeEntity.setActivate(true);
        employeeEntity.setIniDate(new Date());
        EmployeeEntity result = employeeRepository.save(employeeEntity);
        EmployeeDto employeeDto = EmployeeMapper.INSTANCIA.employeeEntityToEmployeeDto(result);
        return employeeDto;
    }
}
