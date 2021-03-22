package com.example.testtdd.cases.calculator;

import com.example.testtdd.cases.calculator.model.ComplexExpression;
import com.example.testtdd.cases.calculator.model.Expression;
import com.example.testtdd.cases.calculator.model.SingleTerm;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {

    private Map<String, BiFunction<Integer, Integer, Integer>> operators = new HashMap<String, BiFunction<Integer, Integer, Integer>>() {{
        put("+", (i, j) -> i+j);
        put("-", (i, j) -> i-j);
        put("*", (i, j) -> i*j);
        put("/", (i, j) -> i/j);
    }};

    private Map<String, Integer> operatorsPriority = new HashMap<String, Integer>() {{
        put("+", 1);
        put("-", 1);
        put("*", 2);
        put("/", 2);
    }};

    public Integer calculate(String formalExpression) {
        if (formalExpression.equals("2*3*4")) {
            System.out.println();
        }
        Expression<Integer> expression =  buildGlobalExpression(formalExpression);
        return expression.calculate();
    }

    private boolean simpleExpression(String expression) {
        for (String operator : operators.keySet()) {
            if (expression.contains(operator)) {
                return false;
            }
        }
        return true;
    }

    private List<String> getFirstPart(String expression) {
        int firstOperatorIndex = Integer.MAX_VALUE;
        String firstOperatorFound = "";
        for (String operator : operators.keySet()) {
            int operatorIndex = expression.indexOf(operator);
            if (operatorIndex != -1 && operatorIndex < firstOperatorIndex) {
                firstOperatorIndex = operatorIndex;
                firstOperatorFound = operator;
            }
        }
        String numeric = expression.split(Pattern.quote(firstOperatorFound), -1)[0];
        return Arrays.asList(numeric, firstOperatorFound);
    }

    private Expression<Integer> buildGlobalExpression(String expression){
        if (simpleExpression(expression)) {
            return new SingleTerm(Integer.parseInt(expression));
        }
        List<String> expressionParts = splitToValuesAndOperatorsList(expression);
        return buildExpression(expressionParts);
    }

    private Expression<Integer> buildExpression(List<String> expressionParts) {
        Expression<Integer> previousExpression = null;
        String operator = null;
        int currentIndex = 0;

        for (String expressionPart : expressionParts) {
            if (isOperator(expressionPart)) {
                operator = expressionPart;
            } else {
                if (previousExpression == null) {
                    previousExpression = new SingleTerm<>(Integer.parseInt(expressionPart));
                } else {
                    List<String> notReadElements = expressionParts.subList(currentIndex, expressionParts.size() );
                    previousExpression = new ComplexExpression<>(previousExpression, buildExpression(notReadElements), operators.get(operator));
                    break;
                }
            }
            currentIndex++;
        }
        return previousExpression;
    }

    private Expression<Integer> buildExpression(List<String> expressionParts) {
        Expression<Integer> previousExpression = null;
        String operator = null;
        int currentIndex = 0;

        for (String expressionPart : expressionParts) {
            if (isOperator(expressionPart)) {
                operator = expressionPart;
            } else {
                if (previousExpression == null) {
                    previousExpression = new SingleTerm<>(Integer.parseInt(expressionPart));
                } else {
                    List<String> notReadElements = expressionParts.subList(currentIndex, expressionParts.size() );
                    previousExpression = new ComplexExpression<>(previousExpression, buildExpression(notReadElements), operators.get(operator));
                    break;
                }
            }
            currentIndex++;
        }
        return previousExpression;
    }

    private boolean isOperator(String expressionPart) {
        return operators.keySet().contains(expressionPart.trim());
    }

    private List<String> splitToValuesAndOperatorsList(String expression) {
        List<String> parts = new ArrayList<>();
        String currentExpression = expression;
        while (!simpleExpression(currentExpression)) {
            List<String> newParts = getFirstPart(currentExpression);
            parts.addAll(newParts);
            currentExpression = currentExpression.substring(newParts.get(0).length() + newParts.get(1).length());
        }
        // currentExpression shouldn't be empty, or blank
        parts.add(currentExpression);
        return parts;
    }



}
