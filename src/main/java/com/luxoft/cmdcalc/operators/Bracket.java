package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.List;

import com.luxoft.cmdcalc.model.CalculatorException;

public abstract class Bracket implements Operator {

	private int priority;
	private String symbol;
	
	public Bracket(String symbol, int priority){
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

	@Override
	public abstract Boolean checkPrioritiesAndAddToStackNewOperator(Deque<Operator> stack, List<Object> outputList) throws CalculatorException;

}
