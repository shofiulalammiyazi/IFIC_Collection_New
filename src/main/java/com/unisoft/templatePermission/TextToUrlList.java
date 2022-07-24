package com.unisoft.templatePermission;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TextToUrlList {
    private List<TextToUrlBase> texttourls = new ArrayList<>();
}
