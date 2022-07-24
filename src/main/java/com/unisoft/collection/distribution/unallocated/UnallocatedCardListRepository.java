package com.unisoft.collection.distribution.unallocated;

import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UnallocatedCardListRepository extends JpaRepository<UnallocatedCardList, Long> {

    List<UnallocatedCardList> findByCreatedDateLessThanAndSamAccountNotInAndWriteOffAccountNotInAndProductGroupNotInAndVvipNotInAndSecureCardNotIn(
            Date startDate, List<String> samAccount, List<String> writeOff, List<String> productGroup, List<String> vvip, List<String> secureCard
    );

    UnallocatedCardList findFirstByCardAccountBasicInfo(CardAccountBasicInfo cardAccountBasicInfo);
}
