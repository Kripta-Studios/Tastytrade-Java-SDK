package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Customer extends TastytradeData {
    // Dataclass containing customer information

    private String id;
    private String firstName;
    private String firstSurname;
    private String lastName;
    private Address address;
    private CustomerSuitability customerSuitability;
    private Address mailingAddress;
    private boolean isForeign;
    private String regulatoryDomain;
    private String usaCitizenshipType;
    private String homePhoneNumber;
    private String mobilePhoneNumber;
    private String workPhoneNumber;
    private Date birthDate;
    private String email;
    private String externalId;
    private String taxNumber;
    private String taxNumberType;
    private String citizenshipCountry;
    private boolean agreedToMargining;
    private boolean subjectToTaxWithholding;
    private boolean agreedToTerms;
    private String extCrmId;
    private boolean hasIndustryAffiliation;
    private boolean hasListedAffiliation;
    private boolean hasPoliticalAffiliation;
    private boolean hasDelayedQuotes;
    private boolean hasPendingOrApprovedApplication;
    private boolean isProfessional;
    private List<CustomerAccountType> permittedAccountTypes;
    private Date createdAt;
    private String identifiableType;
    private CustomerPerson person;
    private String gender;
    private String middleName;
    private String prefixName;
    private String secondSurname;
    private String suffixName;
    private String foreignTaxNumber;
    private String birthCountry;
    private Date visaExpirationDate;
    private String visaType;
    private String signatureOfAgreement;
    private String deskCustomerId;
    private CustomerEntity entity;
    private String familyMemberNames;
    private String hasInstitutionalAssets;
    private String industryAffiliationFirm;
    private boolean isInvestmentAdviser;
    private String listedAffiliationSymbol;
    private String politicalOrganization;
    private String userId;

}
