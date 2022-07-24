package com.unisoft.retail.loan.dataEntry.writeOffAccountDistribution;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface WriteOffAccountDistributionService {
    List<WriteOffAccountDistribution> findAll();

    void storeData(MultipartFile file);

    List<WriteOffAccountDistribution> findByDealerPinAndLatest(String pin);

    List<WriteOffAccountdistributionSummary> findCurrentMonthDealerWriteOffAccountDistribution(String username);
}
