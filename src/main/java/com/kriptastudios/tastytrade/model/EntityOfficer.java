package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EntityOfficer {
    // Dataclass containing entity officer information.

    private String id;
    private String externalId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String prefixName;
    private String suffixName;
    private Address address;
    private String birthCountry;
    private Date birthDate;
    private String citizenshipCountry;
    private String email;
    private String employerName;
    private String employmentStatus;
    private String homePhoneNumber;
    private boolean isForeign;
    private String jobTitle;
    private String maritalStatus;
    private String mobilePhoneNumber;
    private int numberOfDependents;
    private String occupation;
    private boolean ownerOfRecord;
    private String relationshipToEntity;
    private String taxNumber;
    private String taxNumberType;
    private String usaCitizenshipType;
    private Date visaExpirationDate;
    private String visaType;
    private String workPhoneNumber;

}