package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.NoSuchElementException;

import com.luxoft.cmdcalc.model.CalculatorException;

public class AdditionOperator extends MathOperator {

	public AdditionOperator() {
		super("+", 1);
	}
	
	public AdditionOperator(String symbol, int priority) {
		super(symbol, priority);
	}

	@Override
	public void calculate(Deque<Double> stack) throws CalculatorException {
		Double operandA = null;
		Double operandB = null;
		try {
			operandA = stack.pop();
			operandB = stack.pop();
		} catch (NoSuchElementException e) {
			throw new CalculatorException("Error: Not enought operands");
		}
		stack.push(operandB + operandA);
	}
}
