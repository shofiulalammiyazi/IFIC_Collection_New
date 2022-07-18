package com.csinfotechbd.collection.settings.monthlyworkingday;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MonthlyWorkingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "monthly_working_day_id", nullable = false)
    private List<MonthlyWorkingDayDetails> detailsList = new ArrayList<>();

    public MonthlyWorkingDay() {
    }

    public MonthlyWorkingDay(String name, List<MonthlyWorkingDayDetails> detailsList) {
        this.name = name;
        this.detailsList = detailsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MonthlyWorkingDayDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<MonthlyWorkingDayDetails> detailsList) {
        this.detailsList = detailsList;
    }
}
