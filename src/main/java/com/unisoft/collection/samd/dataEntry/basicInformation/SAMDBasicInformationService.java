package com.unisoft.collection.samd.dataEntry.basicInformation;

public interface SAMDBasicInformationService{

    SAMDBasicInformation saveInformation(SAMDBasicInformation samdBasicInformation);
    SAMDBasicInformation findById(Long id);

    SAMDBasicInformation findByStatusAndCreatedDate();
}
