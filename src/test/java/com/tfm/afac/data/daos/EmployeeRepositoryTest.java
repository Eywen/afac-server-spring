package com.tfm.afac.data.daos;

import com.tfm.afac.TestConfig;
import com.tfm.afac.data.model.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@TestConfig
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void ReadTest() {
        assertTrue(this.employeeRepository.findAll().stream()
                .anyMatch(feature ->
                        isEmployeeId1(feature)
                ));
    }

    @Test
    void findByCedulaTest(){
        assertTrue(this.employeeRepository.findById(1).stream()
                .anyMatch(feature ->
                        isEmployeeId1(feature)
                ));
    }

    private static boolean isEmployeeId1(EmployeeEntity feature) {
        return "empleado1".equals(feature.getEmployeeName()) &&
                1111111111 == feature.getCedula() &&
                feature.getLastName1().equals("apellido1") &&
                feature.getLastName2().equals("apellido2") &&
                feature.getAddress().equals("calle 1 # 1-1") &&
                feature.getCity().equals("Florida") &&
                1111111111 == feature.getTelephone() &&
                feature.getFinishDate() == null;
    }
}
