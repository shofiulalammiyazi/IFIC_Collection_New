package com.csinfotechbd.legal.dataEntry.fileFollowUp;

import com.csinfotechbd.loanApi.model.BranchInfo;
import com.csinfotechbd.loanApi.model.CustomerInfo;
import com.csinfotechbd.loanApi.model.LoanAccDetails;
import com.csinfotechbd.loanApi.service.RetailLoanUcbApiService;
import com.csinfotechbd.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.Revisions;
import org.springframework.stereotype.Service;
import com.csinfotechbd.utillity.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LitigationFileFollowUpServiceImpl implements LitigationFileFollowUpService {

    private final LitigationFileFollowUpRepository repository;
    private final RetailLoanUcbApiService retailLoanUcbApiService;

    @Override
    public LitigationFileFollowUpDto getAccountInfoFromApi(String accountNo) {

        LitigationFileFollowUpDto followUpDto = new LitigationFileFollowUpDto();
        LoanAccDetails accDetails = new LoanAccDetails();
        if (StringUtils.hasText(accountNo) && accountNo.length() == 16)
            accDetails = retailLoanUcbApiService.getLoanAccountDetails(accountNo);
        BranchInfo branchInfo = new BranchInfo();
        if (StringUtils.hasText(accDetails.getBranchCode()))
            branchInfo = retailLoanUcbApiService.getBranchInfo(accDetails.getBranchCode());
        CustomerInfo customerInfo = new CustomerInfo();
        if (StringUtils.hasText(accDetails.getCustomerCifNumber()))
            customerInfo = retailLoanUcbApiService.getCustomerInfo(accDetails.getCustomerCifNumber());

        followUpDto.setBranchName(branchInfo.getBranchName());
        followUpDto.setBranchCode(accDetails.getBranchCode());
        followUpDto.setAccountName(accDetails.getAccountName());
        followUpDto.setLoanOutstanding(accDetails.getOutStandingLocalCurrency());
        followUpDto.setCustomerCifNumber(accDetails.getCustomerCifNumber());
        followUpDto.setCustomerMobileNumber(customerInfo.getMobileNumber());

        return followUpDto;
    }

    @Override
    public String save(LitigationFileFollowUp litigationFileFollowUp) {
        if (litigationFileFollowUp.getId() == null) {
//            if (litigationFileFollowUp.getLdNo() == null) {
//                long ldNo = repository.findMaxLdNo(3000L) + 1; // Default Ld No 3000
//                litigationFileFollowUp.setLdNo(ldNo);
//            }
            litigationFileFollowUp.setCreatedBy(UserService.getSessionUsername());
            litigationFileFollowUp.setCreatedDate(new Date());
            litigationFileFollowUp.setEnabled(false);

        } else {
            //LitigationFileFollowUp oldEntry = repository.getOne(litigationFileFollowUp.getId());
            litigationFileFollowUp.setModifiedBy(UserService.getSessionUsername());
            litigationFileFollowUp.setModifiedDate(new Date());

        }

        repository.save(litigationFileFollowUp);

        if (litigationFileFollowUp.getId() != null) {
            litigationFileFollowUp.getFileFollowUpAccountResheduledList().forEach(item ->{
                LitigationFileFollowUp litigationFileFollowUp1 = new LitigationFileFollowUp();
                item.setModifiedDate(litigationFileFollowUp.getModifiedDate());
                item.setModifiedBy(litigationFileFollowUp.getModifiedBy());


                item.setLitigationFileFollowUp(litigationFileFollowUp1);
            });

            litigationFileFollowUp.getFileFollowUpLegalNoticeServedForArtharinSuits().forEach(item ->{
                LitigationFileFollowUp litigationFileFollowUp1 = new LitigationFileFollowUp();

                item.setModifiedDate(litigationFileFollowUp.getModifiedDate());
                item.setModifiedBy(litigationFileFollowUp.getModifiedBy());

                item.setLitigationFileFollowUp(litigationFileFollowUp1);
            });

            litigationFileFollowUp.getFileFollowUpLegalNoticeServedNIActs().forEach(item ->{
                LitigationFileFollowUp litigationFileFollowUp1 = new LitigationFileFollowUp();

                item.setModifiedDate(litigationFileFollowUp.getModifiedDate());
                item.setModifiedBy(litigationFileFollowUp.getModifiedBy());

                item.setLitigationFileFollowUp(litigationFileFollowUp1);
            });
        }

        litigationFileFollowUp.getFileFollowUpAccountResheduledList().forEach(item ->{
            LitigationFileFollowUp litigationFileFollowUp1 = new LitigationFileFollowUp();
            litigationFileFollowUp1.setId(litigationFileFollowUp.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationFileFollowUp.getCreatedBy());

            item.setLitigationFileFollowUp(litigationFileFollowUp1);
        });

        litigationFileFollowUp.getFileFollowUpLegalNoticeServedForArtharinSuits().forEach(item ->{
            LitigationFileFollowUp litigationFileFollowUp2 = new LitigationFileFollowUp();

            litigationFileFollowUp2.setId(litigationFileFollowUp.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationFileFollowUp.getCreatedBy());

            item.setLitigationFileFollowUp(litigationFileFollowUp2);
        });

        litigationFileFollowUp.getFileFollowUpLegalNoticeServedNIActs().forEach(item ->{
            LitigationFileFollowUp litigationFileFollowUp2 = new LitigationFileFollowUp();

            litigationFileFollowUp2.setId(litigationFileFollowUp.getId());
            item.setCreatedDate(new Date());
            item.setCreatedBy(litigationFileFollowUp.getCreatedBy());

            item.setLitigationFileFollowUp(litigationFileFollowUp2);
        });

        repository.save(litigationFileFollowUp);
        return "1";
    }

    @Override
    public List<LitigationFileFollowUp> findAll() {
        return repository.findAll();
    }

    @Override
    public List<LitigationFileFollowUp> findByEnabled(boolean enabled) {
        return null;
    }

    @Override
    public Page<LitigationFileFollowUp> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public LitigationFileFollowUp findById(Long id) {
        return repository.findById(id).orElse(new LitigationFileFollowUp());
    }

    @Override
    public Page<LitigationFileFollowUp> getLitigationFileFollowUpByAccountNumber(String accountNo, Pageable pageable) {
        return repository.findByLoanAccountNumberAndEnabled(accountNo, true, pageable);
    }

    @Override
    public List<LitigationFileFollowUp> getByAccountNumber(String accountNo) {
        return repository.findByLoanAccountNumber(accountNo);
    }

    @Override
    public Page<Revision<Integer, LitigationFileFollowUp>> getRevisions(Long id, Pageable pageable) {
        return repository.findRevisions(id, pageable);
    }

    @Override
    public List<LitigationFileFollowUp> getRevisions(Long id) {
        Revisions<Integer, LitigationFileFollowUp> litigationRevisionLists = repository.findRevisions(id);
        return litigationRevisionLists.stream().map(Revision::getEntity).collect(Collectors.toList());
    }


//
//    @Override
//    public String save(LitigationFileFollowUp litigationFileFollowUp) {
//        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        boolean existingFileFollowUp = repository
//                .existsByCustomerAccountNumberAndEnabled(litigationFileFollowUp.getCustomerAccountNumber(), true);
//        litigationFileFollowUp.setLastModifiedEntry(true);
//        if (!existingFileFollowUp) {
//            litigationFileFollowUp.setCreatedBy(user.getUsername());
//            litigationFileFollowUp.setCreatedDate(new Date());
//            long ldNo = repository.findMaxLdNo(3000L) + 1; // Default Ld No 3000
//            litigationFileFollowUp.setLdNo(ldNo);
//        } else {
//            setModifyingValues(litigationFileFollowUp);
//            litigationFileFollowUp.setModifiedBy(user.getUsername());
//            litigationFileFollowUp.setModifiedDate(new Date());
//        }
//
//        repository.save(litigationFileFollowUp);
//
//        return "1";
//    }
//
//    @Override
//    public Page<LitigationFileFollowUp> getModificationHistory(String customerAccountNumber, boolean isLastModified, boolean enabled, Pageable pageable) {
//        return repository
//                .findByCustomerAccountNumberAndLastModifiedEntryAndEnabled(customerAccountNumber, isLastModified, enabled, pageable);
//    }
//
//    @Override
//    public LitigationFileFollowUp getLastModifiedEntry(String customerAccountNumber, boolean isLastModifiedEntry, boolean enabled) {
//        return repository
//                .findByCustomerAccountNumberAndLastModifiedEntryAndEnabled(customerAccountNumber, true, true)
//                .orElse(new LitigationFileFollowUp());
//    }
//
//    @Override
//    public Page<LitigationFileFollowUp> getInitialList(String customerAccountNumber, Pageable pageable) {
//        return repository
//                .findByCustomerAccountNumberAndEnabled(customerAccountNumber, true, pageable);
//    }
//
//    @Override
//    public Page<LitigationFileFollowUp> getActiveList(Pageable pageable) {
//        return repository.findByLastModifiedEntryAndEnabled(true, true, pageable);
//    }
//
//    private void setModifyingValues(LitigationFileFollowUp litigationFileFollowUp) {
//        litigationFileFollowUp.setId(null);
//        repository.updateTheLastModifiedEntry(litigationFileFollowUp.getCustomerAccountNumber());
//        if (!litigationFileFollowUp.isEnabled())
//            repository.updateEnableState(
//                    litigationFileFollowUp.getCustomerAccountNumber(),
//                    litigationFileFollowUp.isEnabled()
//            );
//    }
//

}
