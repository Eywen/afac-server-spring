package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.services.business.EmployeeService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(EmployeeResource.EMPLOYEES)
public class EmployeeResource {

    public static final String EMPLOYEES = "/employees";
    public static final String CREATE = "/create";

    @Autowired
    private EmployeeService employeeService;

    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto){

        try {
            EmployeeDto createdEmployee = employeeService.create(employeeDto);
            System.out.println("creado empleado");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (ForbiddenException e) {
            System.out.println("cedula ya existe");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(employeeDto);
        }
    }
}
