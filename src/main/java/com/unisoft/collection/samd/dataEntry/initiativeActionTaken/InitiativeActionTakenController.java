package com.unisoft.collection.samd.dataEntry.initiativeActionTaken;

import com.unisoft.user.UserPrincipal;
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
@RequestMapping(value = "/collection/samd/initiative-action-taken")
public class InitiativeActionTakenController {

    private final InitiativeActionTakenRepository repository;

    @GetMapping("/create")
    public InitiativeActionTaken save(Model model, @RequestParam(value = "id", required = true) String accountNumber) {

        InitiativeActionTaken initiativeActionTakenEntity= new InitiativeActionTaken();

        List<InitiativeActionTaken> initiativeActionTakenList = repository.findAllByAccountNumber(accountNumber);

        if (initiativeActionTakenList.size() >0){
            initiativeActionTakenEntity = initiativeActionTakenList.get(0);
        }
        /*model.addAttribute("borrowerPresentPropositionEntity", borrowerPresentPropositionEntity);

        if (borrowerPresentPropositionEntity.getId() != null) {
            model.addAttribute("borrowerPresentPropositionEntity",borrowerPresentPropositionEntity);
        }*/

        return initiativeActionTakenEntity;
    }

    @PostMapping("/save")
    public InitiativeActionTaken save(@Valid @RequestBody InitiativeActionTakenPayload initiativeActionTakenPayload) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        InitiativeActionTaken initiativeActionTaken = new InitiativeActionTaken();

        InitiativeActionTaken initiativeActionTakenEntity = new InitiativeActionTaken();

        List<InitiativeActionTaken> initiativeActionTakenList = repository.findAllByAccountNumber(initiativeActionTakenPayload.getAccount());

        if (initiativeActionTakenList.size() >0){
            initiativeActionTakenEntity = initiativeActionTakenList.get(0);
        }


        if (initiativeActionTakenEntity.getId() ==null){
            initiativeActionTaken.setCreatedBy(user.getUsername());
            initiativeActionTaken.setCreatedDate(new Date());

            initiativeActionTaken.setAccountNumber(initiativeActionTakenPayload.getAccount());
            initiativeActionTaken.setInitiativeActionTaken(initiativeActionTakenPayload.getInitiativeActionTaken());
            initiativeActionTaken.setDealerId(user.getEmpId());

            repository.save(initiativeActionTaken);
        }else {

            InitiativeActionTaken oldEntity = repository.getOne(initiativeActionTakenEntity.getId());
            InitiativeActionTaken previousEntity = new InitiativeActionTaken();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            previousEntity.setAccountNumber(initiativeActionTakenPayload.getAccount());
            previousEntity.setInitiativeActionTaken(initiativeActionTakenPayload.getInitiativeActionTaken());


            previousEntity.setCreatedBy(oldEntity.getCreatedBy());
            previousEntity.setCreatedDate(oldEntity.getCreatedDate());
            previousEntity.setModifiedBy(user.getUsername());
            previousEntity.setModifiedDate(new Date());
            repository.save(previousEntity);

        }

        return null;
    }

}
