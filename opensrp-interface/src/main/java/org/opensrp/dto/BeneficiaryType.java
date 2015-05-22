package org.opensrp.dto;

public enum BeneficiaryType {
    child("child"),
    mother("mother"),
    ec("ec"),
    elco("elco"),
    hh("hh");
    private String value;

    BeneficiaryType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static BeneficiaryType from(String beneficiaryType) {
        return valueOf(beneficiaryType);
    }
}
