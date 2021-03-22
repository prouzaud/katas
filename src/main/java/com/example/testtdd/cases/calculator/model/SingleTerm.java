package com.example.testtdd.cases.calculator.model;

public class SingleTerm<T> implements Expression<T>{

    T value;

    @Override
    public T calculate() {
        return value;
    }

    public SingleTerm(T value) {
        this.value = value;
    }
}
