package com.tfm.afac.data.model;

public enum CiudadEnum {
    BUGA("Buga"),
    BUGALAGRANDE("Bugalagrande"),
    CALI("Cali"),
    CANDELARIA("Candelaria"),
    LA_UNION("La Union"),
    PALMIRA("Palmira"),
    PUERTO_TEJADA("Puerto Tejada"),
    PRADERA("Pradera"),
    ROLDANILLO("Roldanillo"),
    SANTANDER("Santander"),
    SEVILLA("Sevilla"),
    TULUA("Tulua");

    private String ciudad;

    private CiudadEnum(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }
}
