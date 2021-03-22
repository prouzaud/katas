package com.example.testtdd.cases.calculator.model;

import java.util.function.BiFunction;

public class ComplexExpression<T> implements Expression<T>{

    Expression<T> firstExpression;
    Expression<T> secondExpression;
    BiFunction<T, T, T> operator;

    @Override
    public T calculate() {
        T value1 = firstExpression.calculate();
        T value2 = secondExpression.calculate();
        return operator.apply(value1, value2);
    }

    public ComplexExpression(Expression<T> firstExpression, Expression<T> secondExpression, BiFunction<T, T, T> operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }
}
