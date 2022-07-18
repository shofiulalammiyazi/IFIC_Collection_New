package com.csinfotechbd.collection.settings.additionalInfoExcel;

import com.csinfotechbd.customerloanprofile.AdditionalInfo.AdditionalInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddiInfoExcelWrapper {

    List<AdditionalInfo> additionalInfoList = new ArrayList<>();

}
