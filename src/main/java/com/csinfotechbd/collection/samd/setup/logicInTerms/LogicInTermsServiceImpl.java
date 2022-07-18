package com.csinfotechbd.collection.samd.setup.logicInTerms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogicInTermsServiceImpl implements LogicInTermsService{


    @Autowired
    private LogicInTermsRepository logicInTermsRepository;


    @Override
    public List<LogicInTerms> findAll() {
        return logicInTermsRepository.findAll();
    }

    @Override
    public void save(LogicInTerms logicInTerms) {
            logicInTermsRepository.save(logicInTerms);
    }

    @Override
    public LogicInTerms findLogicInTermsById(Long id) {
        return logicInTermsRepository.findLogicInTermsById(id);
    }
}
