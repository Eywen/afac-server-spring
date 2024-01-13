package com.tfm.afac.data.model;

public enum StatusGuideEnum {

    DELIVERED("Entregado"),
    RETURN("Devuelto"),
    REPORTED_COMPANY("Reportado empresa"),

    DELIVERY("reparto"),
    ENTRY("entrada");

    private String status;

    private StatusGuideEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
