package com.kriptastudios.tastytrade.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.ToString;

/**
 * Base class for all the data models objects (DTOs) of Tastytrade.
 * Purpose:
 * 1. Provide a 'toString()' base (similar to __str__ in Python) using Lombok.
 * 2. Configures Jackson so it *automatically* translates
 * names of fields in Java (camelCase) to JSON (kebab-case/dasher-case).
 * Ex: 'accountNumber' in Java will be mapped to 'account-number' in JSON.
 * 3. Ignores unknows fields in the JSON, making the SDK more robust
 * to changes in the API.
 */
@ToString
@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TastytradeData {
    // This class is internationally left empty.
    // Its only purpose is providing this configuration of annotations
    // to all model classes that extend it.
}