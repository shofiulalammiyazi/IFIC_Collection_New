package com.unisoft.collection.samd.setup.writtenOffManagement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WrittenOffManagementServiceImpl implements WrittenOffManagementService{


    @Autowired
    private WrittenOffManagementRepository writtenOffManagementRepository;


    @Override
    public WrittenOffManagement save(WrittenOffManagement writtenOffManagement) {
        if (writtenOffManagement.getWOffInterestSuspenseAmount() != null &&  writtenOffManagement.getWOffProvisionAmount() != null){
            writtenOffManagement.setTotalWOffAmount(writtenOffManagement.getWOffInterestSuspenseAmount() + writtenOffManagement.getWOffProvisionAmount());
        }
        if (writtenOffManagement.getPreviousMonthTotalWOffRecoveryAmount() != null && writtenOffManagement.getCurrentMonthWOffRecoveryAmount() != null){
            writtenOffManagement.setCumulativeWOffRecovery(writtenOffManagement.getPreviousMonthTotalWOffRecoveryAmount() + writtenOffManagement.getCurrentMonthWOffRecoveryAmount());
        }

        if (writtenOffManagement.getLastMonthRemainingWOffOutstanding() != null && writtenOffManagement.getCurrentMonthWOffRecoveryAmount() != null){
            writtenOffManagement.setCurrentMonthRemainingWOffOutstanding(writtenOffManagement.getLastMonthRemainingWOffOutstanding() - writtenOffManagement.getCurrentMonthWOffRecoveryAmount());
        }
        if (writtenOffManagement.getPreviousMonthTotalWOffWaivedAmount() != null && writtenOffManagement.getWOffWaiverAmountSettlement() != null){
            writtenOffManagement.setCumulativeWOffWaivedAmount(writtenOffManagement.getPreviousMonthTotalWOffWaivedAmount() + writtenOffManagement.getWOffWaiverAmountSettlement());
        }
        WrittenOffManagement writtenOffManagement1 = writtenOffManagementRepository.save(writtenOffManagement);
        return writtenOffManagement1;
    }

    @Override
    public WrittenOffManagement findWrittenOffManagementByCustomerId(String customerId) {
        WrittenOffManagement writtenOffManagement = writtenOffManagementRepository.findWrittenOffManagementByCustomerId(customerId);
        return writtenOffManagement;
    }
}
