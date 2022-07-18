package com.csinfotechbd.legal.schedulers;

import com.csinfotechbd.collection.settings.branch.Branch;
import com.csinfotechbd.collection.settings.branch.BranchService;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfo;
import com.csinfotechbd.legal.dataEntry.caseEntry.LitigationCaseInfoService;
import com.csinfotechbd.legal.log.mailHistoryLog.LegalMailHistory;
import com.csinfotechbd.legal.log.mailHistoryLog.LegalMailHistoryRepository;
import com.csinfotechbd.legal.setup.legalDivisionInfo.LegalDivisionInfo;
import com.csinfotechbd.legal.setup.legalDivisionInfo.LegalDivisionInfoRepository;
import com.csinfotechbd.mailing.Mail;
import com.csinfotechbd.mailing.SendMailService;
import com.csinfotechbd.utillity.DateUtils;
import com.csinfotechbd.utillity.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@EnableAsync
@Component
@RequiredArgsConstructor
public class LegalSchedulerCornJobs {

    private final LitigationCaseInfoService litigationCaseInfoService;
    private final BranchService branchService;
    private final SendMailService mailService;
    private final LegalMailHistoryRepository legalMailHistoryRepository;
    private final LegalDivisionInfoRepository legalDivisionInfoRepository;
    private final DateUtils dateUtils;

    @Scheduled(cron = "0 0 6 * * *")
    public void sendMailsForMissingNextDates() {
        List<LitigationCaseInfo> litigationCaseInfoList = litigationCaseInfoService.getListForExpiredNextDate();

        List<LegalDivisionInfo> divisionInfos = legalDivisionInfoRepository.findAll();
        LegalDivisionInfo divisionInfo = divisionInfos.isEmpty() ? new LegalDivisionInfo() : divisionInfos.get(0);
        Map<String, Branch> branches = branchService.getActiveList().stream().collect(Collectors.toMap(Branch::getBranchCode, Function.identity()));

        Map<String, List<LitigationCaseInfo>> branchWiseCases = new LinkedHashMap<>();

        for (LitigationCaseInfo suit : litigationCaseInfoList) {
            Branch branch = branches.get(suit.getBranchCode());
            if (branch == null || !StringUtils.hasText(branch.getEmail())) continue;
            List<LitigationCaseInfo> caseList = branchWiseCases.containsKey(branch.getEmail()) ? branchWiseCases.get(branch.getEmail()) : new LinkedList<>();
            caseList.add(suit);
            String email = branch.getEmail();
            branchWiseCases.put(email, caseList);
        }

        branchWiseCases.forEach(this::sendCaseViaEmail);
        sendCaseViaEmail(divisionInfo.getHodEmail(), litigationCaseInfoList);
    }

    private StringBuilder getMessageForSuitsWithExpiredNextDate(List<LitigationCaseInfo> litigationCaseInfoList) {
        StringBuilder stringBuilder = new StringBuilder();
        if (litigationCaseInfoList == null || litigationCaseInfoList.isEmpty()) return stringBuilder;

        stringBuilder.append("Dear Sir/Madam,\n");
        String info = "This is an auto generated mail from Case Management System to inform you that next hearing dates for the following " + litigationCaseInfoList.size() + " law suits have been expired for more than 3 days. The associated parties need to update the next hearing dates for those cases as soon as possible for operational integrity. \n\n";
        stringBuilder.append(info);
        stringBuilder.append("SL#\t|\tLD No.\t|\tCase Number\t\t|\tBranch Name\t\t|\tPrevious Date\n");
        stringBuilder.append("---------------------------------------------------------------------------------");

        int count = 0;

        for (LitigationCaseInfo suit : litigationCaseInfoList) {
            stringBuilder.append(++count);
            stringBuilder.append("\t|\t");
            stringBuilder.append(suit.getLdNo() == null ? " " : suit.getLdNo());
            stringBuilder.append("\t|\t");
            stringBuilder.append(suit.getCaseNumber() == null ? " " : suit.getCaseNumber());
            stringBuilder.append("\t\t|\t");
            stringBuilder.append(suit.getBranchName() == null ? " " : suit.getBranchName());
            stringBuilder.append("\t\t|\t");
            stringBuilder.append(dateUtils.getFormattedDateString(suit.getNextDate(), "dd-MM-yyyy"));
            stringBuilder.append("\n");
            stringBuilder.append("---------------------------------------------------------------------------------");
            stringBuilder.append("\n");
        }
        stringBuilder.append("\nFor any information, please contact with the administrator.\n");
        return stringBuilder;
    }

    public void sendCaseViaEmail(String emailAddress, List<LitigationCaseInfo> litigationCaseInfoList) {
        StringBuilder message = getMessageForSuitsWithExpiredNextDate(litigationCaseInfoList);

        Mail mail = new Mail();
        mail.setRecipient(emailAddress);
        mail.setMessage(message.toString());
        mail.setSubject("Notification for Expired Next Date");

        try {
            if (!litigationCaseInfoList.isEmpty()) {
                mailService.sendMail(mail);
                storeLogForSystemGeneratedEmail(mail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void storeLogForSystemGeneratedEmail(Mail mail) {
        LegalMailHistory legalMailHistory = new LegalMailHistory();
        legalMailHistory.setMailType(mail.getSubject());
        legalMailHistory.setMailFrom("System");
        legalMailHistory.setMailTo(mail.getRecipient());
        legalMailHistory.setMailMessage(mail.getMessage());

        legalMailHistoryRepository.save(legalMailHistory);
    }

}
