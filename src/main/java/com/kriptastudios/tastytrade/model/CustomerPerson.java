package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerPerson extends TastytradeData {
    // Dataclass containing customer person information

    private String externalId;
    private String firstName;
    private String lastName;
    private String citizenshipCountry;
    private String usaCitizenshipType;
    private String employmentStatus;
    private String maritalStatus;
    private int numberOfDependents;
    private String occupation;
    private String middleName;
    private String prefixName;
    private String suffixName;
    private String birthCountry;
    private Date birthDate;
    private Date visaExpirationDate;
    private String visaType;
    private String employerName;
    private String jobTitle;

}
