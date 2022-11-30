package com.unisoft.collection.lettertemplate;

import com.unisoft.base.BaseInfo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter @Setter
@Table(name = "retail_ops_letter_templates")
public class LetterTemplate extends BaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String unit;
    private String name;
    private String emailSubject;
    @Lob
    private String template;

    public LetterTemplate() {
    }


    public LetterTemplate(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
