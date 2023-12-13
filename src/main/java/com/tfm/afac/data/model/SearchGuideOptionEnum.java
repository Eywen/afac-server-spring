package com.tfm.afac.data.model;

public enum SearchGuideOptionEnum {
    STATE("status"),
    ENTRY_DATE("entryDate"),
    ASSIGNMENT_DATE("assignmentDate"),
    DELIVERY_DATE("deliveryDate");

    private String search;

    private SearchGuideOptionEnum(String status) {
        this.search = status;
    }

    public String getSearch() {
        return search;
    }
}
