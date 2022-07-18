package com.csinfotechbd.collection.emi;


import java.util.List;

public interface EmiService {

    List<EmiEmtityDto> findContractByDateRange(String contractNo);
}
