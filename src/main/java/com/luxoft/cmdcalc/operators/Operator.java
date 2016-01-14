package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.List;

import com.luxoft.cmdcalc.model.CalculatorException;

public interface Operator {

	int getPriority();
	String getSymbol();	
	public abstract Boolean checkPrioritiesAndAddToStackNewOperator(Deque<Operator> stack, List<Object> outputList) throws CalculatorException;

}
