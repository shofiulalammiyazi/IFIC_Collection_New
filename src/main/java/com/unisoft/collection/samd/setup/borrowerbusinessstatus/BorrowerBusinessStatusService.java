package com.unisoft.collection.samd.setup.borrowerbusinessstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerBusinessStatusService {

    @Autowired
    private BorrowerBusinessStatusRepository repository;

    public  void saveOrUpdate(BorrowerBusinessStatus obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<BorrowerBusinessStatus>findAllBorrowerBusinessStatus(){
        return repository.findAll();
    }
    public BorrowerBusinessStatus findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
