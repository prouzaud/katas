package com.example.testtdd.cases.romans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomansInverseTest {

    RomansInverse romansInverse = new RomansInverse();

    // valid input tests
    @ParameterizedTest
    @CsvSource(
            nullValues = "NULL",
            delimiterString = "->",
            value = {
                    "I -> 1",
                    "II -> 2",
                    "III -> 3",
                    "IV -> 4",
                    "V -> 5",
                    "VI -> 6",
                    "VII -> 7",
                    "X -> 10",
                    "IXX -> 19",
                    "XX -> 20",
                    "XXI -> 21",
                    "XXII -> 22",
                    "IVXXX -> 26",
                    "XXX -> 30",
                    "XXXIV-> 34",
                    "IL -> 49",
                    "L -> 50",
                    "LI -> 51",
                    "C -> 100",
                    "CC -> 200",
                    "XXXIIICCC -> 267",
                    "CCC -> 300",
                    "D -> 500",
                    "LIVM -> 946",
                    "M -> 1000",
                    "MVIL ->1044",
                    "IMM -> 1999",
                    "MM -> 2000",
                    "VIMMMMMM -> 5994",
                    "MMMMMMIV -> 6004",
            })
    void toInt_whenPassRomanNumber_thenReturnsValue(ArgumentsAccessor accessor) {
        int result = romansInverse.toInt(accessor.getString(0));
        assertEquals(accessor.getInteger(1), result);
    }

    // Invalid input tests
    @ParameterizedTest
    @CsvSource(
            nullValues = "NULL",
            delimiterString = "->",
            value = {
                    // null case
                    "NULL -> Null is not a valid input!",
                    "EMPTY -> Empty string is not a valid input!",
                    // a heavier caracter must be at the start or the end of its segment - for example IVI -> V can't be at the center
                    "IVI ->  Invalid roman number: V is misplaced.",
                    "CMXII ->  Invalid roman number: M is misplaced.",
                    "ILVM ->  Invalid roman number: L is misplaced.",
                    // multiple heavier characters must be grouped
                    "MIM ->  Invalid roman number: multiple \"heavier\" M not grouped.",
                    "MCIC ->  Invalid roman number: multiple \"heavier\" C not grouped.",
                    "MMCCIC ->  Invalid roman number: multiple \"heavier\" C not grouped.",
                    // I, V, X, L, C, D, M are the only allowed characters
                    "TOTO -> Forbidden characters found: T, O, T, O",
                    "PII -> Forbidden characters found: P",
                    "IPI -> Forbidden characters found: P",
                    "IIP -> Forbidden characters found: P",
                    // too long repetitions are not allowed (for example 4 must be IV, so IIII is not a valid roman number)
                    "IIII -> A too long repetition of I was found.",
                    "VV -> A too long repetition of V was found.",
                    "IVV -> A too long repetition of V was found.",
                    "VVI -> A too long repetition of V was found.",
                    "XXXX -> A too long repetition of X was found.",
                    "LL -> A too long repetition of L was found.",
                    "CCCC -> A too long repetition of C was found.",
                    "XXXCCCC -> A too long repetition of C was found.",
                    "CCCCXXX -> A too long repetition of C was found.",
                    "DD -> A too long repetition of D was found.",
                    "MMMMDD -> A too long repetition of D was found.",
            })
    void checkRomanNumberExceptionCases(ArgumentsAccessor accessor) {

        RomansInverse.RomanNumberException exception =Assertions.assertThrows(RomansInverse.RomanNumberException.class, () -> {
            romansInverse.toInt(readValue(accessor,0));
        });
        assertEquals(accessor.getString(1), exception.getMessage());
    }

    private String readValue(ArgumentsAccessor accessor, int indice) {
        String readedValue = accessor.getString(indice);
        if ("EMPTY".equals(readedValue)) {return "";}
        else {return readedValue;}
    }
}
