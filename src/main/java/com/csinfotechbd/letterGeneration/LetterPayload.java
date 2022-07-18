package com.csinfotechbd.letterGeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LetterPayload {
    private double currentOverdue;
    private String currentDpdBucket;
    private double emiAmount;
}
