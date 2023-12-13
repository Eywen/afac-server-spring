package com.tfm.afac.services.business;

import com.tfm.afac.TestConfig;
import com.tfm.afac.api.dtos.GuideDto;
import com.tfm.afac.data.daos.GuideRepository;
import com.tfm.afac.data.model.GuideEntity;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import com.tfm.afac.services.mapper.GuideMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@TestConfig
public class GuideServiceTest {

    @Autowired
    private GuideService guideService;

    @MockBean
    private GuideRepository guideRepository;

    private GuideDto guideDto;

    private GuideEntity guide;

    private List<GuideDto> guideDtoList;
    private List<GuideEntity> guideEntityList;

    @BeforeEach
    public void onInit() {
        MockitoAnnotations.openMocks(this);
        guideDto = GuideDto.builder()
                .idGuide(1)
                .recipient("destinatario2")
                .address("calle 2 # 2-2")
                .city("Palmira")
                .telephone(2222222222L)
                .activate(true)
                .status("reparto")
                .entryDate(new Date())
                /*.idEmployee(2)
                .idCustomer(2)*/
                .build();

        guideDtoList = new ArrayList<>();
        guideEntityList = new ArrayList<>();
        guideDtoList.add(guideDto);


        guide = GuideMapper.INSTANCIA.guideDTOToGuideEntity(guideDto);
        guideEntityList.add(guide);
    }

    @Test
    void CreateGuideTest() {

        GuideEntity guide = GuideMapper.INSTANCIA.guideDTOToGuideEntity(guideDto);
        when(guideRepository.save(any (GuideEntity.class))).thenReturn(guide);
        GuideDto result = this.guideService.create(guideDto);

        assertNotNull(result);
        assertEquals(guideDto.getIdGuide(), result.getIdGuide());
        verify(guideRepository, times(1)).findByIdGuide(anyLong());
        verify(guideRepository, times(1)).save(any(GuideEntity.class));
    }

    @Test
    void createWithForbiddenExceptionTest(){

        when(guideRepository.findByIdGuide(anyLong()))
                .thenThrow(ForbiddenException.class);
        assertThrows(ForbiddenException.class, () -> guideService.create(guideDto));
    }

    @Test
    void updateGuideTest(){

        when(guideRepository.findByIdGuide(anyLong()))
                .thenReturn(Optional.of(guide));
        guideDto.setAddress("modificado");
        when(guideRepository.save(any (GuideEntity.class))).thenReturn(guide);
        assertNotNull( this.guideService.update(guideDto));
        verify(guideRepository, times(1)).findByIdGuide(anyLong());
        verify(guideRepository, times(1)).save(any(GuideEntity.class));
    }

    @Test
    void updateWithNotFoundExceptionTest(){
        when(guideRepository.findByIdGuide(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> guideService.update(guideDto));
        verify(guideRepository, times(1)).findByIdGuide(anyLong());
    }
    @Test
    void findByIdGuideTest(){
        when(guideRepository.findByIdGuide(anyLong()))
                .thenReturn(Optional.of(guide));
        this.guideService.findByIdGuide(1L);
        assertNotNull( this.guideRepository.findByIdGuide(guideDto.getIdGuide()));
    }

    @Test
    void findByIdWithNotFoundExceptionTest(){
        when(guideRepository.findByIdGuide(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> guideService.update(guideDto));
    }

    /////////////
    @Test
    void findByEmployeeIdTest(){
        when(guideRepository.findByEmployeeId(anyInt())).thenReturn(guideEntityList);
        List<GuideDto> guideDtos = guideService.findByEmployeeId(1);
        assertNotNull(guideDtos);
        verify(guideRepository, times(1)).findByEmployeeId(anyInt());
    }

    @Test
    void findByEmployeeIdEmptyTest(){
        when(guideRepository.findByEmployeeId(anyInt())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> guideService.findByEmployeeId(5));
    }

    @Test
    void findByCustomerIdTest(){
        when(guideRepository.findByCustomerId(anyInt())).thenReturn(guideEntityList);
        List<GuideDto> guideDtos = guideService.findByCustomerId(1);
        assertNotNull(guideDtos);
        verify(guideRepository, times(1)).findByCustomerId(anyInt());
    }

    @Test
    void findByCustomerIdEmptyTest(){
        when(guideRepository.findByCustomerId(anyInt())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> guideService.findByCustomerId(5));
    }

    @Test
    void findByStatusTest(){
        when(guideRepository.findByStatus(anyString())).thenReturn(guideEntityList);
        List<GuideDto> guideDtos = guideService.findByStatus("reparto");
        assertNotNull(guideDtos);
        verify(guideRepository, times(1)).findByStatus(anyString());
    }

    @Test
    void findByStatusEmptyTest(){
        when(guideRepository.findByStatus(anyString())).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> guideService.findByStatus("reparto"));
    }

    @Test
    void findByEntryDateTest() {
        when(guideRepository.findByEntryDate(any(Date.class))).thenReturn(guideEntityList);
        Date entryDate = new Date();
        List<GuideDto> guideDtos = guideService.findByEntryDate(entryDate);
        assertNotNull(guideDtos);
        verify(guideRepository, times(1)).findByEntryDate(entryDate);
    }

    @Test
    void findByEntryDateEmptyTest(){
        when(guideRepository.findByEntryDate(any(Date.class))).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> guideService.findByEntryDate(new Date()));
    }

    @Test
    void findByDeliveryDateTest() {
        when(guideRepository.findByDeliveryDate(any(Date.class))).thenReturn(guideEntityList);
        Date inst = new Date();
        List<GuideDto> guideDtos = guideService.findByDeliveryDate(inst);
        assertNotNull(guideDtos);
        verify(guideRepository, times(1)).findByDeliveryDate(inst);
    }

    @Test
    void findByDeliveryDateEmptyTest(){
        when(guideRepository.findByDeliveryDate(any(Date.class))).thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, () -> guideService.findByDeliveryDate(new Date()));
    }
}
