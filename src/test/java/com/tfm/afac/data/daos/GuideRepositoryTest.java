package com.tfm.afac.data.daos;

import com.tfm.afac.TestConfig;
import com.tfm.afac.data.model.GuideEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class GuideRepositoryTest {

    @Autowired
    GuideRepository guideRepository;

    private Date myDate;

    @BeforeEach
    void onInit() throws ParseException {
        myDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("2023-11-28");
    }

    @Test
    void findByIdGuideTest(){
        assertTrue(this.guideRepository.findByIdGuide(1).stream()
                .anyMatch(feature ->
                        isGuideId1(feature)
                ));
    }

    @Test
    void findByEmployeeIdTest(){
        assertTrue(this.guideRepository.findByEmployeeId(1).stream()
                .anyMatch(feature ->
                        isGuideId1(feature)
                ));
    }
    @Test
    void findByCustomerIdTest(){
        assertTrue(this.guideRepository.findByCustomerId(1).stream()
                .anyMatch(feature ->
                        isGuideId1(feature)
                ));
    }

    @Test
    void findByStatusTest(){
        assertTrue(this.guideRepository.findByStatus("reparto").stream()
                .anyMatch(feature ->
                        isGuideId1(feature)
                ));
    }
    @Test
    void findByEntryDateTest(){

        assertTrue(this.guideRepository.findByEntryDate(myDate).stream()
                .anyMatch(feature ->
                        isGuideId1(feature)
                ));
    }

    @Test
    void findByDeliveryDateTest(){

        assertTrue(this.guideRepository.findByEntryDate(myDate).stream()
                .anyMatch(feature ->
                        isGuideId1(feature)
                ));
    }

    private static boolean isGuideId1(GuideEntity feature) {
        return "destinatario1".equals(feature.getRecipient()) &&
                1 == feature.getIdGuide() &&
                feature.getAddress().equals("calle 1 # 1-1") &&
                feature.getCity().equals("Palmira") &&
                feature.getStatus().equals("reparto") &&
                1111111111 == feature.getTelephone() ;
    }
}
