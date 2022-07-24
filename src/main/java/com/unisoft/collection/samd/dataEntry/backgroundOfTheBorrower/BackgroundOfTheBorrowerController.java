package com.unisoft.collection.samd.dataEntry.backgroundOfTheBorrower;

import com.unisoft.user.UserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/samd/backgound-borrower")
public class BackgroundOfTheBorrowerController {

    private final BackgroundOfTheBorrowerRepository repository;

    @GetMapping("/create")
    public BackgroundOfTheBorrower save(@RequestParam(value = "id", required = true) String accountNumber) {

        BackgroundOfTheBorrower backgroundOfTheBorrowerEntity = new BackgroundOfTheBorrower();

        List<BackgroundOfTheBorrower> backgroundOfTheBorrowerList = repository.findAllByAccountNumber(accountNumber);

        if (backgroundOfTheBorrowerList.size() >0){
            backgroundOfTheBorrowerEntity = backgroundOfTheBorrowerList.get(0);
        }

        return backgroundOfTheBorrowerEntity;
    }



    @PostMapping("/save")
    public BackgroundOfTheBorrower setBackgroundOfTheBorrower(@Valid @RequestBody BackgroundOfTheBorrowerPayload backgroundOfTheBorrowerPayload) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BackgroundOfTheBorrower backgroundOfTheBorrower = new BackgroundOfTheBorrower();

        BackgroundOfTheBorrower backgroundOfTheBorrowerEntity = new BackgroundOfTheBorrower();

        List<BackgroundOfTheBorrower> backgroundOfTheBorrowerList = repository.findAllByAccountNumber(backgroundOfTheBorrowerPayload.getAccount());

        if (backgroundOfTheBorrowerList.size() >0){
            backgroundOfTheBorrowerEntity = backgroundOfTheBorrowerList.get(0);
        }


        if (backgroundOfTheBorrowerEntity.getId() ==null){
            backgroundOfTheBorrower.setCreatedBy(user.getUsername());
            backgroundOfTheBorrower.setCreatedDate(new Date());

            backgroundOfTheBorrower.setAccountNumber(backgroundOfTheBorrowerPayload.getAccount());
            backgroundOfTheBorrower.setBackgroundOftheBorrower(backgroundOfTheBorrowerPayload.getBackgroundOfTheBorrower());

            repository.save(backgroundOfTheBorrower);
        }else {

            BackgroundOfTheBorrower oldEntity = repository.getOne(backgroundOfTheBorrowerEntity.getId());
            BackgroundOfTheBorrower previousEntity = new BackgroundOfTheBorrower();
            BeanUtils.copyProperties(oldEntity, previousEntity);

            previousEntity.setAccountNumber(backgroundOfTheBorrowerPayload.getAccount());
            previousEntity.setBackgroundOftheBorrower(backgroundOfTheBorrowerPayload.getBackgroundOfTheBorrower());

            previousEntity.setCreatedBy(oldEntity.getCreatedBy());
            previousEntity.setCreatedDate(oldEntity.getCreatedDate());
            previousEntity.setModifiedBy(user.getUsername());
            previousEntity.setModifiedDate(new Date());
            repository.save(previousEntity);

        }

        return null;
    }
}
