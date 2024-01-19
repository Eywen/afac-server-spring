package com.tfm.afac.data.model;

public enum SearchGuideOptionEnum {
    STATE("Status", "Estado"),
    ENTRY_DATE("EntryDate","Fecha entrada"),
    ASSIGNMENT_DATE("AssignmentDate", "Fecha cargue"),
    DELIVERY_DATE("DeliveryDate", "Fecha entrega");

    private String search;
    private String strState;

    private SearchGuideOptionEnum(String search, String strState) {

        this.search = search;
        this.strState = strState;
    }

    public String getSearch() {
        return this.search;
    }

    public String getStrState() {
        return this.strState;
    }
}
