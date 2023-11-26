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
public class CustomerDto implements Serializable {

    private static final long serialVersionUID = -5759634642592243768L;

    private int id;
    private String customerName;
    private String city;
    private String address;
    private long telephone;
    private Date iniDate;
    private Date finishDate;
    private Date closeMonthDate;
    private boolean activate;
}
