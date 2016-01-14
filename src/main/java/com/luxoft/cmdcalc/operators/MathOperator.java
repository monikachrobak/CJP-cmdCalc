package com.luxoft.cmdcalc.operators;

import java.util.Deque;

import com.luxoft.cmdcalc.model.CalculatorException;

public abstract class MathOperator implements Operator {
	private int priority;
	private String symbol;
	
	public MathOperator(String symbol, int priority){
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
	
	public abstract void calculate(Deque<Double> stack) throws CalculatorException;

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
