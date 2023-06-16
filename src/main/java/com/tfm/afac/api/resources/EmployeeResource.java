package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.services.business.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void create(@Valid @RequestBody EmployeeDto employeeDto){

        EmployeeDto result = employeeService.create(employeeDto);
        System.out.println("creado empleado");
    }
}
