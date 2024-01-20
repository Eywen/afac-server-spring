package com.tfm.afac.data.daos;

import com.tfm.afac.TestConfig;
import com.tfm.afac.data.model.CustomerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void ReadTest() {
        assertTrue(this.customerRepository.findAll().stream()
                .anyMatch(feature ->
                        isCustomerId1(feature)
                ));
    }

    @Test
    void findByIdTest(){
        assertTrue(this.customerRepository.findById(1).stream()
                .anyMatch(feature ->
                        isCustomerId1(feature)
                ));
    }

    @Test
    void findByCustomerNameTest(){
        assertTrue(this.customerRepository.findByCustomerName("cliente1").stream()
                .anyMatch(feature ->
                        isCustomerId1(feature)
                ));
    }

    private static boolean isCustomerId1(CustomerEntity feature) {
        return "cliente1".equals(feature.getCustomerName()) &&
                feature.getAddress().equals("calle 1 # 1-1") &&
                feature.getCity().equals("Florida") &&
                1111111111 == feature.getTelephone() &&
                feature.getFinishDate() == null;
    }
}
