package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "FILE_FOLLOW_UP_AUCTION_STAYED")
public class AuctionStayedEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long fileFollowUpId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private Date auctionStayedDate;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "file_follow_up_auction_stayed_id")
    @JsonBackReference
    @JsonIgnore
    private LitigationFileFollowUp litigationFileFollowUp;
}
