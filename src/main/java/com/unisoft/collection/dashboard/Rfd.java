package com.unisoft.collection.dashboard;
/*
  Created by Md.   Islam on 1/4/2020
*/

import lombok.Data;

import java.util.List;

@Data
public class Rfd {

    private List<RfdMenuModel> rfdMenuList;

    private List<RfdMenuModel> subMenu1List;
    private List<RfdMenuModel> subMenu2List;
    private List<RfdMenuModel> subMenu3List;

}
