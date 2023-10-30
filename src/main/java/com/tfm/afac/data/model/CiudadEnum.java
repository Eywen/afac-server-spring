package com.tfm.afac.data.model;

public enum CiudadEnum {
    Buga("Buga"),
    Bugalagrande("Bugalagrande"),
    Cali("Cali"),
    Candelaria("Candelaria"),
    LaUnion("La Union"),
    Palmira("Palmira"),
    PuertoTejada("Puerto Tejada"),
    Pradera("Pradera"),
    Roldanillo("Roldanillo"),
    Santander("Santander"),
    Sevilla("Sevilla"),
    Tulua("Tulua");

    private String ciudad;

    private CiudadEnum(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCiudad() {
        return ciudad;
    }
}
