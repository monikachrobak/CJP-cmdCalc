package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

import com.luxoft.cmdcalc.model.CalculatorException;

public class MultiplicationOperator extends MathOperator {

	public MultiplicationOperator() {
		super("*", 2);
	}
	
	public MultiplicationOperator(String symbol, int priority) {
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
		stack.push(operandB * operandA);
	}
	
	@Override
	public Boolean checkPrioritiesAndAddToStackNewOperator(Deque<Operator> stack, List<Object> outputList) {
		// check if stack is empty or check priorities
		while (!stack.isEmpty() && (this.getPriority() <= (stack.peek()).getPriority())) {
			outputList.add(stack.pop());
		}
		// add new operator to the stack
		stack.push(this);
		return true; // return hasOperandOccuredLastTime value
	}

}
