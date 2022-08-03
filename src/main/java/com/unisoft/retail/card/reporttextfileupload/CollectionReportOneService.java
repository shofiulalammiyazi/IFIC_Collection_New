package com.unisoft.retail.card.reporttextfileupload;

import com.unisoft.detailsOfCollection.cardviewmodels.RecoveryViewDTO;
import com.unisoft.retail.card.dataEntry.distribution.accountInfo.CardAccountDTO;
import com.unisoft.utillity.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionReportOneService {
    @Autowired
    DateUtils dateUtils;

    @Autowired
    private CollectionReportOneRepository collectionReportOneRepository;
    @Autowired
    private ApproveTransactionsUnbilledTransactionsPaymentRepository approveTransactionsUnbilledTransactionsPaymentRepository;

    public List<UnbilledTransactionDetailsDTO> findAllByContractNo(String contractNo){
        List<UnbilledTransactionDetailsDTO> collectionReportUnbilledDTOS = new ArrayList<>();
        List<Tuple> tuples = collectionReportOneRepository.findAllUnbilledByContractNo(contractNo);

        for(Tuple t : tuples){
            UnbilledTransactionDetailsDTO collectionReportUnbilledDTO = new UnbilledTransactionDetailsDTO();

            collectionReportUnbilledDTO.setCardNo(t.get("CARD_NO") == null ? "" : t.get("CARD_NO").toString());
            collectionReportUnbilledDTO.setTransactionPostDate(t.get("POST_DATE") == null ? "" : t.get("POST_DATE").toString());
            collectionReportUnbilledDTO.setTransactionDate(t.get("TXN_DATE") == null ? "" : t.get("TXN_DATE").toString());
            collectionReportUnbilledDTO.setTransactionCode(t.get("APPROVAL_CODE") == null ? "" : t.get("APPROVAL_CODE").toString());
            collectionReportUnbilledDTO.setDescription(t.get("TXN_DESCRIPTION") == null ? "" : t.get("TXN_DESCRIPTION").toString());
            collectionReportUnbilledDTO.setCurrency(t.get("TXN_CURRENCY") == null ? "" : t.get("TXN_CURRENCY").toString());
            collectionReportUnbilledDTO.setBdtUnbilledAmount(t.get("TXN_AMOUNT") == null ? "" : t.get("TXN_AMOUNT").toString());
            collectionReportUnbilledDTO.setBillingCurrency(t.get("BILLING_CURRENC") == null ? "" : t.get("BILLING_CURRENC").toString());
            collectionReportUnbilledDTO.setBillingAmount(t.get("BILLING_AMOUNT") == null ? "" : t.get("BILLING_AMOUNT").toString());
            collectionReportUnbilledDTO.setMerchantCode(t.get("TERM_LOCATION") == null ? "" : t.get("TERM_LOCATION").toString());

            collectionReportUnbilledDTOS.add(collectionReportUnbilledDTO);
        }
        return collectionReportUnbilledDTOS;
    }


    public List<TransactionApprovedHistoryDTO> findAllTrxnApprovalHistoryByContractNo(String contractNo){
        List<TransactionApprovedHistoryDTO> transactionApprovedHistoryDTOS = new ArrayList<>();
        List<Tuple> tuples = collectionReportOneRepository.findAllTrxnApprovalHistoryByContractNo(contractNo);

        for(Tuple t : tuples){
            TransactionApprovedHistoryDTO transactionApprovedHistoryDTO = new TransactionApprovedHistoryDTO();

            transactionApprovedHistoryDTO.setDate(t.get("TXN_DATE") == null ? "" : t.get("TXN_DATE").toString().substring(0,12));
            transactionApprovedHistoryDTO.setTime(t.get("TXN_DATE") == null ? "" : t.get("TXN_DATE").toString().substring(12));

            transactionApprovedHistoryDTO.setCardNo(t.get("CARD_NO") == null ? "" : t.get("CARD_NO").toString());
            transactionApprovedHistoryDTO.setTransactionCurrencyCode(t.get("TXN_CURRENCY") == null ? "" : t.get("TXN_CURRENCY").toString());
            transactionApprovedHistoryDTO.setTransactionAmount(t.get("TXN_AMOUNT") == null ? "" : t.get("TXN_AMOUNT").toString());
            transactionApprovedHistoryDTO.setTransactionOriginalCountry(t.get("TERM_LOCATION") == null ? "" : t.get("TERM_LOCATION").toString());
            transactionApprovedHistoryDTO.setAuthorizedBillingCurrencyCode(t.get("BILLING_CURRENC") == null ? "" : t.get("BILLING_CURRENC").toString());
            transactionApprovedHistoryDTO.setAuthorizedBillingAmount(t.get("BILLING_AMOUNT") == null ? "" : t.get("BILLING_AMOUNT").toString());
            transactionApprovedHistoryDTO.setMerchantDescription(t.get("TXN_DESCRIPTION") == null ? "" : t.get("TXN_DESCRIPTION").toString());
            transactionApprovedHistoryDTO.setMcc(t.get("MCC") == null ? "" : t.get("MCC").toString());
            transactionApprovedHistoryDTO.setCnpSettlementBillingAmount(t.get("TXN_TYPE") == null ? "" : t.get("TXN_TYPE").toString());

            transactionApprovedHistoryDTO.setPosEntryMode(t.get("POS_ENTRY") == null ? "" : t.get("POS_ENTRY").toString());
            transactionApprovedHistoryDTO.setSettledIndicator(t.get("S_INDICATOR") == null ? "" : t.get("S_INDICATOR").toString());




            transactionApprovedHistoryDTOS.add(transactionApprovedHistoryDTO);
        }
        return transactionApprovedHistoryDTOS;
    }

    public List<ApproveTransactionsUnbilledTransactionsPayment>getLastThreeMonthTransactionApprovalHistory(String contractNo){
        return approveTransactionsUnbilledTransactionsPaymentRepository.getLastThreeMonthData(contractNo);
    }

    public List<ApproveTransactionsUnbilledTransactionsPayment>getSearchApproveTransactionPaymentDataList(String contractNo,String startDate,String endDate){
        return approveTransactionsUnbilledTransactionsPaymentRepository.getSearchApproveTransactionPaymentData(contractNo,startDate,endDate );
    }


    public CardAccountDTO findCardAccountInfoByContractNo(String contractNo){
       Tuple tuple = collectionReportOneRepository.findCardAccountInfoByContractNo(contractNo);
        CardAccountDTO cardAccountDTO = new CardAccountDTO();

        if(tuple !=null){
            cardAccountDTO.setCardNo(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
            cardAccountDTO.setAutoPayAcBdt(tuple.get("AUTO_PAY_AC_BDT") == null ? "" : tuple.get("AUTO_PAY_AC_BDT").toString());
            cardAccountDTO.setCardNo(tuple.get("AUTO_PAY_AC_USD") == null ? "" : tuple.get("AUTO_PAY_AC_USD").toString());
        }

        return cardAccountDTO;
    }

    public RecoveryViewDTO findRecoveryViewDTOByContractNo(String autoPayBdt, String cardNo){
        Tuple tuple = collectionReportOneRepository.findRecoveryViewDTOByContractNo(autoPayBdt, cardNo);

        RecoveryViewDTO recoveryViewDTO = new RecoveryViewDTO();

        recoveryViewDTO.setAvailableBalanceCashBdt(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setAvailableBalancePurchaseBdt(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setAutoPayAcAvailableBalanceBdt(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setLastPaymentAmountBdt(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setLastPaymentDateBdt(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setAvailableBalanceCashUsd(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setAvailableBalancePurchaseUsd(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setAutoPayAcAvailableBalanceUsd(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());

        recoveryViewDTO.setLastPaymentAmountUsd(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setLastPaymentDateUsd(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        recoveryViewDTO.setTotalPaymentDateUsd(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());

        return recoveryViewDTO;


    }

}
