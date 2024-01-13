package com.tfm.afac.services.business;

import com.tfm.afac.api.dtos.GuideDto;
import com.tfm.afac.data.model.SearchGuideOptionEnum;

import java.util.Date;
import java.util.List;

public interface GuideService {
    GuideDto create(GuideDto guideDto);

    GuideDto update(GuideDto guideDto);

    GuideDto findByIdGuide(Long id);

    List<GuideDto> findByEmployeeId(Integer id);

    List<GuideDto> findByCustomerId(Integer id);

    List<GuideDto> findByStatus(String status);

    List<GuideDto> findByEntryDate(Date entryDate);

    List<GuideDto> findByDeliveryDate(Date deliveryDate);

    List<GuideDto> findByAssignmentDate(Date assignmentDate);

    List<GuideDto> findBySearchOption(SearchGuideOptionEnum searchGuideOption, String searchValue);
}
