package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.daos.EmployeeRepository;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.EmployeeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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
                                throw new ForbiddenException("El empleado con esta cedula ya existe: " +  employee.getCedula() + " " + employee.getEmployeeName());
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
                 .map(EmployeeMapper.INSTANCIA::employeeEntityToEmployeeDto)
                 .collect(Collectors.toList());
    }

    public Page<EmployeeEntity> readAllPageable(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    public Page<EmployeeEntity> readAllActive(Pageable pageable, boolean activate) {
            Specification<EmployeeEntity> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (activate) {
                    predicates.add(cb.equal(root.get("activate"), activate));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            };
            return employeeRepository.findAll(spec, pageable);
    }
}
