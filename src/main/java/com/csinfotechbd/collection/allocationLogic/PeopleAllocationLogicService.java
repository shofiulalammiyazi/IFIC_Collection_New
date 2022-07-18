package com.csinfotechbd.collection.allocationLogic;
/*
Created by Monirul Islam at 7/18/2019
*/

import com.csinfotechbd.collection.settings.employee.EmployeeInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleAllocationLogicService {

    @Autowired
    private PeopleAllocationLogicDao peopleAllocationLogicDao;

    @Autowired
    private PeopleAllocationLogicRepository peopleAllocationLogicRepository;

    public List<PeopleAllocationLogicInfo> getAll() {
        return peopleAllocationLogicDao.getList();
    }

    public boolean saveNew(PeopleAllocationLogicInfo agency) {
        return peopleAllocationLogicDao.saveNew(agency);
    }

    public boolean updateAgency(PeopleAllocationLogicInfo agency) {
        return peopleAllocationLogicDao.updateObj(agency);
    }

    public PeopleAllocationLogicInfo getById(Long Id) {
        return peopleAllocationLogicDao.getById(Id);
    }

    public List<PeopleAllocationLogicInfo> getActiveList() {
        return peopleAllocationLogicDao.getActiveOnly();
    }

    public PeopleAllocationLogicInfo getByDealerId(EmployeeInfoEntity id, String unit) {
        return peopleAllocationLogicRepository.findByDealerAndUnit(id, unit);
    }

    public PeopleAllocationLogicInfo getByDealerIdAndDistributionType(EmployeeInfoEntity id, String unit, String distributionType) {
        return peopleAllocationLogicRepository.findByDealerAndUnitAndDistributionType(id, unit, distributionType);
    }

    public Long getSupperVisorPinByTeamleaderId(Long id) {
        return peopleAllocationLogicRepository.getSupperVisorIdByTeamleader(id);
    }

    public Long getManagerIdByEmployeeInfoEntityId(Long id) {
        return peopleAllocationLogicRepository.getManagerIdBySuperVisorId(id);
    }


    public List<PeopleAllocationLogicInfo> findDealerByTeamLeaderId(Long id) {
        return peopleAllocationLogicRepository.findListByTeamleaderId(id);
    }

    public List<PeopleAllocationLogicInfo> findTeamLeadBySupervisorId(Long id) {
        return peopleAllocationLogicRepository.findTeamLeadBySupervisorId(id);
    }


    public List<String> findDealerPinByAnyPin(String userPin) {
        return peopleAllocationLogicRepository.getAllDealerPinByAnyPin(userPin);
    }

    public List<SupervisorViewModel> getSupervisorByManager(List<String> pinList){
        List<Tuple> tuples = peopleAllocationLogicRepository.findSupervisorsByManager(pinList);
        List<SupervisorViewModel> supervisorViewModels = new ArrayList<>();

        for(Tuple t : tuples){
            SupervisorViewModel supervisorViewModel = new SupervisorViewModel(t);

            supervisorViewModels.add(supervisorViewModel);
        }

        return supervisorViewModels;
    }

    public List<SupervisorViewModel> getAllSupervisor(){
        List<Tuple> tuples = peopleAllocationLogicRepository.getAllSupervisorDoc();
        List<SupervisorViewModel> supervisorViewModels = new ArrayList<>();

        for(Tuple t : tuples){
            SupervisorViewModel supervisorViewModel = new SupervisorViewModel(t);

            supervisorViewModels.add(supervisorViewModel);
        }

        return supervisorViewModels;
    }
}
