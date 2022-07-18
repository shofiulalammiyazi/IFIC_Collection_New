package com.csinfotechbd.collection.samd.dataEntry.borrowerPresentProposition;


import com.csinfotechbd.collection.samd.dataEntry.reasonForClassification.ReasonForClassification;
import com.csinfotechbd.collection.samd.dataEntry.reasonForClassification.ReasonForClassificationPayload;
import com.csinfotechbd.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/samd/borrower-present-proposition")
public class BorrowerPresentPropositionController {

    private final BorrowerPresentPropositionRepository repository;

    @GetMapping("/create")
    public BorrowerPresentProposition save(Model model, @RequestParam(value = "id", required = true) String accountNumber) {

        BorrowerPresentProposition borrowerPresentPropositionEntity = new BorrowerPresentProposition();

        List<BorrowerPresentProposition> borrowerPresentPropositionList = repository.findAllByAccountNumber(accountNumber);

        if (borrowerPresentPropositionList.size() >0){
            borrowerPresentPropositionEntity = borrowerPresentPropositionList.get(0);
        }
        model.addAttribute("borrowerPresentPropositionEntity", borrowerPresentPropositionEntity);

        if (borrowerPresentPropositionEntity.getId() != null) {
            model.addAttribute("borrowerPresentPropositionEntity",borrowerPresentPropositionEntity);
        }

        return borrowerPresentPropositionEntity;
    }

    @PostMapping("/save")
    public BorrowerPresentProposition save(@Valid @RequestBody BorrowerPresentPropositionPayload borrowerPresentPropositionPayload) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BorrowerPresentProposition borrowerPresentProposition = new BorrowerPresentProposition();

        BorrowerPresentProposition borrowerPresentPropositionEntity = new BorrowerPresentProposition();

        List<BorrowerPresentProposition> borrowerPresentPropositionList = repository.findAllByAccountNumber(borrowerPresentPropositionPayload.getAccount());

        if (borrowerPresentPropositionList.size() >0){
            borrowerPresentPropositionEntity = borrowerPresentPropositionList.get(0);
        }


        if (borrowerPresentPropositionEntity.getId() ==null){
            borrowerPresentProposition.setCreatedBy(user.getUsername());
            borrowerPresentProposition.setCreatedDate(new Date());

            borrowerPresentProposition.setAccountNumber(borrowerPresentPropositionPayload.getAccount());
            borrowerPresentProposition.setBorrowerPresentProposition(borrowerPresentPropositionPayload.getBorrowerPresentProposition());
            borrowerPresentProposition.setDealerId(user.getEmpId());

            repository.save(borrowerPresentProposition);
        }else {

            BorrowerPresentProposition oldEntity = repository.getOne(borrowerPresentPropositionEntity.getId());
            BorrowerPresentProposition previousEntity = new BorrowerPresentProposition();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            previousEntity.setAccountNumber(borrowerPresentPropositionPayload.getAccount());
            previousEntity.setBorrowerPresentProposition(borrowerPresentPropositionPayload.getBorrowerPresentProposition());


            previousEntity.setCreatedBy(oldEntity.getCreatedBy());
            previousEntity.setCreatedDate(oldEntity.getCreatedDate());
            previousEntity.setModifiedBy(user.getUsername());
            previousEntity.setModifiedDate(new Date());
            repository.save(previousEntity);

        }

        return null;
    }


}
