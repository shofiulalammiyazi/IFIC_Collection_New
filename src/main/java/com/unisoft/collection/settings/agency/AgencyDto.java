package com.unisoft.collection.settings.agency;

import lombok.Data;

@Data
public class AgencyDto {

    private String name;

    private String firstName;
    private String lastName;

    private String pin;

    private String contactPerson;

    private String contactNo;

    private String registeredAddress;

    private String agreementWithAgency;
    private String password;
}
