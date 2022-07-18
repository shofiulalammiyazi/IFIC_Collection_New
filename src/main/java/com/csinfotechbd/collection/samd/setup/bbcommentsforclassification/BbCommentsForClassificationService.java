package com.csinfotechbd.collection.samd.setup.bbcommentsforclassification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BbCommentsForClassificationService {

    @Autowired
    private BbCommentsForClassificationRepository repository;

    public  void saveOrUpdate(BbCommentsForClassification obj){

       try {
           repository.save(obj);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<BbCommentsForClassification>objectList(){
        return repository.findAll();
    }
    public BbCommentsForClassification findByid(String id){
        return repository.findById(Long.parseLong(id)).get();
    }

    public void remove(String id){
        repository.deleteById(Long.parseLong(id));
    }

}
