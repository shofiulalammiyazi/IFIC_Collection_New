package com.unisoft.collection.samd.setup.sourceofrecoverytools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SourceOfRecoveryToolsService {

    @Autowired
    private SourceOfRecoveryToolsRepository repository;

    public void saveOrUpdate(SourceOfRecoveryTools obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<SourceOfRecoveryTools>findSourceOfRecoveryTools(){
        return repository.findAll();
    }

    public SourceOfRecoveryTools findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
