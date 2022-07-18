package com.csinfotechbd.retail.card.dataEntry.reasonDelinquencyCard;

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import com.csinfotechbd.collection.settings.employee.EmployeeService;
import com.csinfotechbd.user.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReasonDelinquencyCardServiceImpl implements ReasonDelinquencyCardService{

    @Autowired
    private ReasonDelinquencyCardRepository reasonDelinquencyCardRepository;
    @Autowired
    private EmployeeService employeeService;


    @Override
    public ReasonDelinquencyCard save(ReasonDelinquencyCard reasonDelinquencyCard) {
        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeInfoEntity employeeInfoEntity = employeeService.getByPin(user.getUsername());
        reasonDelinquencyCard.setDealerName(employeeInfoEntity.getUser().getFirstName()+" "+ employeeInfoEntity.getUser().getLastName());
        return reasonDelinquencyCardRepository.save(reasonDelinquencyCard);
    }

    @Override
    public List<ReasonDelinquencyCard> findReasonDelinquencyCardById(Long id) {
        return reasonDelinquencyCardRepository.findReasonDelinquenciesCardByCustomerId(id);
    }
}
