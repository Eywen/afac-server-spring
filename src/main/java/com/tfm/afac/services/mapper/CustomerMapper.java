package com.tfm.afac.services.mapper;

import com.tfm.afac.api.dtos.CustomerDto;
import com.tfm.afac.data.model.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCIA= Mappers.getMapper(CustomerMapper.class);

    CustomerDto customerEntityToCustomerDto(CustomerEntity customerEntity);
    CustomerEntity customerDTOToCustomerEntity(CustomerDto customerDto);
}
