package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.CustomerDto;
import com.tfm.afac.data.daos.CustomerRepository;
import com.tfm.afac.data.model.CustomerEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.CustomerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto create(CustomerDto customer) {

        CustomerEntity customerEntity = CustomerMapper.INSTANCIA.customerDTOToCustomerEntity(customer);
        customerRepository.findByCustomerName(customer.getCustomerName())
                    .ifPresent(user -> {
                                throw new ForbiddenException("El cliente ya existe: " +  customer.getCustomerName());
                            });
        customerEntity.setActivate(true);
        customerEntity.setIniDate(new Date());
        return CustomerMapper.INSTANCIA.customerEntityToCustomerDto(customerRepository.save(customerEntity));
    }

    @Override
    public CustomerDto update( CustomerDto customer) {

        CustomerEntity customerEntity = CustomerMapper.INSTANCIA.customerDTOToCustomerEntity(customer);
        return CustomerMapper.INSTANCIA.customerEntityToCustomerDto(
                customerRepository.findById(customer.getId())
                        .map(existingCustomer -> {
                            existingCustomer.setCustomerName(customerEntity.getCustomerName());
                            existingCustomer.setCity(customerEntity.getCity());
                            existingCustomer.setAddress(customerEntity.getAddress());
                            existingCustomer.setTelephone(customerEntity.getTelephone());
                            existingCustomer.setActivate(customerEntity.isActivate());
                            existingCustomer.setCloseMonthDay(customerEntity.getCloseMonthDay());
                            return existingCustomer;
                        })
                        .map(customerRepository::save)
                        .orElseThrow(() -> new NotFoundException("No se encontró el cliente: " + customer.getCustomerName()))
        );
    }

    @Override
    public CustomerDto findById(int id) {

        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró el cliente: " + id));

        return CustomerMapper.INSTANCIA.customerEntityToCustomerDto(customer);
    }

    @Override
    public List<CustomerDto> readAll() {
         return customerRepository.findAll().stream()
                 .map(CustomerMapper.INSTANCIA::customerEntityToCustomerDto)
                 .collect(Collectors.toList());
    }

    public Page<CustomerEntity> readAllPageable(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
    public Page<CustomerEntity> readAllActive(Pageable pageable, boolean activate) {
            Specification<CustomerEntity> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (activate) {
                    predicates.add(cb.equal(root.get("activate"), activate));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            };
            return customerRepository.findAll(spec, pageable);
    }
}
