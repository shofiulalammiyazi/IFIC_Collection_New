package com.csinfotechbd.collection.settings.diaryNoteMenu;
/*
Created by Monirul Islam at 6/26/2019 
*/

import com.csinfotechbd.base.BaseInfo;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class DiaryNoteMenuEntity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private String remarks;

}
