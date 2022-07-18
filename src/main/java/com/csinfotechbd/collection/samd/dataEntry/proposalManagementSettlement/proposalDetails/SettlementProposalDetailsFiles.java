package com.csinfotechbd.collection.samd.dataEntry.proposalManagementSettlement.proposalDetails;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SettlementProposalDetailsFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;
    private String dmsFileId;
    private String dmsFileType;

    @OneToOne(cascade = CascadeType.ALL)
    private SettlementProposalDetails settlementProposalDetails;


    @Transient
    private Long settlementProposalDetailsId;

    public SettlementProposalDetails getSettlementProposalDetails() {
        return settlementProposalDetails = (settlementProposalDetails != null) ? settlementProposalDetails : new SettlementProposalDetails();
    }

    public void setSettlementProposalDetails(SettlementProposalDetails settlementProposalDetails) {
        this.settlementProposalDetails = new SettlementProposalDetails(settlementProposalDetailsId);
    }
}
