package com.tfm.afac.services.business;

//import com.tfm.afac.TestConfig;
import com.tfm.afac.TestConfig;
import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.model.EmployeeEntity;
import com.tfm.afac.services.business.EmployeeService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestConfig
//@RunWith(value= SpringRunner.class)
//@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testCreateEmployeeForbidden() {
        EmployeeDto employee = EmployeeDto.builder()
                .id(1).employeeName("empleado1")
                .lastName1("apellido")
                .cedula(1)
                .address("calle")
                .telephone(11)
                .iniDate(new Date())
                .activate(true)
                .build();
        assertNotNull( this.employeeService.create(employee));
    }
}
