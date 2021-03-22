package com.example.testtdd.cases.romans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

public class RomansInlineTest {

    Romans romans = new Romans();

    @ParameterizedTest
    @CsvSource(
            nullValues = "NULL",
            delimiterString = "->",

            value = {
                    "NULL -> EMPTY_STRING",
                    "-1 -> EMPTY_STRING",
                    "0 -> EMPTY_STRING",
                    "1 -> I",
                    "2 -> II",
                    "3 -> III",
                    "4 -> IV",
                    "5 -> V",
                    "6 -> VI",
                    "9 -> IX",
                    "10 -> X",
                    "19 -> IXX",
                    "20 -> XX",
                    "29 -> IXXX",
                    "39 -> XIL",
                    "40 -> XL",
                    "44 -> VIL",
                    "45 -> VL",
                    "46 -> IVL",
                    "50 -> L",
                    "63 -> LXIII",
                    "100 -> C",
                    "500 -> D",
                    "911 -> XICM",
                    "996 -> IVM",
                    "1000 -> M",
                    "1994 -> VIMM",
                    "1996 -> IVMM",
                    "2539 -> MMDXIL",
                    "7168 -> MMMMMMMCLXVIII",
                    "7198 -> MMMMMMMIICC",
                    "20021 -> MMMMMMMMMMMMMMMMMMMMXXI",
    })
    void numberToRoman_whenPassNumber_thenReturnsValue(ArgumentsAccessor accessor) {
        String result = romans.numberToRoman(accessor.getInteger(0));
        assertEquals(readExpectedResult(accessor), result);
    }

    private String readExpectedResult(ArgumentsAccessor accessor) {
        return "EMPTY_STRING".equals(accessor.getString(1)) ? "" : accessor.getString(1);
    }

}
