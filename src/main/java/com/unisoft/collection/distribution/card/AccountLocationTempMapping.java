package com.unisoft.collection.distribution.card;
/*
Created by   Islam at 9/25/2019
*/

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "acc_loc_map_temp")
public class AccountLocationTempMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customerId;

    private String locationName;

    private String custComAddress;
}
