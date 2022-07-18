package com.csinfotechbd.collection.samd.dataEntry.basicInformation;

import java.util.List;

public interface SAMDBasicInformationService{

    SAMDBasicInformation saveInformation(SAMDBasicInformation samdBasicInformation);
    SAMDBasicInformation findById(Long id);

    SAMDBasicInformation findByStatusAndCreatedDate();
}
