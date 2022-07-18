package com.csinfotechbd.letterGeneration;

import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntity;
import com.csinfotechbd.customerbasicinfo.CustomerBasicInfoEntityRepository;
import com.csinfotechbd.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailedLetterHistoriesService {

    private EmailedLetterHistoriesRepository emailedLetterHistoriesRepository;

    private CustomerBasicInfoEntityRepository customerBasicInfoEntityRepository;

    public Long insertData(EmailedLetterHistories letterHistories) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomerBasicInfoEntity customerBasicInfoEntity = customerBasicInfoEntityRepository.findFirstByAccountNoOrderByAccountNoAsc(letterHistories.getAccountNo());

        letterHistories.setCustomerId(customerBasicInfoEntity.getId());
        letterHistories.setCreatedBy(principal.getUsername());
        letterHistories.setCreatedDate(new Date());

        emailedLetterHistoriesRepository.save(letterHistories);

        return letterHistories.getId();
    }

    public List<EmailedLetterHistories> getByCustomerId(Long customerId) {
        return emailedLetterHistoriesRepository.findByCustomerIdOrderByCreatedDateDesc(customerId);
    }
}
