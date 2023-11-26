package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.CustomerDto;
import com.tfm.afac.data.model.CustomerEntity;
import com.tfm.afac.services.business.CustomerService;
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
@RequestMapping(CustomerResource.CUSTOMERS)
public class CustomerResource {

    public static final String CUSTOMERS = "/customers";
    private static final String CUSTOMER_ID = "/{id}";
    private static final String DISABLE = "/disable";

    @Autowired
    private CustomerService customerService;

    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@Valid @RequestBody CustomerDto customerDto){

        try {
            CustomerDto createdCustomer = customerService.create(customerDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(CUSTOMER_ID)
    public ResponseEntity<CustomerDto> update (@PathVariable Integer id,@Valid @RequestBody CustomerDto customerDto){
        try {
            CustomerDto createdCustomer = customerService.update(customerDto);
            return ResponseEntity.status(HttpStatus.OK).body(createdCustomer);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerDto);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(DISABLE+CUSTOMER_ID)
    public ResponseEntity<CustomerDto> disable (@PathVariable Integer id){
        try {
            CustomerDto customer = customerService.findById(id);
            if (null != customer){
                customer.setActivate(false);
                CustomerDto updatedCustomer = customerService.update(customer);
                return ResponseEntity.status(HttpStatus.OK).body(updatedCustomer);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomerDto());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomerDto());
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(CUSTOMER_ID)
    public ResponseEntity<CustomerDto> findById (@PathVariable Integer id){
        try {
            CustomerDto createdCustomer = customerService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(createdCustomer);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(new CustomerDto());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomerDto());
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll (){
        try {
            List<CustomerDto> customerList = customerService.readAll();
            return ResponseEntity.status(HttpStatus.OK).body(customerList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
    @SecurityRequirement(name = "basicAuth")
    @GetMapping("/readallactivate")
    public ResponseEntity<Page<CustomerEntity>>  findAllActive (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "order") String order,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        try {
            Page<CustomerEntity> customerDtos = customerService.readAllActive(PageRequest.of(page,size, Sort.by(order)),true);
            if (!asc)
                customerDtos = customerService.readAllActive(PageRequest.of(page,size, Sort.by(order).descending()),true);

            return ResponseEntity.status(HttpStatus.OK).body(customerDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*@SecurityRequirement(name = "basicAuth")
    @GetMapping("/readallpage")
    public ResponseEntity<Page<CustomerEntity>> findAllCustomers (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "order") String order,
            @RequestParam(defaultValue = "true") boolean asc

    ){
        try {
            Page<CustomerEntity> customerDtos = customerService.readAllPageable(PageRequest.of(page,size, Sort.by(order)));
            if (!asc)
                customerDtos = customerService.readAllPageable(PageRequest.of(page,size, Sort.by(order).descending()));

            return ResponseEntity.status(HttpStatus.OK).body(customerDtos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }*/
}
