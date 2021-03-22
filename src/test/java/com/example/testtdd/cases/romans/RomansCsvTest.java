package com.example.testtdd.cases.romans;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomansCsvTest {

    Romans romans = new Romans();

    @ParameterizedTest
    @CsvFileSource(resources = "/cases/romans/romans.csv", numLinesToSkip = 1, nullValues = {"NULL"})
    void numberToRoman_whenPassNumber_thenReturnsValue(ArgumentsAccessor accessor) {
        String result = romans.numberToRoman(accessor.getInteger(0));
        assertEquals(readExpectedResult(accessor), result);
    }

    private String readExpectedResult(ArgumentsAccessor accessor) {
        return "EMPTY_STRING".equals(accessor.getString(1)) ? "" :
                        accessor.getString(1);
    }

}
