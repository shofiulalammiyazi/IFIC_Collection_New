package com.csinfotechbd.collection.samd.setup.dpdSamd;

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class DpdSamdEntity extends BaseInfo {
    
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String dpdName;
}
