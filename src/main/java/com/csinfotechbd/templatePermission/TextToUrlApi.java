package com.csinfotechbd.templatePermission;
/*
Created by Monirul Islam at 9/29/2019
*/

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/collection/texttourl")
public class TextToUrlApi {

    private TextToUrlBaseRepository textToUrlBaseRepository;

    @PostMapping("/urltolist")
    public boolean saveUrlToList(@RequestBody TextToUrlList textToUrlList) {

        List<TextToUrlBase> textToUrlBases = textToUrlList.getTexttourls();

        for (TextToUrlBase t : textToUrlBases) {
            String List = "List";
            String listUrl = t.getUrl() + "list";
            String View = "View";
            String viewUrl = t.getUrl() + "view";
            String Create = "Create";
            String createUrl = t.getUrl() + "create";
            String Edit = "Edit";
            String editUrl = t.getUrl() + "edit";


            t.getTextToUrlChildren().add(new TextToUrlChild(List, listUrl));
            t.getTextToUrlChildren().add(new TextToUrlChild(View, viewUrl));
            t.getTextToUrlChildren().add(new TextToUrlChild(Create, createUrl));
            t.getTextToUrlChildren().add(new TextToUrlChild(Edit, editUrl));
        }
        textToUrlBaseRepository.saveAll(textToUrlBases);
        return true;
    }
}
