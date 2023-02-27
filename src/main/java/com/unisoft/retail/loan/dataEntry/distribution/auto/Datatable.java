package com.unisoft.retail.loan.dataEntry.distribution.auto;

import lombok.Data;

import java.util.List;

@Data
public class Datatable<T> {

    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;


    // setter and getter ...

}
