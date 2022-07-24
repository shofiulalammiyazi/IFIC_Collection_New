package com.unisoft.collection.settings.division;
/*
Created by   Islam at 6/19/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class DivisionEntity extends BaseInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long divId;
    @Column(unique = true)
    @NotBlank(message = "Please enter division code")
    private String divCode;

    @NotBlank(message = "Please enter division name!")
    private String divName;
    private String divDescription;

    /**
     * Provides Json string from the entity
     *
     * @return Json string
     */
    @Override
    public String toString() {
        return "{'divId':" + divId + ", 'divCode':'" + divCode + "', 'divName':'" + divName + "'}";
    }
}
