package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.EmptyStackException;
import java.util.List;

import com.luxoft.cmdcalc.model.CalculatorException;

public class AdditionOperator extends MathOperator {

	public AdditionOperator() {
		super("+", 1);
	}
	
	public AdditionOperator(String symbol, int priority) {
		super(symbol, priority);
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

	@Override
	public void calculate(Deque<Double> stack) throws CalculatorException {
		Double operandA = null;
		Double operandB = null;
		try {
			operandA = stack.pop();
			operandB = stack.pop();
		} catch (EmptyStackException e) {
			throw new CalculatorException("Error: Not enought operands");
		}
		stack.push(operandB + operandA);
	}
}
