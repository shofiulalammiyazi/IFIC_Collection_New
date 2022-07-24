package com.unisoft.collection.settings.securedCardCriteria;


/*Created by Nabil, assisted by Monir, Onjon on 19 August, 2019 */

import com.unisoft.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class SecuredCardCriteriaEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String rule1[];

    private String rule2[];
}
