package com.tfm.afac.services.mapper;

import com.tfm.afac.api.dtos.EmployeeDto;
import com.tfm.afac.data.model.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCIA= Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto employeeEntityToEmployeeDto(EmployeeEntity employeeEntity);
    EmployeeEntity employeeDTOToEmployeeEntity(EmployeeDto employeeDto);
}
