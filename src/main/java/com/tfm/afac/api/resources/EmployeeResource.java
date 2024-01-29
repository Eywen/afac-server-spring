package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.business.EmployeeService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private static final String DISABLE = "/disable";

    private static final String ACTIVATE = "/activate";
    private static final String STRING_ACTIVATE = "/{activated}";


    private EmployeeService employeeService;

    @Autowired
    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody EmployeeDto employeeDto){

        try {
            EmployeeDto createdEmployee = employeeService.create(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(employeeDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(EMPLOYEE_ID)
    public ResponseEntity<EmployeeDto> update (@PathVariable Integer id,@Valid @RequestBody EmployeeDto employeeDto){
        try {
            EmployeeDto createdEmployee = employeeService.update(employeeDto);
            return ResponseEntity.status(HttpStatus.OK).body(createdEmployee);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(employeeDto);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(DISABLE+EMPLOYEE_ID)
    public ResponseEntity<EmployeeDto> disable (@PathVariable Integer id){
        try {
            EmployeeDto employee = employeeService.findById(id);
            if (null != employee){
                employee.setActivate(false);
                EmployeeDto updatedEmployee = employeeService.update(employee);
                return ResponseEntity.status(HttpStatus.OK).body(updatedEmployee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmployeeDto());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmployeeDto());
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(EMPLOYEE_ID)
    public ResponseEntity<EmployeeDto> findById (@PathVariable Integer id){
        try {
            EmployeeDto createdEmployee = employeeService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(createdEmployee);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new EmployeeDto());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new EmployeeDto());
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll (){
        try {
            List<EmployeeDto> employeeList = employeeService.readAll();
            return ResponseEntity.status(HttpStatus.OK).body(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
    @SecurityRequirement(name = "basicAuth")
    @GetMapping(ACTIVATE + STRING_ACTIVATE)
    public ResponseEntity<List<EmployeeDto>> findByActivate (@PathVariable String activated){
        try {
            List<EmployeeDto> employeeList = employeeService.findByActivate(Boolean.valueOf(activated));
            return ResponseEntity.status(HttpStatus.OK).body(employeeList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
    @SecurityRequirement(name = "basicAuth")
    @GetMapping("/readallactivate")
    public ResponseEntity<Page<EmployeeEntity>>  findAllActive (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "order") String order,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        try {
            Page<EmployeeEntity> employeeDtos = employeeService.readAllActive(PageRequest.of(page,size, Sort.by(order)),true);
            if (!asc)
                employeeDtos = employeeService.readAllActive(PageRequest.of(page,size, Sort.by(order).descending()),true);

            return ResponseEntity.status(HttpStatus.OK).body(employeeDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping("/readallpage")
    public ResponseEntity<Page<EmployeeEntity>> findAllEmployees (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "order") String order,
            @RequestParam(defaultValue = "true") boolean asc

    ){
        try {
            Page<EmployeeEntity> employeeDtos = employeeService.readAllPageable(PageRequest.of(page,size, Sort.by(order)));
            if (!asc)
                employeeDtos = employeeService.readAllPageable(PageRequest.of(page,size, Sort.by(order).descending()));

            return ResponseEntity.status(HttpStatus.OK).body(employeeDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
