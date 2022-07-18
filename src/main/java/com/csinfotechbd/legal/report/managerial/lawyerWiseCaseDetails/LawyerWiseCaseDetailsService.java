package com.csinfotechbd.legal.report.managerial.lawyerWiseCaseDetails;

import com.csinfotechbd.legal.setup.lawyers.LawyerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LawyerWiseCaseDetailsService {
    @Autowired
    private LawyerWiseCaseDetailsRepository repository;

    public List<LawyerWiseCaseDetailsControllerDto> getReport(long lawyerId) {
        return repository.getReport(lawyerId).stream().map(LawyerWiseCaseDetailsControllerDto::new).collect(Collectors.toList());
    }
    public List<LawyerDto> getAssignedLawyerList() {
        return repository.getAssignedLawyerList().stream().map(LawyerDto::new).collect(Collectors.toList());
    }
}
