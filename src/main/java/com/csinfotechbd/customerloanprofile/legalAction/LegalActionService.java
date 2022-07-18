package com.csinfotechbd.customerloanprofile.legalAction;

import java.util.List;

public interface LegalActionService {
    List<LegalActionDto> getLegalAction(String accountNo);
}
