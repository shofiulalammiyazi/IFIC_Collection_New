package com.unisoft.collection.samd.dataEntry.basicInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SAMDBasicInformationServiceImpl implements SAMDBasicInformationService{


    @Autowired
    private SAMDBasicInformationRepository samdBasicInformationRepository;


    @Override
    public SAMDBasicInformation saveInformation(SAMDBasicInformation samdBasicInformation) {
        SAMDBasicInformation prevData = samdBasicInformationRepository.findSAMDBasicInformationByStatusAndOrderByCreatedDateDesc();
        if (prevData != null){
            prevData.setStatus(false);
            samdBasicInformationRepository.save(prevData);
        }
        samdBasicInformation.setStatus(true);
        return samdBasicInformationRepository.save(samdBasicInformation);
    }


    @Override
    public SAMDBasicInformation findById(Long id){
        SAMDBasicInformation samdBasicInformation = samdBasicInformationRepository.getOne(id);
        return samdBasicInformation;
    }

    @Override
    public SAMDBasicInformation findByStatusAndCreatedDate() {
        return samdBasicInformationRepository.findSAMDBasicInformationByStatusAndOrderByCreatedDateDesc();
    }
}
