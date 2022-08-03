package com.unisoft.collection.letterInformation;

import lombok.Data;

import javax.persistence.Tuple;

@Data
public class LetterDto {

    private int total;

    public LetterDto(){

    }
    public LetterDto(Tuple data) {
        total += ((Number) data.get("TOTAL")).intValue();

    }
}
