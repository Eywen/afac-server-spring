package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.GuideDto;
import com.tfm.afac.data.daos.GuideRepository;
import com.tfm.afac.data.model.GuideEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.CustomerMapper;
import com.tfm.afac.services.mapper.EmployeeMapper;
import com.tfm.afac.services.mapper.GuideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl implements GuideService{

    @Autowired
    private GuideRepository guideRepository;

    @Override
    public GuideDto create(GuideDto guideDto) {

        GuideEntity guideEntity = GuideMapper.INSTANCIA.guideDTOToGuideEntity(guideDto);
        guideRepository.findByIdGuide(guideDto.getIdGuide())
                .ifPresent(guide -> {
                    throw new ForbiddenException("La guia con este identificador ya existe: " +  guideDto.getIdGuide());
                });
        guideEntity.setActivate(true);
        guideEntity.setEntryDate(new Date());
        return GuideMapper.INSTANCIA.guideEntityToGuideDto(guideRepository.save(guideEntity));
    }

    @Override
    public GuideDto update(GuideDto guideDto) {
        return GuideMapper.INSTANCIA.guideEntityToGuideDto(
                guideRepository.findByIdGuide(guideDto.getIdGuide())
                        .map(existingGuide -> {
                            existingGuide.setEntryDate(guideDto.getEntryDate());
                            existingGuide.setDeliveryDate(guideDto.getDeliveryDate());
                            existingGuide.setRecipient(guideDto.getRecipient());
                            /*existingGuide.setCustomerId(guideDto.getIdCustomer());
                            existingGuide.setEmployeeId(guideDto.getIdEmployee());*/
                            existingGuide.setStatus(guideDto.getStatus());
                            existingGuide.setCity(guideDto.getCity());
                            existingGuide.setAddress(guideDto.getAddress());
                            existingGuide.setTelephone(guideDto.getTelephone());
                            existingGuide.setActivate(guideDto.isActivate());
                            existingGuide.setEmployee(EmployeeMapper.INSTANCIA.employeeDTOToEmployeeEntity(guideDto.getEmployee()));
                            existingGuide.setCustomer(CustomerMapper.INSTANCIA.customerDTOToCustomerEntity(guideDto.getCustomer()));
                            existingGuide.setActivate(guideDto.isActivate());
                            return existingGuide;
                        })
                        .map(guideRepository::save)
                        .orElseThrow(() -> new NotFoundException("No se encontró la guia: " + guideDto.getIdGuide()))
        );
    }


    @Override
    public GuideDto findByIdGuide(Long id) {
        GuideEntity guide = guideRepository.findByIdGuide(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la guia: " + id));

        return GuideMapper.INSTANCIA.guideEntityToGuideDto(guide);
    }

    @Override
    public List<GuideDto> findByEmployeeId(Integer employeeId) {

        List<GuideDto> guideDtoList = guideRepository.findByEmployeeId(employeeId)
                .stream()
                .map(GuideMapper.INSTANCIA::guideEntityToGuideDto)
                .collect(Collectors.toList());
        return getReturnGuideDtos(guideDtoList);
    }

    @Override
    public List<GuideDto> findByCustomerId(Integer customerId) {
        List<GuideDto> guideDtoList =  guideRepository.findByCustomerId(customerId)
                .stream()
                .map(GuideMapper.INSTANCIA::guideEntityToGuideDto)
                .collect(Collectors.toList());
        return getReturnGuideDtos(guideDtoList);
    }

    private static List<GuideDto> getReturnGuideDtos(List<GuideDto> guideDtoList) {
        if (!guideDtoList.isEmpty() && null != guideDtoList)
            return guideDtoList;
        else
            throw new NotFoundException("No se encontraron guias para estos datos");
    }

    @Override
    public List<GuideDto> findByStatus(String status) {
        List<GuideDto> guideDtoList =  guideRepository.findByStatus(status)
                .stream()
                .map(GuideMapper.INSTANCIA::guideEntityToGuideDto)
                .collect(Collectors.toList());
        return getReturnGuideDtos(guideDtoList);
    }

    @Override
    public List<GuideDto> findByEntryDate(Date entryDate) {

        List<GuideDto> guideDtoList = guideRepository.findByEntryDate(entryDate)
                .stream()
                .map(GuideMapper.INSTANCIA::guideEntityToGuideDto)
                .collect(Collectors.toList());
        return getReturnGuideDtos(guideDtoList);
    }

    @Override
    public List<GuideDto> findByDeliveryDate(Date deliveryDate) {
        List<GuideDto> guideDtoList = guideRepository.findByDeliveryDate(deliveryDate)
                .stream()
                .map(GuideMapper.INSTANCIA::guideEntityToGuideDto)
                .collect(Collectors.toList());
        return getReturnGuideDtos(guideDtoList);
    }
}
