package com.unisoft.retail.card.dataEntry.reasonDelinquencyCard;

import com.unisoft.audittrail.AuditTrailService;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.unisoft.user.UserPrincipal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/collection/card/reasonForDelinquency")
public class ReasonDelinquencyCardController {

    @Autowired
    private ReasonDelinquencyCardRepository reasonDelinquencyCardRepository;

    @Autowired
    private ReasonDelinquencyCardService reasonDelinquencyCardService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/save")
    @ResponseBody
    public ReasonDelinquencyCard save(ReasonDelinquencyCard reasonDelinquencyCard){
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        reasonDelinquencyCard.setDealerName(employeeInfoEntity.getUser().getFirstName()+" "+ employeeInfoEntity.getUser().getLastName());

        if (reasonDelinquencyCard.getId()==null){
            reasonDelinquencyCard.setCreatedBy(user.getUsername());
            reasonDelinquencyCard.setCreatedDate(new Date());
            ReasonDelinquencyCard reasonDelinquencyCard1 = reasonDelinquencyCardService.save(reasonDelinquencyCard);
            auditTrailService.saveCreatedData("Reason for Delinquency - Card ", reasonDelinquencyCard1);
        }
        else {
            ReasonDelinquencyCard oldEntity = reasonDelinquencyCardRepository.getOne(reasonDelinquencyCard.getId());
            ReasonDelinquencyCard previousEntity = new ReasonDelinquencyCard();
            BeanUtils.copyProperties(oldEntity,previousEntity);

            reasonDelinquencyCard.setCreatedBy(user.getUsername());
            reasonDelinquencyCard.setCreatedDate(new Date());
            reasonDelinquencyCardRepository.save(reasonDelinquencyCard);
            auditTrailService.saveUpdatedData("Reason for Delinquency - Card ",oldEntity,previousEntity);
        }
        ReasonDelinquencyCard reasonDelinquencyCard1 = reasonDelinquencyCardService.save(reasonDelinquencyCard);
        return reasonDelinquencyCard1;

    }

    @GetMapping(value = "/findBy")
    @ResponseBody
    public List<ReasonDelinquencyCard> getById(@RequestParam Long id){
        List<ReasonDelinquencyCard> reasonDelinquencyCards = reasonDelinquencyCardService.findReasonDelinquencyCardById(id);
        return reasonDelinquencyCards;
    }

}
