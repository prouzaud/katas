package com.example.testtdd.cases.romans;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RomansInverse {

    private final static Map<String, Integer> binding = new HashMap<String, Integer>() {
        {
            put("I", 1);
            put("V", 5);
            put("X", 10);
            put("L", 50);
            put("C", 100);
            put("D", 500);
            put("M", 1000);
        }
    };


    public int toInt(String romanNumber) {

        checkRomanNumber(romanNumber);

        String heavierChar = getHeavierCharacter(romanNumber);
        if (romanNumber.equals(heavierChar)) {
            return binding.get(romanNumber);
        } else {
            if (isNumberLesserThanHeavierCharacter(romanNumber, heavierChar)) {
                int heavierCharacterCount = countCharacterOccurrence(romanNumber, heavierChar);
                int subValue = toInt(romanNumber.replaceAll(Pattern.quote(heavierChar), ""));
                return heavierCharacterCount * binding.get(heavierChar) - subValue;
            } else {
                int subValue = toInt(romanNumber.substring(1));
                return binding.get(heavierChar) + subValue;
            }

        }
    }

    private int countCharacterOccurrence(String romanNumber, String character) {

        int result = 0;
        for (int i = 0; i < romanNumber.length(); i++) {
            if (romanNumber.charAt(i) == character.charAt(0)) {
                result++;
            }
        }
        return result;
    }

    private boolean isThereForbiddenCharacters(String romanNumber) {

        for (int i = 0; i < romanNumber.length(); i++) {
            if (!binding.keySet().contains(romanNumber.charAt(i) + "")) {
                return true;
            }
        }
        return false;
    }

    private boolean isNumberLesserThanHeavierCharacter(String romanNumber, String heavierCharacter) {
        return !romanNumber.startsWith(heavierCharacter);
    }

    private String getHeavierCharacter(String romanNumber) {

        if (romanNumber.contains("M")) {
            return "M";
        } else if (romanNumber.contains("D")) {
            return "D";
        } else if (romanNumber.contains("C")) {
            return "C";
        } else if (romanNumber.contains("L")) {
            return "L";
        } else if (romanNumber.contains("X")) {
            return "X";
        } else if (romanNumber.contains("V")) {
            return "V";
        } else {
            return "I";
        }
    }

    public void checkRomanNumber(String romanNumber) {

        if (romanNumber == null) {
            throw new RomanNumberException("Null is not a valid input!");
        }
        if (romanNumber.length() == 0) {
            throw new RomanNumberException("Empty string is not a valid input!");
        }

        checkForbiddenCharactersAbsence(romanNumber);
        checkHeavierCharactersAlwaysAtABound(romanNumber);
        checkHeavierCharsMustBeGrouped(romanNumber);
        checkRepetitionLengths(romanNumber);
    }

    public class RomanNumberException extends RuntimeException {

        public RomanNumberException(String message) {
            super(message);
        }
    }


    private void checkForbiddenCharactersAbsence(String romanNumber) {

        String messageList = romanNumber
                .chars()
                .mapToObj(i -> (char) i + "")
                .filter(c -> !binding.keySet().contains(c))
                .collect(Collectors.joining(", "));

        if (messageList.length() > 0) {
            throw new RomanNumberException("Forbidden characters found: " + messageList);
        }
    }

    private void checkHeavierCharactersAlwaysAtABound(String romanNumber) {

        if (romanNumber.length() > 1) {
            String heavyCharacter = getHeavierCharacter(romanNumber);

            if (romanNumber.startsWith(heavyCharacter)) {
                checkHeavierCharactersAlwaysAtABound(romanNumber.substring(1));
            } else if (romanNumber.endsWith(heavyCharacter)) {
                checkHeavierCharactersAlwaysAtABound(romanNumber.substring(0, romanNumber.length() - 2));
            } else {
                throw new RomanNumberException("Invalid roman number: " + heavyCharacter + " is misplaced.");
            }
        }
    }

    private void checkHeavierCharsMustBeGrouped(String romanNumber) {

        if (romanNumber.length() > 1) {
            String heavyCharacter = getHeavierCharacter(romanNumber);
            boolean firstFound = false;
            boolean differentCharFollows = false;
            for (int i = 0; i < romanNumber.length(); i++) {
                if (!firstFound && heavyCharacter.equals("" + romanNumber.charAt(i))) {
                    firstFound = true;
                } else if (firstFound && !heavyCharacter.equals("" + romanNumber.charAt(i))) {
                    differentCharFollows = true;
                } else if (firstFound && differentCharFollows && heavyCharacter.equals("" + romanNumber.charAt(i))) {
                    throw new RomanNumberException("Invalid roman number: multiple \"heavier\" " + heavyCharacter + " not grouped.");
                }
            }
        }
    }

    private void checkRepetitionLengths(String romanNumber) {
        if (romanNumber.equals("IIII")) {
            System.out.println("");
        }
        repetitiveCharactersLimitations.entrySet().forEach( option -> {
            if (romanNumber.contains(buildTooLongCharSequence(option.getKey(), option.getValue()))){
                throw new RomanNumberException("A too long repetition of " + option.getKey() + " was found.");
            }
        });
    }

    private String buildTooLongCharSequence(String character, int number) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0 ; i < number ; i++) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    private final static Map<String, Integer> repetitiveCharactersLimitations = new HashMap<String, Integer>() {
        {
            put("I", 4);
            put("V", 2);
            put("X", 4);
            put("L", 2);
            put("C", 4);
            put("D", 2);
        }
    };
}
