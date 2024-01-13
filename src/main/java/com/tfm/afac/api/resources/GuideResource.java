package com.tfm.afac.api.resources;

import com.tfm.afac.api.dtos.GuideDto;
import com.tfm.afac.data.model.SearchGuideOptionEnum;
import com.tfm.afac.data.model.StatusGuideEnum;
import com.tfm.afac.services.business.GuideService;
import com.tfm.afac.services.exceptions.ForbiddenException;
import com.tfm.afac.services.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(GuideResource.GUIDE)
public class GuideResource {

    private static final Logger log = LoggerFactory.getLogger(GuideResource.class);
    public static final String GUIDE = "/guide";
    public static final String ID = "/{id}";
    private static final String EMPLOYEE = "/employee";
    private static final String CUSTOMER = "/customer";
    private static final String ENTRY_DATE = "/entrydate";
    private static final String DATE = "/{date}";
    private static final String DELIVERY_DATE = "/deliverydate";
    private static final String DISABLE = "/disable";
    private static final String GUIDE_ID = "/{guideid}";
    private static final String STATUS = "/status";
    private static final String SEARCH_OPTION = "/searchoption";
    private static final String SEARCH = "/search";
    private static final String SEARCH_VALUE = "/{searchvalue}";
    private static final String OPTION = "/{searchoption}";

    @Autowired
    private GuideService guideService;


    @SecurityRequirement(name = "basicAuth")
    @PostMapping
    public ResponseEntity<GuideDto> addGuide(@Valid @RequestBody GuideDto guideDto){

        try {
            GuideDto createdGuide = guideService.create(guideDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGuide);
        } catch (ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(guideDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(ID)
    public ResponseEntity<GuideDto> update (@PathVariable Long id,@Valid @RequestBody GuideDto guideDto){
        try {
            GuideDto createdGuide = guideService.update(guideDto);
            return ResponseEntity.status(HttpStatus.OK).body(createdGuide);
        }  catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(ID)
    public ResponseEntity<GuideDto> findById (@PathVariable Long id){
        try {
            GuideDto createdGuide = guideService.findByIdGuide(id);
            return ResponseEntity.status(HttpStatus.OK).body(createdGuide);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GuideDto());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GuideDto());
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(EMPLOYEE + ID)
    public ResponseEntity<List<GuideDto>> findByEmployeeId (@PathVariable Integer id){
        try {
            List<GuideDto> guideByEmployeeIdList = guideService.findByEmployeeId(id);
            return ResponseEntity.status(HttpStatus.OK).body(guideByEmployeeIdList);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(CUSTOMER + ID)
    public ResponseEntity<List<GuideDto>> findByCustomerId (@PathVariable Integer id){
        try {
            List<GuideDto> guideByEmployeeIdList = guideService.findByCustomerId(id);
            return ResponseEntity.status(HttpStatus.OK).body(guideByEmployeeIdList);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    /*@SecurityRequirement(name = "basicAuth")
    @GetMapping( )
    public ResponseEntity<List<GuideDto>> findByStatus (@RequestParam String status){

        try {
            /*if (Arrays.stream(StatusGuideEnum.values())
                    .map(elem -> elem.getStatus())
                    .collect(Collectors.toList()).contains(status)
            ){*/
               /* List<GuideDto> createdGuide = guideService.findByStatus(status);
                return ResponseEntity.status(HttpStatus.OK).body(createdGuide);
           // }
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
       /* } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    /*@SecurityRequirement(name = "basicAuth")
    @GetMapping(ENTRY_DATE + DATE)
    public ResponseEntity<List<GuideDto>> findByEntryDate (@PathVariable String date){

        try {
            Date entryDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(date);
            List<GuideDto> createdGuide = guideService.findByEntryDate(entryDate);
            return ResponseEntity.status(HttpStatus.OK).body(createdGuide);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    /*@SecurityRequirement(name = "basicAuth")
    @GetMapping(DELIVERY_DATE + DATE)
    public ResponseEntity<List<GuideDto>> findByDeliveryDate (@PathVariable String date){
        System.out.println("strDate: "+date);
        try {
            Date deliveryDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(date);
            System.out.println("Searching guides : "+deliveryDate);
            List<GuideDto> deliveryDateGuides = guideService.findByDeliveryDate(deliveryDate);
            return ResponseEntity.status(HttpStatus.OK).body(deliveryDateGuides);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }*/

    @SecurityRequirement(name = "basicAuth")
    @PutMapping(DISABLE + GUIDE_ID)
    public ResponseEntity<GuideDto> disable (@PathVariable Long guideid){
        try {
            GuideDto guide = guideService.findByIdGuide(guideid);
            if (null != guide){
                guide.setActivate(false);
                GuideDto updatedGuide = guideService.update(guide);
                return ResponseEntity.status(HttpStatus.OK).body(updatedGuide);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(SEARCH + OPTION + SEARCH_VALUE)
    public ResponseEntity<List<GuideDto>> searhOption (@PathVariable String searchoption, @PathVariable String searchvalue){
        try {
            List<GuideDto> guide = guideService.findBySearchOption(SearchGuideOptionEnum.valueOf(searchoption),searchvalue);
            if (!CollectionUtils.isEmpty(guide)){
                return ResponseEntity.status(HttpStatus.OK).body(guide);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping(STATUS)
    public ResponseEntity<List<String>> getStatus (){
        return ResponseEntity.status(HttpStatus.OK)
                .body(Stream.of(StatusGuideEnum.values())
                        .map(StatusGuideEnum::getStatus)
                        .collect(Collectors.toList()));
    }

    @SecurityRequirement(name = "basicAuth")
    @GetMapping( SEARCH + SEARCH_OPTION)
    public ResponseEntity<List<Map<String, String>>> getSearchOption (){
        //List<SearchGuideOptionEnum> list = Arrays.asList(SearchGuideOptionEnum.values());
        /*return ResponseEntity.status(HttpStatus.OK)
                .body(Stream.of(SearchGuideOptionEnum.values())
                        .map(SearchGuideOptionEnum::getSearch)
                        .collect(Collectors.toList()));*/
        List<Map<String, String>> list = Stream.of(SearchGuideOptionEnum.values()).parallel().map(temp -> {
            Map<String, String> obj = new HashMap<String, String>();
            obj.put("search", temp.getSearch());
            obj.put("strState", temp.getStrState());
            obj.put("name", temp.name());
            return obj;
        }).collect(Collectors.toList());


        return ResponseEntity.status(HttpStatus.OK)
                .body(list);
    }



}
