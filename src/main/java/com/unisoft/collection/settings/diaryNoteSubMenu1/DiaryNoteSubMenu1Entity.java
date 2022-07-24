package com.unisoft.collection.settings.diaryNoteSubMenu1;
/*
Created by   Islam at 6/26/2019
*/

import com.unisoft.base.BaseInfo;
import com.unisoft.collection.settings.diaryNoteMenu.DiaryNoteMenuEntity;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class DiaryNoteSubMenu1Entity extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private String remarks;
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "diaryNoteMenu_id")
    private DiaryNoteMenuEntity diaryNoteMenu;
}
