package com.unisoft.collection.distribution.card;
/*
Created by   Islam at 9/29/2019
*/

import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionRepository;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountService;
import com.unisoft.retail.card.dataEntry.distribution.accountBasicInfo.CardAccountBasicInfo;
import com.unisoft.retail.card.dataEntry.distribution.accountDistributionInfo.CardAccountDistributionInfo;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.employee.EmployeeService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/distribution/card/unallocated/")
public class CardUnallocatedController {

    private CardAccountService cardAccountService;

    private CardAccountDistributionRepository cardAccountDistributionRepository;

    private EmployeeService employeeService;

    private AgencyService agencyService;

    @GetMapping("list")
    public String cardUnallocatedList(Model model) {
        LocalDate today = LocalDate.now();

        LocalDate startDateOfMonth = today.withDayOfMonth(1);
        Date startDate = Date.from(startDateOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        LocalDate endDateOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        String endDateOfMonthString = endDateOfMonth.toString();
        endDateOfMonthString = endDateOfMonthString + " 11:59 PM";

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(endDateOfMonthString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }


        List<CardAccountDistributionInfo> byCreatedDateLessThan = cardAccountDistributionRepository.findByCreatedDateIsBetweenAndDealerPinAndSupervisorPin(
                startDate, endDate, "0", "0");
        List<CardViewModel> cardViewModels = new ArrayList<>();

        for (CardAccountDistributionInfo l : byCreatedDateLessThan) {
            CardViewModel cardViewModel = getCardViewModelForBasicInfo(l, new CardViewModel());
            cardViewModels.add(cardViewModel);
        }

        Gson gson = new Gson();
        model.addAttribute("cardlist", cardViewModels);
        model.addAttribute("cardviewlistJson", gson.toJson(cardViewModels));
        model.addAttribute("dealerList", employeeService.getDealerList());
        model.addAttribute("agencyList", agencyService.getAll());
        return "collection/distribution/card/cardunallocatedlist";
    }

    private CardViewModel getCardViewModelForBasicInfo(CardAccountDistributionInfo cardAccountDistributionInfo, CardViewModel cardViewModel) {
        CardAccountInfo cardAccountInfo = getCardAccountInfo(cardAccountDistributionInfo.getCardAccountBasicInfo());

        cardViewModel.setAccountNo(cardAccountDistributionInfo.getCardAccountBasicInfo().getCardNo());
        cardViewModel.setCustomerId(cardAccountDistributionInfo.getCardAccountBasicInfo().getClientId());
        cardViewModel.setCustomerName(cardAccountDistributionInfo.getCardAccountBasicInfo().getCardName());
        cardViewModel.setAgeCode(cardAccountInfo.getMoAgeCode());
        cardViewModel.setLocation(cardAccountDistributionInfo.getCardAccountBasicInfo().getLocation());
        cardViewModel.setOutstandingAmount(cardAccountInfo.getMoAccOutstanding() + "");
        cardViewModel.setSupervisorName(cardAccountDistributionInfo.getSupervisorName());
        cardViewModel.setDelaerName(cardAccountDistributionInfo.getDealerName());
        return cardViewModel;
    }

    private CardAccountInfo getCardAccountInfo(CardAccountBasicInfo cardAccountBasicInfo) {
        List<CardAccountInfo> byCardAccountBasicId = cardAccountService.findByCardAccountBasicId(cardAccountBasicInfo);
        Collections.sort(byCardAccountBasicId, new Comparator<CardAccountInfo>() {
            @Override
            public int compare(CardAccountInfo o1, CardAccountInfo o2) {
                if (o1.getCreatedDate() == null || o2.getCreatedDate() == null)
                    return 0;
                return o1.getCreatedDate().compareTo(o2.getCreatedDate());
            }
        });
        int index = byCardAccountBasicId.size() - 1;
        return byCardAccountBasicId.get(index);
    }
}
