package com.csinfotechbd.retail.loan.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratedLetterService {
    @Autowired
    private GeneratedLetterRepository generatedLetterRepository;


    public List<GeneratedLetter> saveAll(List<GeneratedLetter> generatedLetters) {
        return generatedLetterRepository.saveAll(generatedLetters);
    }
}
