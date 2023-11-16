package com.tfm.afac.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeDto implements Serializable {

    private static final long serialVersionUID = -5759634642592245748L;

    private int id;
    private String employeeName;
    private String lastName1;
    private String lastName2;
    private long cedula;
    private String city;
    private String address;
    private long telephone;
    private Date iniDate;
    private Date finishDate;
    private boolean activate;
}
