package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.GuideDto;
import com.tfm.afac.services.business.GuideService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RestTestConfig
class GuideResourceTest {

    @Autowired
    private GuideResource guideResource;

    @MockBean
    private GuideService guideService;

    private GuideDto guideDto;
    private List<GuideDto> guideDtoList;

    @BeforeEach
    public void onInit() {
        guideDto = GuideDto.builder()
                .idGuide(2)
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
        guideDtoList.add(guideDto);
    }

    @Test
    void testAddGuide() {
        when(guideService.create(any(GuideDto.class))).thenReturn(guideDto);

        ResponseEntity<GuideDto> response = guideResource.addGuide(guideDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(guideDto, response.getBody());
        verify(guideService, times(1)).create(any(GuideDto.class));
    }

    @Test
    void testUpdate() {

        when(guideService.update(any(GuideDto.class))).thenReturn(guideDto);

        long guideId = 1;
        when(guideService.update(any(GuideDto.class))).thenReturn(guideDto);

        ResponseEntity<GuideDto> responseEntity = guideResource.update(guideId, guideDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(guideDto, responseEntity.getBody());

        verify(guideService, times(1)).update(any(GuideDto.class));
    }

    @Test
    void testFindById() {

        when(guideService.findByIdGuide(anyLong())).thenReturn(guideDto);

        ResponseEntity<GuideDto> response = guideResource.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guideDto, response.getBody());

        verify(guideService, times(1)).findByIdGuide(anyLong());
    }

    @Test
    void testFindByEmployeeId() {

        when(guideService.findByEmployeeId(anyInt())).thenReturn(guideDtoList);

        ResponseEntity<List<GuideDto>> response = guideResource.findByEmployeeId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guideDtoList, response.getBody());
    }

    @Test
    void testFindByCustomerId() {

        when(guideService.findByCustomerId(anyInt())).thenReturn(guideDtoList);

        ResponseEntity<List<GuideDto>> response = guideResource.findByCustomerId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guideDtoList, response.getBody());
    }


    @Test
    void addGuideForbiddenExceptionTest() {

        when(guideService.create(any(GuideDto.class))).thenThrow(ForbiddenException.class);
        ResponseEntity<GuideDto> responseEntity = guideResource.addGuide(guideDto);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(guideDto, responseEntity.getBody());
        verify(guideService, times(1)).create(any(GuideDto.class));
    }

    @Test
    void updateNotFoundExceptionTest() {

        long guideId = 1;
        when(guideService.update(any(GuideDto.class))).thenThrow(NotFoundException.class);

        ResponseEntity<GuideDto> responseEntity = guideResource.update(guideId, guideDto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(null, responseEntity.getBody());

        verify(guideService, times(1)).update(any(GuideDto.class));
    }

    @Test
    void testDisable() {
        long guideId = 1;
        GuideDto guideDto = guideDtoList.get(0);

        when(guideService.findByIdGuide(guideId)).thenReturn(guideDto);
        when(guideService.update(any(GuideDto.class))).thenReturn(guideDto);

        ResponseEntity<GuideDto> responseEntity = guideResource.disable(guideId);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertFalse(responseEntity.getBody().isActivate());

        verify(guideService, times(1)).findByIdGuide(anyLong());
        verify(guideService, times(1)).update(any(GuideDto.class));
    }

    @Test
    void testSearchOption() {
        String searchOption = "DELIVERY_DATE";
        String searchValue = "DeliveryDate";
        List<GuideDto> guideDtoList = new ArrayList<>();
        guideDtoList.add(guideDto);

        when(guideService.findBySearchOption(any(), any())).thenReturn(guideDtoList);

        ResponseEntity<List<GuideDto>> responseEntity = guideResource.searhOption(searchOption, searchValue);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(guideDtoList, responseEntity.getBody());

        verify(guideService, times(1)).findBySearchOption(any(), any());
    }

    @Test
    void testGetStatus() {
        ResponseEntity<List<String>> responseEntity = guideResource.getStatus();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        //verify(guideService, never()).isEmpty(); // No interaction with guideService
    }

    @Test
    void testGetSearchOption() {
        ResponseEntity<List<Map<String, String>>> responseEntity = guideResource.getSearchOption();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

    }

}
