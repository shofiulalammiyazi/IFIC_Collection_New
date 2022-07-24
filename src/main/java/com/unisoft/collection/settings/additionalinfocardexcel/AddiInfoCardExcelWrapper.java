package com.unisoft.collection.settings.additionalinfocardexcel;


import com.unisoft.retail.card.dataEntry.additionalinfocard.AdditionalInfoCard;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddiInfoCardExcelWrapper {

    List<AdditionalInfoCard> additionalInfoList = new ArrayList<>();

}
