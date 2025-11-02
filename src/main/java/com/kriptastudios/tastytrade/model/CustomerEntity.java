package com.kriptastudios.tastytrade.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerEntity extends TastytradeData {
    // Dataclass containing customer entity information

    private String id;
    private Address address;
    private String businessNature;
    private String email;
    private List<EntityOfficer> entityOfficers;
    private EntitySuitability entitySuitability;
    private String entityType;
    private String foreignInstitution;
    private String grantorBirthDate;
    private String grantorEmail;
    private String grantorFirstName;
    private String grantorLastName;
    private String grantorMiddleName;
    private String grantorTaxNumber;
    private String hasForeignBankAffiliation;
    private String hasForeignInstitutionAffiliation;
    private boolean isDomestic;
    private String legal_name;
    private String phoneNumber;
    private String taxNumber;

}
