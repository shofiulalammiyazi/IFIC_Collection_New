package com.unisoft.retail.card.reporttextfileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionReportTwoService {

    @Autowired
    private CollectionReportTwoRepository collectionReportTwoRepository;

    public List<CollectionReportUnbilledDTO> findAllByContractNo(String contractNo){
        List<CollectionReportUnbilledDTO> collectionReportUnbilledDTOS = new ArrayList<>();
        List<Tuple> tuples = collectionReportTwoRepository.findAllUnbilledByContractNo(contractNo);

        for(Tuple t : tuples){
            CollectionReportUnbilledDTO collectionReportUnbilledDTO = new CollectionReportUnbilledDTO();

            collectionReportUnbilledDTO.setFirstTxnDate(t.get("FIRST_TXN_DATE") == null ? "" : t.get("FIRST_TXN_DATE").toString());
            collectionReportUnbilledDTO.setFirstTxnPoste(t.get("FIRST_TXN_POSTE") == null ? "" : t.get("FIRST_TXN_POSTE").toString());
            collectionReportUnbilledDTO.setCardHolderName(t.get("NAME_ON_CARD") == null ? "" : t.get("NAME_ON_CARD").toString());
            collectionReportUnbilledDTO.setFirstTxnAmt(t.get("FIRST_TXN_AMT") == null ? "" : t.get("FIRST_TXN_AMT").toString());
            collectionReportUnbilledDTO.setTotalBdtPay(t.get("TOTAL_BDT_PAY") == null ? "" : t.get("TOTAL_BDT_PAY").toString());
            collectionReportUnbilledDTO.setTotalUsdPay(t.get("TOTAL_USD_PAY") == null ? "" : t.get("TOTAL_USD_PAY").toString());

            collectionReportUnbilledDTOS.add(collectionReportUnbilledDTO);
        }
        return collectionReportUnbilledDTOS;
    }

    public CollectionReportUnbilledDTO findUnbilledByContractNoAndClientId(String contractNo, String clientId){
       Tuple tuple = collectionReportTwoRepository.findByContractNoAndClientId(contractNo, clientId);

        CollectionReportUnbilledDTO collectionReportUnbilledDTO = new CollectionReportUnbilledDTO();

        collectionReportUnbilledDTO.setCardNo(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());
        collectionReportUnbilledDTO.setDueDate(tuple.get("DUE_DATE") == null ? "" : tuple.get("DUE_DATE").toString().substring(0,10));
        collectionReportUnbilledDTO.setCreditLimit(tuple.get("CREDIT_LIMIT") == null ? "" : tuple.get("CREDIT_LIMIT").toString());

        return collectionReportUnbilledDTO;
    }


    public TransactionApprovalHistoryHeaderDTO findCardNoByContractNoAndClientId(String contractNo, String clientId){
        Tuple tuple = collectionReportTwoRepository.findCardNoByContractNoClientId(contractNo, clientId);

        TransactionApprovalHistoryHeaderDTO transactionApprovalHistoryHeaderDTO = new TransactionApprovalHistoryHeaderDTO();

        transactionApprovalHistoryHeaderDTO.setCardNo(tuple.get("CARD_NO") == null ? "" : tuple.get("CARD_NO").toString());


        return transactionApprovalHistoryHeaderDTO;
    }

    public List<FdrDto> findAllFdrByContractNo(String contractNo){
        List<FdrDto>fdr=new ArrayList<>();
        List<Tuple> tupleData=collectionReportTwoRepository.findAllFdrByContractNo(contractNo);
        for(Tuple t : tupleData){
            FdrDto fd = new FdrDto();
            fd.setFdrValue(t.get("FDR_VALUE") == null ? "" : t.get("FDR_VALUE").toString());
            fdr.add(fd);
        }
        return fdr;
    }

}
