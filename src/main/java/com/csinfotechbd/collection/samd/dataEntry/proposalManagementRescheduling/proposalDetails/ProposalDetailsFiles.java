package com.csinfotechbd.collection.samd.dataEntry.proposalManagementRescheduling.proposalDetails;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class ProposalDetailsFiles {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;
    private String dmsFileId;
    private String dmsFileType;

    @OneToOne(cascade = CascadeType.ALL)
    private ProposalDetails proposalDetails;


    @Transient
    private Long proposalDetailsId;

    public ProposalDetails getProposalDetails() {
        return proposalDetails = (proposalDetails != null) ? proposalDetails : new ProposalDetails();
    }

    public void setProposalDetails(ProposalDetails proposalDetails) {
        this.proposalDetails = new ProposalDetails(proposalDetailsId);
    }
}
