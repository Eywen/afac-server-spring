package com.tfm.afac.api.dtos;

import com.tfm.afac.data.model.StatusGuideEnum;
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
public class GuideDto implements Serializable {

    private static final long serialVersionUID = -5759634342592245748L;

    private long idGuide;
    private String recipient;
    private String address;
    private String city;
    private long telephone;
    private boolean activate;
    private String status;
    private Date entryDate;
    private Date deliveryDate;
    private int idCustomer;
    private int idEmployee;
}
