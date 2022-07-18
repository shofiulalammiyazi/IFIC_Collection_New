package com.csinfotechbd.collection.samd.setup.borrowerGuarantorsCapabilityToRepay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerGuarantorCapabilityRepayService {

    @Autowired
    private BorrowerGuarantorCapabilityRepayRepository repository;

    public  void saveOrUpdate(BorrowerGuarantorCapabilityRepay obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<BorrowerGuarantorCapabilityRepay>objectList(){
        return repository.findAll();
    }
    public BorrowerGuarantorCapabilityRepay findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
