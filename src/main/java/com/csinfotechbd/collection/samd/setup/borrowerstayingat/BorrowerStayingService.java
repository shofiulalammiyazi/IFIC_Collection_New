package com.csinfotechbd.collection.samd.setup.borrowerstayingat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowerStayingService {

    @Autowired
    private BorrowerStayingRepository repository;

    public  void saveOrUpdate(BorrowerStaying obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<BorrowerStaying>findBorrowerStaingAll(){
        return repository.findAll();
    }
    public BorrowerStaying findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
