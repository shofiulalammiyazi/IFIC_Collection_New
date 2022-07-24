package com.unisoft.collection.samd.dataEntry.cardDistribution;

import lombok.Data;

@Data
public class SamCardDistributionDTO {
    private Long dealerId;
    private Long[] selectedIds;
}
