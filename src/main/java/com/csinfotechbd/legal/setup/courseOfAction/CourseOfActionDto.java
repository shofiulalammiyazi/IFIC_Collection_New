package com.csinfotechbd.legal.setup.courseOfAction;


// Created by Yasir Araphat on 09 March, 2021

import lombok.Data;

@Data
public class CourseOfActionDto {

    private Long id;
    private String name;
    private String contestedType;


    public CourseOfActionDto() {
    }

    public CourseOfActionDto(CourseOfAction courseOfAction) {
        if (courseOfAction == null || courseOfAction.getId() == null) return;

        id = courseOfAction.getId();
        name = courseOfAction.getName();
        contestedType = courseOfAction.getContestedType();
    }

}
