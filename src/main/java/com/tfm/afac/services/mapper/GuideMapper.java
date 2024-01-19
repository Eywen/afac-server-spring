package com.tfm.afac.services.mapper;

import com.tfm.afac.api.dtos.GuideDto;
import com.tfm.afac.data.model.GuideEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GuideMapper {

    GuideMapper INSTANCIA= Mappers.getMapper(GuideMapper.class);

    GuideDto guideEntityToGuideDto(GuideEntity guideEntity);
    GuideEntity guideDTOToGuideEntity(GuideDto guideDto);
}
