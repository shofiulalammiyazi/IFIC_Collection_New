package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplclassified;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibilityProbabilityClassifiedService {

    @Autowired
    private PossibilityProbabilityClassifiedRepository repository;

    public  void saveOrUpdate(PossibilityProbabilityClassified obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<PossibilityProbabilityClassified>objectList(){
        return repository.findAll();
    }
    public PossibilityProbabilityClassified findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
