package com.unisoft.collection.samd.dataEntry.updateAddress;

import com.unisoft.customerbasicinfo.CustomerBasicInfoEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UpdateAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String borrBusiHome;
    private String borrBusiPlot;
    private String borrBusivill;
    private String borrBusiPO;
    private String borrBusiPS;
    private String borrBusiDis;
    private String borrBusiMoNo;


    private String borrPresHome;
    private String borrPresPlot;
    private String borrPresvill;
    private String borrPresPO;
    private String borrPresPS;
    private String borrPresDis;
    private String borrPresMoNo;



    private String borrPerHome;
    private String borrPerPlot;
    private String borrPervill;
    private String borrPerPO;
    private String borrPerPS;
    private String borrPerDis;
    private String borrPerMoNo;



    private String guaranOnePresenHome;
    private String guaranOnePresenPlot;
    private String guaranOnePresenvill;
    private String guaranOnePresenPO;
    private String guaranOnePresenPS;
    private String guaranOnePresenDis;
    private String guaranOnePresenMoNo;


    private String guaranTwoPresenHome;
    private String guaranTwoPresenPlot;
    private String guaranTwoPresenvill;
    private String guaranTwoPresenPO;
    private String guaranTwoPresenPS;
    private String guaranTwoPresenDis;
    private String guaranTwoPresenMoNo;


    private String guaranThreePresenHome;
    private String guaranThreePresenPlot;
    private String guaranThreePresenvill;
    private String guaranThreePresenPO;
    private String guaranThreePresenPS;
    private String guaranThreePresenDis;
    private String guaranThreePresenMoNo;


    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private CustomerBasicInfoEntity customerBasicInfoEntity;
}
