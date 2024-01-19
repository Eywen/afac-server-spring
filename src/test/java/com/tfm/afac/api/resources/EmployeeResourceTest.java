package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.business.EmployeeService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RestTestConfig
public class EmployeeResourceTest {

    @Autowired
    private EmployeeResource employeeResource;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDto employeeDto;

    @BeforeEach
    public void onInit() {

        employeeDto = EmployeeDto.builder()
                .id(1).employeeName("empleado1")
                .lastName1("apellido")
                .cedula(00000001)
                .address("calle")
                .telephone(11)
                .iniDate(new Date())
                .activate(true)
                .build();
    }

    @Test
    void addEmployeeTest() {

        when(employeeService.create(any(EmployeeDto.class))).thenReturn(employeeDto);
        ResponseEntity<EmployeeDto> responseEntity = employeeResource.addEmployee(employeeDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(employeeDto, responseEntity.getBody());

        verify(employeeService, times(1)).create(any(EmployeeDto.class));
    }

    @Test
    void addEmployeeForbiddenExceptionTest() {

        when(employeeService.create(any(EmployeeDto.class))).thenThrow(ForbiddenException.class);
        ResponseEntity<EmployeeDto> responseEntity = employeeResource.addEmployee(employeeDto);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(employeeDto, responseEntity.getBody());
        verify(employeeService, times(1)).create(any(EmployeeDto.class));
    }


    @Test
    void updateTest() {

        Integer employeeId = 1;
        when(employeeService.update(any(EmployeeDto.class))).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> responseEntity = employeeResource.update(employeeId, employeeDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employeeDto, responseEntity.getBody());

        verify(employeeService, times(1)).update(any(EmployeeDto.class));
    }

    @Test
    void updateNotFoundExceptionTest() {

        Integer employeeId = 1;
        when(employeeService.update(any(EmployeeDto.class))).thenThrow(NotFoundException.class);

        ResponseEntity<EmployeeDto> responseEntity = employeeResource.update(employeeId, employeeDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(employeeDto, responseEntity.getBody());

        verify(employeeService, times(1)).update(any(EmployeeDto.class));
    }

    @Test
    void disableTest(){
        Integer employeeId = 1;
        when(employeeService.findById(anyInt())).thenReturn(employeeDto);
        ResponseEntity<EmployeeDto> responseEntity = employeeResource.disable(employeeId);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(employeeService, times(1)).findById(anyInt());
        verify(employeeService, times(1)).update(any(EmployeeDto.class));
    }

    @Test
    void findByIdTest(){
        Integer employeeId = 1;
        when(employeeService.findById(anyInt())).thenReturn(employeeDto);

        ResponseEntity<EmployeeDto> responseEntity = employeeResource.findById(employeeId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(employeeDto, responseEntity.getBody());

        verify(employeeService, times(1)).findById(anyInt());
    }

    @Test
    void findAllActivate(){

        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = true;

        Page<EmployeeEntity> mockEmployeePage = new PageImpl<>(Collections.emptyList());
        when(employeeService.readAllActive(any(PageRequest.class), anyBoolean())).thenReturn(mockEmployeePage);
        ResponseEntity<Page<EmployeeEntity>> responseEntity = employeeResource.findAllActive(page, size, order, asc);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockEmployeePage, responseEntity.getBody());
        verify(employeeService, times(1)).readAllActive(any(PageRequest.class), anyBoolean());

    }

    @Test
    void findAllEmployeesAscTest(){
        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = true;

        Page<EmployeeEntity> mockEmployeePage = new PageImpl<>(Collections.emptyList());
        when(employeeService.readAllPageable(any(PageRequest.class))).thenReturn(mockEmployeePage);
        ResponseEntity<Page<EmployeeEntity>> responseEntity = employeeResource.findAllEmployees(page, size, order, asc);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockEmployeePage, responseEntity.getBody());
        verify(employeeService, times(1)).readAllPageable(any(PageRequest.class));

    }

    @Test
    void findAllEmployeesDescTest(){
        int page = 0;
        int size = 10;
        String order = "order";
        boolean asc = false;

        Page<EmployeeEntity> mockEmployeePage = new PageImpl<>(Collections.emptyList());
        when(employeeService.readAllPageable(any(PageRequest.class))).thenReturn(mockEmployeePage);
        ResponseEntity<Page<EmployeeEntity>> responseEntity = employeeResource.findAllEmployees(page, size, order, asc);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockEmployeePage, responseEntity.getBody());
        verify(employeeService, times(2)).readAllPageable(any(PageRequest.class));

    }




}
