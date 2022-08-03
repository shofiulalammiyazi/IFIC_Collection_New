package com.unisoft.common;



import com.unisoft.collection.settings.agency.AgencyEntity;
import com.unisoft.collection.settings.agency.AgencyService;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanEntity;
import com.unisoft.collection.settings.assetClassificationLoan.AssetClassificationLoanRepository;
import com.unisoft.collection.settings.employee.EmployeeInfoEntity;
import com.unisoft.collection.settings.employee.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class EnumDataService {

    private AgencyService agencyService;

    private EmployeeService employeeService;



    private AssetClassificationLoanRepository assetClassificationLoanRepository;

    public Map<Long, String> getClStatuses() {
        Map<Long, String> statuses = new HashMap<>();
        List<AssetClassificationLoanEntity> allData = assetClassificationLoanRepository.findAll();
        for (AssetClassificationLoanEntity data : allData)
            statuses.put(data.getId(), data.getType().getName());
        return statuses;
    }

    public List<EmployeeInfoEntity> getDealers() {
        List<EmployeeInfoEntity> dealers = employeeService.getDealerList();
        return dealers;
    }

    public List<AgencyEntity> getAgencies() {
        List<AgencyEntity> agencies = agencyService.getAll();
        return agencies;
    }


}
