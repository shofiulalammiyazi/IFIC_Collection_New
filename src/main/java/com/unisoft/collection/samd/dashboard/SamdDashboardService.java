package com.unisoft.collection.samd.dashboard;

import com.unisoft.collection.dashboard.AdvancedSearchPayload;
import com.unisoft.collection.samd.dataEntry.loanAccountDistribution.SamLoanAccountDistribution;
import com.unisoft.loanApi.dao.FunctionDao;
import com.unisoft.loanApi.model.AdvancedSearchDataModel;
import com.unisoft.loanApi.utils.ParameterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SamdDashboardService {

    private final SamdDashboardDao dashboardDao;
    private final ParameterManager parameterManager;

    @Qualifier("preparedStatementBasedDao")
    private FunctionDao preparedStatementBasedDao;

    public List<SamLoanAccountDistribution> getSamAccountListByUser(String userPin) {
        return dashboardDao.getSamAccountListByUser(userPin);
    }

    //method for SAMD advanced search
    public List<AdvancedSearchDataModel> getSamdAdvancedSearchData(AdvancedSearchPayload payload) {
        Map<String, Object> params = parameterManager.getSamdAdvancedSearchParams(payload);
        return preparedStatementBasedDao.getSamdAdvancedSearchData(params);
    }
}
