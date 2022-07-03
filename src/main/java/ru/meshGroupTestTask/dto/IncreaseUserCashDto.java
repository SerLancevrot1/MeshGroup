package ru.meshGroupTestTask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IncreaseUserCashDto {
    private BigDecimal maxCash;

    private BigDecimal fixedStepOfIncrease;
}
