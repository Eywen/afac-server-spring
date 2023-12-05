package com.tfm.afac.services.business;

import com.tfm.afac.TestConfig;
import com.tfm.afac.api.dtos.CustomerDto;
import com.tfm.afac.data.daos.CustomerRepository;
import com.tfm.afac.data.model.CustomerEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestConfig
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private CustomerDto customerDto;
    private CustomerEntity customerEntity;

    @BeforeEach
    private void onInit(){
        customerDto = CustomerDto.builder()
                .id(1)
                .customerName("cliente1")
                .address("calle 1 # 1-1")
                .city("'Florida'")
                .telephone(1111111111)
                .activate(true)
                .iniDate(new Date())
                .build();
        customerEntity = CustomerMapper.INSTANCIA.customerDTOToCustomerEntity(customerDto);
    }

    @Test
    void customerCreateTest(){
        when(customerRepository.save(any (CustomerEntity.class))).thenReturn(customerEntity);
        CustomerDto result = this.customerService.create(customerDto);

        assertNotNull(result);
        verify(customerRepository, times(1)).findByCustomerName(anyString());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void createWithForbiddenExceptionTest(){

        when(customerRepository.findByCustomerName(anyString()))
                .thenThrow(ForbiddenException.class);
        assertThrows(ForbiddenException.class, () -> customerService.create(customerDto));
    }

    @Test
    void updateCustomerTest(){

        when(customerRepository.findById(anyInt()))
                .thenReturn(Optional.of(customerEntity));
        customerDto.setCustomerName("modificado");
        when(customerRepository.save(any (CustomerEntity.class))).thenReturn(customerEntity);
        assertNotNull( this.customerService.update(customerDto));
    }

    @Test
    void updateWithNotFoundExceptionTest(){
        when(customerRepository.findByCustomerName(anyString()))
                .thenThrow(NotFoundException.class);
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> customerService.update(customerDto));
    }
    @Test
    void findByIdCustomerTest(){
        when(customerRepository.findById(anyInt()))
                .thenReturn(Optional.of(customerEntity));

        assertNotNull( this.customerService.findById(customerDto.getId()));
    }

    @Test
    void findByIdWithNotFoundExceptionTest(){
        when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> customerService.update(customerDto));
    }

    @Test
    void readAllCustomerTest(){
        List<CustomerDto> result = this.customerService.readAll();
        verify(customerRepository, times(1)).findAll();
    }
    @Test
    void readAllPageableCustomerTest(){
        Page page = mock(Page.class);
        Pageable pageable = Pageable.unpaged();
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertNotNull( this.customerService.readAllPageable(pageable));
        assertFalse( this.customerService.readAllPageable(pageable).isEmpty());
    }

    @Test
    void readAllPageablecustomerEmptyTest(){

        Pageable pageable = Pageable.unpaged();
        Page<CustomerEntity> page = Page.empty();
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(page);
        assertTrue( this.customerService.readAllPageable(pageable).isEmpty());
    }
}
