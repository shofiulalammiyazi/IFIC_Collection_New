package com.unisoft.collection.letterInformation;

import com.unisoft.common.CommonEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Setter
@Getter
public class LetterInformationsFile extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    private String dmsFileId;

    private String dmsFileType;

    @OneToOne(cascade = CascadeType.REFRESH)
    private LetterInformations letterInformations;

}
