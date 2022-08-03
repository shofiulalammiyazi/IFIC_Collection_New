package com.unisoft.cardprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;

@Service
public class AllocationDetailsService {
    @Autowired
    private AllocationDetailsRepository allocationDetailsRepository;

    public AllocationDetailsDTO findCardAccountInfoByContractNoAndClientId(String contractNo, String startDate, String endDate, String clientId){
        Tuple tuple = allocationDetailsRepository.findCardAccountInfoByContractNoAndClientId(contractNo, startDate, endDate, clientId);
        AllocationDetailsDTO allocationDetailsDTO = new AllocationDetailsDTO();

        if(tuple !=null){
            allocationDetailsDTO.setMoOutStanding(tuple.get("OUTSTANDING_AMOUNT") == null ? "" : tuple.get("OUTSTANDING_AMOUNT").toString());
            allocationDetailsDTO.setMoAge(tuple.get("AGE_CODE") == null ? "" : tuple.get("AGE_CODE").toString());
            allocationDetailsDTO.setDistributionDate(tuple.get("CREATED_DATE") == null ? "" : tuple.get("CREATED_DATE").toString());
            allocationDetailsDTO.setMoStateCode(tuple.get("STATE_CODE") == null ? "" : tuple.get("STATE_CODE").toString());
            allocationDetailsDTO.setMoDpd(tuple.get("DPD_BUCKET") == null ? "" : tuple.get("DPD_BUCKET").toString());
        }





        return allocationDetailsDTO;
    }

}
