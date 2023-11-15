package com.tfm.afac.api.resources;

import com.tfm.afac.data.model.CiudadEnum;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(CiudadResource.CIUDAD)
public class CiudadResource {
    public static final String CIUDAD = "/ciudad";

    @SecurityRequirement(name = "basicAuth")
    @GetMapping
    public ResponseEntity<List<String>> getCiudades (){


        return ResponseEntity.status(HttpStatus.OK)
                .body(Stream.of(CiudadEnum.values())
                        .map(CiudadEnum::getCiudad)
                        .collect(Collectors.toList()));
    }

}
