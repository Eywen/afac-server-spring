package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.services.business.EmployeeService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(EmployeeResource.EMPLOYEES)
public class EmployeeResource {

    public static final String EMPLOYEES = "/employees";
    public static final String CREATE = "/create";
    private static final String EMPLOYEE_ID = "/{id}";

    @Autowired
    private EmployeeService employeeService;

    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    public ResponseEntity<EmployeeDto> create(@Valid @RequestBody EmployeeDto employeeDto){

        try {
            EmployeeDto createdEmployee = employeeService.create(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(employeeDto);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping
    public ResponseEntity<EmployeeDto> update (@Valid @RequestBody EmployeeDto employeeDto){
        try {
            EmployeeDto createdEmployee = employeeService.update(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(employeeDto);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping
    public ResponseEntity<EmployeeDto> findById (@PathVariable int id){
        try {
            EmployeeDto createdEmployee = employeeService.findById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new EmployeeDto());
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll (){
        try {
            List<EmployeeDto> EmployeeList = employeeService.readAll();
            return ResponseEntity.status(HttpStatus.OK).body(EmployeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
}
