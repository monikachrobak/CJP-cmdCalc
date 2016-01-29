package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.List;

import com.luxoft.cmdcalc.model.CalculatorException;

public abstract class MathOperator implements Operator {
	private int priority;
	private String symbol;

	public MathOperator(String symbol, int priority) {
		this.symbol = symbol;
		this.priority = priority;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Take needed number of operands from given stack and make proper operation
	 * 
	 * @param stack
	 *            - prepared stack which contains operands only
	 * @throws CalculatorException
	 */
	public abstract void calculate(Deque<Double> stack) throws CalculatorException;
	
	@Override
	public Boolean addOperatorsFromStackToOutputListIfNeededAndAddToStackNewOperator(Deque<Operator> stack,
			List<Object> outputList) {
		while (!stack.isEmpty() && (this.getPriority() <= (stack.peek()).getPriority())) {
			outputList.add(stack.pop());
		}
		stack.push(this);
		return true; // return hasOperandOccuredLastTime value
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + priority;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MathOperator other = (MathOperator) obj;
		if (priority != other.priority)
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

}
