package com.unisoft.collection.samd.setup.possibilityprobabilitytohitinnplUnclassified;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PossibilityProbabilityService {

    @Autowired
    private PossibilityProbabilityRepository repository;

    public  void saveOrUpdate(PossibilityProbability obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<PossibilityProbability>objectList(){
        return repository.findAll();
    }
    public PossibilityProbability findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
