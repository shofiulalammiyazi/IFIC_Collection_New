package com.csinfotechbd.templatePermission;
/*
Created by Monirul Islam at 9/29/2019 
*/

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TextToUrlList {
    private List<TextToUrlBase> texttourls = new ArrayList<>();
}
