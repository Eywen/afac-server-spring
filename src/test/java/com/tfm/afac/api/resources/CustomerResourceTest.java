package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.CustomerDto;
import com.tfm.afac.data.model.CustomerEntity;
import com.tfm.afac.services.business.CustomerService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RestTestConfig
class CustomerResourceTest {

    @Autowired
    CustomerResource customerResource;

    @MockBean
    CustomerService customerService;

    CustomerDto customerDto;
    CustomerEntity customerEntity;

    @BeforeEach
    void onInit(){
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
    void addCustomerTest() {

        when(customerService.create(any(CustomerDto.class))).thenReturn(customerDto);
        ResponseEntity<CustomerDto> responseEntity = customerResource.addCustomer(customerDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(customerDto, responseEntity.getBody());

        verify(customerService, times(1)).create(any(CustomerDto.class));
    }

    @Test
    void addCustomerForbiddenExceptionTest() {

        when(customerService.create(any(CustomerDto.class))).thenThrow(ForbiddenException.class);
        ResponseEntity<CustomerDto> responseEntity = customerResource.addCustomer(customerDto);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(customerDto, responseEntity.getBody());
        verify(customerService, times(1)).create(any(CustomerDto.class));
    }


    @Test
    void updateTest() {

        Integer customerId = 1;
        when(customerService.update(any(CustomerDto.class))).thenReturn(customerDto);

        ResponseEntity<CustomerDto> responseEntity = customerResource.update(customerId, customerDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDto, responseEntity.getBody());

        verify(customerService, times(1)).update(any(CustomerDto.class));
    }

    @Test
    void updateNotFoundExceptionTest() {

        Integer customerId = 1;
        when(customerService.update(any(CustomerDto.class))).thenThrow(NotFoundException.class);

        ResponseEntity<CustomerDto> responseEntity = customerResource.update(customerId, customerDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(customerDto, responseEntity.getBody());

        verify(customerService, times(1)).update(any(CustomerDto.class));
    }

    @Test
    void disableTest(){
        Integer customerId = 1;
        when(customerService.findById(anyInt())).thenReturn(customerDto);
        ResponseEntity<CustomerDto> responseEntity = customerResource.disable(customerId);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(customerService, times(1)).findById(anyInt());
        verify(customerService, times(1)).update(any(CustomerDto.class));
    }

    @Test
    void disableNotFoundTest(){
        Integer customerId = 1;
        when(customerService.findById(anyInt())).thenReturn(null);
        ResponseEntity<CustomerDto> responseEntity = customerResource.disable(customerId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void findByIdTest(){
        Integer customerId = 1;
        when(customerService.findById(anyInt())).thenReturn(customerDto);

        ResponseEntity<CustomerDto> responseEntity = customerResource.findById(customerId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDto, responseEntity.getBody());

        verify(customerService, times(1)).findById(anyInt());
    }
    @Test
    void findByIdNotFoundTest(){
        Integer customerId = 1;
        when(customerService.findById(anyInt())).thenThrow(NotFoundException.class);

        ResponseEntity<CustomerDto> responseEntity = customerResource.findById(customerId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void findAllActivate(){

        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = true;

        Page<CustomerEntity> mockCustomerPage = new PageImpl<>(Collections.emptyList());
        when(customerService.findByActivatePage(any(PageRequest.class), anyBoolean())).thenReturn(mockCustomerPage);
        ResponseEntity<Page<CustomerEntity>> responseEntity = customerResource.findAllActivePage(page, size, order, asc);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCustomerPage, responseEntity.getBody());
        verify(customerService, times(1)).findByActivatePage(any(PageRequest.class), anyBoolean());

    }

    @Test
    void findAllActivateCustomersAscTest(){
        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = true;

        Page<CustomerEntity> mockCustomerPage = new PageImpl<>(Arrays.asList(customerEntity));
        when(customerService.readAllPageable(any(PageRequest.class))).thenReturn(mockCustomerPage);
        ResponseEntity<Page<CustomerEntity>> responseEntity = customerResource.findAllActivePage(page, size, order, asc);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(customerService, times(1)).findByActivatePage(any(PageRequest.class),anyBoolean());
    }

    @Test
    void findAllActivateCustomersDescTest(){
        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = false;

        Page<CustomerEntity> mockCustomerPage = new PageImpl<>(Collections.emptyList());
        when(customerService.readAllPageable(any(PageRequest.class))).thenReturn(mockCustomerPage);
        ResponseEntity<Page<CustomerEntity>> responseEntity = customerResource.findAllActivePage(page, size, order, asc);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(customerService, times(2)).findByActivatePage(any(PageRequest.class),anyBoolean());
    }

    @Test
    void findByActivateBadRequestTest() {
        String activated = "true";
        when(customerService.findByActivate(anyBoolean()))
                .thenThrow(RuntimeException.class);

        ResponseEntity<List<CustomerDto>> responseEntity = customerResource.findByActivate(activated);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(customerService, times(1)).findByActivate(true);
    }
    @Test
    void findByActivateTest() {
        String activated = "true";
        List<CustomerDto> customerDtoList = new ArrayList<>();
        customerDtoList.add(customerDto);
        when(customerService.findByActivate(anyBoolean()))
                .thenReturn(customerDtoList);

        ResponseEntity<List<CustomerDto>> responseEntity = customerResource.findByActivate(activated);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(customerService, times(1)).findByActivate(true);
    }

    @Test
    void findAllActivePageBadRequestTest() {
        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = true;

        when(customerService.findByActivatePage(any(PageRequest.class), anyBoolean()))
                .thenThrow(RuntimeException.class);

        ResponseEntity<Page<CustomerEntity>> responseEntity = customerResource.findAllActivePage(page, size, order, asc);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
        verify(customerService, times(1)).findByActivatePage(any(PageRequest.class), anyBoolean());
    }

    @Test
    void findAllActivePageDefaultTest() {
        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = true;

        Page<CustomerEntity> mockCustomerPage = new PageImpl<>(Collections.emptyList());
        when(customerService.findByActivatePage(any(PageRequest.class), anyBoolean())).thenReturn(mockCustomerPage);

        ResponseEntity<Page<CustomerEntity>> responseEntity = customerResource.findAllActivePage(page, size, order, asc);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockCustomerPage, responseEntity.getBody());
        verify(customerService, times(1)).findByActivatePage(any(PageRequest.class), anyBoolean());
    }

    @Test
    void findAllTest() {

        List<CustomerDto> customerList = new ArrayList<>();
        customerList.add(customerDto);
        when(customerService.readAll()).thenReturn(customerList);

        ResponseEntity<List<CustomerDto>> responseEntity = customerResource.findAll();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerList, responseEntity.getBody());
        verify(customerService, times(1)).readAll();
    }
}
