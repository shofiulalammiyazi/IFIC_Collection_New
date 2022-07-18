package com.csinfotechbd.collection.samd.setup.borrowerguarantoravailability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerAndGuarantorAvailabilityService {

    @Autowired
    private BorrowerAndGuarantorAvailabilityRepository repository;

    public  void saveOrUpdate(BorrowerAndGuarantorAvailability obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<BorrowerAndGuarantorAvailability>findAllBorrowerAndGuarantorAvailability(){
        return repository.findAll();
    }
    public BorrowerAndGuarantorAvailability findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
