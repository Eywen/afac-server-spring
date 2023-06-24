package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.daos.EmployeeRepository;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeDto create(EmployeeDto employee) {

        EmployeeEntity employeeEntity = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employee);
        employeeRepository.findByCedula(employee.getCedula())
                    .ifPresent(user -> {
                                throw new ForbiddenException("El empleado con esta cedula ya existe" +  employee);
                            });
        employeeEntity.setActivate(true);
        employeeEntity.setIniDate(new Date());
        return EmployeeMapper.INSTANCIA.employeeEntityToEmployeeDto(employeeRepository.save(employeeEntity));
    }

    @Override
    public EmployeeDto update( EmployeeDto employee) {

        EmployeeEntity employeeEntity = EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(employee);
        return EmployeeMapper.INSTANCIA.employeeEntityToEmployeeDto(
                employeeRepository.findById(employee.getId())
                        .map(existingEmployee -> {
                            existingEmployee.setEmployeeName(employeeEntity.getEmployeeName());
                            existingEmployee.setLastName1(employeeEntity.getLastName1());
                            existingEmployee.setLastName2(employeeEntity.getLastName2());
                            existingEmployee.setCity(employeeEntity.getCity());
                            existingEmployee.setAddress(employeeEntity.getAddress());
                            existingEmployee.setTelephone(employeeEntity.getTelephone());
                            existingEmployee.setActivate(employeeEntity.isActivate());
                            return existingEmployee;
                        })
                        .map(employeeRepository::save)
                        .orElseThrow(() -> new NotFoundException("No se encontró el empleado con la cedula: " + employee.getCedula()))
        );
    }

    @Override
    public EmployeeDto findById(int id) {

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el empleado con el ID: " + id));

        return EmployeeMapper.INSTANCIA.employeeEntityToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> readAll() {
         return employeeRepository.findAll().stream()
                 .map(employee -> EmployeeMapper.INSTANCIA.employeeEntityToEmployeeDto(employee))
                 .collect(Collectors.toList());
    }
}
