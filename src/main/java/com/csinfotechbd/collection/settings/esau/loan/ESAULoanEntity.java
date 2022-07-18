package com.csinfotechbd.collection.settings.esau.loan;
/*
  Created by Md. Monirul Islam on 9/9/2019
*/

import com.csinfotechbd.base.BaseInfo;
import com.csinfotechbd.collection.settings.dpdBucket.DPDBucketEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

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
