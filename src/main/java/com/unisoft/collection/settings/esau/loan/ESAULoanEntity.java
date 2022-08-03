package com.unisoft.collection.settings.esau.loan;
/*
  Created by Md.   Islam on 9/9/2019
*/

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ESAU_RATING_LOAN")
public class ESAULoanEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double finalAvgUpperLimit = 0.0;
    private Double finalAvgLowerLimit = 0.0;
    private String ratingName;
}
