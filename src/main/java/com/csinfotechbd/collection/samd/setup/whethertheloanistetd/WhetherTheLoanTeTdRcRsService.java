package com.csinfotechbd.collection.samd.setup.whethertheloanistetd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhetherTheLoanTeTdRcRsService {

    @Autowired
    private WhetherTheLoanTeTdRcRsRepository repository;

    public void saveOrUpdate(WhetherTheLoanTeTdRcRs obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<WhetherTheLoanTeTdRcRs>whetherTheLoanTeTdRcRsList(){
        return repository.findAll();
    }
    public WhetherTheLoanTeTdRcRs findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
