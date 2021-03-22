package com.example.testtdd.cases.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    Calculator calculator = new Calculator();

    @ParameterizedTest
    @CsvSource(
            nullValues = "NULL",
            delimiterString = "=",

            value = {
                    "2*3*4=24",
                    "1=1",
                    "2=2",
                    "1+1=2",
                    "1+2=3",
                    "1+2+3=6",
                    "3*3=9",
                    "1*1*1=1",

                    "4/2=2",
                    "1+4/2=3",
                    "1+4/2+1=4",
            })
    void calculate_validExpressions(ArgumentsAccessor accessor) {

        int result = calculator.calculate(accessor.getString(0));
        assertEquals(accessor.getInteger(1), result);
    }
}
