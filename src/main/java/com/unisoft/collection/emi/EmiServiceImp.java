package com.unisoft.collection.emi;

import com.unisoft.utillity.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmiServiceImp implements EmiService {

    @Autowired
    private EmiRepository emiRepository;
    @Autowired
    private DateUtils dateUtils;


    @Override
    public List<EmiEmtityDto> findContractByDateRange(String contractNo) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        List<EmiEmtityDto> emiEmtityDtos = new ArrayList<>();
        Date startDate = dateUtils.getMonthStartDate();
        Date endDate = dateUtils.getMonthEndDate();
        List<EmiEntity> emiEntities =  emiRepository.findContractByDateRange(contractNo,startDate,endDate);
        for (EmiEntity emiEntity: emiEntities){
            EmiEmtityDto dto = new EmiEmtityDto();
            dto.setContractId(emiEntity.getContractId());
            dto.setPaymentDate(simpleDateFormat.format(emiEntity.getPaymentDate()));
            dto.setBdtEmi(emiEntity.getBdtEmi());
            dto.setUsdEmi(emiEntity.getUsdEmi());
            emiEmtityDtos.add(dto);
        }

        return emiEmtityDtos;
    }
}
