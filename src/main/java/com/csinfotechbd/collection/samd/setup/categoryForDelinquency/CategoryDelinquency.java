package com.csinfotechbd.collection.samd.setup.categoryForDelinquency;


import com.csinfotechbd.collection.samd.dataEntry.monitoringFollowUp.MonitoringAndFollowUp;
import com.csinfotechbd.collection.samd.dataEntry.monitoringFollowUp.MonitoringAndFollowUpCategoryDelinquency;
import com.csinfotechbd.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CategoryDelinquency extends CommonEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    private String name;

//    @OneToMany(mappedBy = "categoryDelinquency",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//    private List<MonitoringAndFollowUpCategoryDelinquency> monitoringAndFollowUpCategoryDelinquencyList = new ArrayList<>();


    public CategoryDelinquency() {
    }

    public CategoryDelinquency(Long id) {
        this.setId(id);
    }
}
