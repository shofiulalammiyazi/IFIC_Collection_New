package com.unisoft.collection.samd.setup.categoryForDelinquency;


import com.unisoft.common.CommonEntity;
import lombok.Data;

import javax.persistence.*;

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
