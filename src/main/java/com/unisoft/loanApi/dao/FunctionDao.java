package com.unisoft.loanApi.dao;

import com.unisoft.loanApi.model.AdvanceSearchDataModel;
import com.unisoft.loanApi.model.AdvancedSearchDataModel;
import com.unisoft.loanApi.model.BaseLoanApiModel;
import com.unisoft.loanApi.model.LoanAccStatement;

import java.util.List;
import java.util.Map;



public interface FunctionDao {

    <T extends BaseLoanApiModel> List<T> getDataFromServer1(String functionName, Map<String, Object> params, Class<T> type);
    <T extends BaseLoanApiModel> List<T> getDataFromServer2(String functionName, Map<String, Object> params, Class<T> type);

    LoanAccStatement getLatestAccountStatement(Map<String, Object> params);

    List<AdvanceSearchDataModel> getAdvanceSearchData(Map<String, Object> params);

    List<AdvancedSearchDataModel> getSamdAdvancedSearchData(Map<String, Object> params);

//    List test();
}
