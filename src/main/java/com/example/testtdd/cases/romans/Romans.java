package com.example.testtdd.cases.romans;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Romans {

    private List<Binding> bindings = Arrays.asList(
        new Binding(1, "I", 3, 1),
        new Binding(5, "V", 8, 4),
        new Binding(10, "X", 38, 9),
        new Binding(50, "L", 88, 39),
        new Binding(100, "C", 388, 89),
        new Binding(500, "D", 888, 389),
        new Binding(1000, "M", Integer.MAX_VALUE, 889));

    public String numberToRoman(Integer number) {

        if (number == null || number <= 0 ) {
            return "";
        }

        if(number ==4){
            System.out.println("");
        }

        Binding binding = getHeavierBinding(number);
        if (number == binding.number) {
            return binding.heavierChar;
        } else {
            int residualValue = Math.abs(number - binding.number);
            if (shouldTheHeaviestCharBeAtTheLeft(number,binding)) {
                return numberToRoman(residualValue) + binding.heavierChar;
            } else {
                return binding.heavierChar + numberToRoman(residualValue);
            }
        }
    }

    private boolean shouldTheHeaviestCharBeAtTheLeft(int value, Binding binding) {
        int minimizedValue = minimizeNumber(value, binding);
        return minimizedValue - binding.number < 0;
    }

    public int minimizeNumber(int number, Binding binding) {
        if (number - binding.number < binding.pivotValue) {
            return number;
        } else {
            return minimizeNumber(number - binding.number, binding);
        }
    }



    private Binding getHeavierBinding(int number ) {
        for (Binding binding : bindings) {
            if (binding.maxLimit >= number) {
                return binding;
            }
        }
        return null;
    }


    private class Binding {
        int number;
        int maxLimit;
        String heavierChar;
        int pivotValue;

        public Binding(int number, String heavierChar, int maxLimit, int minLimit) {
            this.number = number;
            this.heavierChar = heavierChar;
            this.maxLimit = maxLimit;
            this.pivotValue = minLimit;
        }
    }
}
