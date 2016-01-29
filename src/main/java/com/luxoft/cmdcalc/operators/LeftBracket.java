package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.List;


public class LeftBracket extends Bracket {

	public LeftBracket() {
		super("(", 0);
	}
	
	public LeftBracket(String symbol, int priority) {
		super(symbol, priority);
	}
	
	@Override
	public Boolean addOperatorsFromStackToOutputListIfNeededAndAddToStackNewOperator(Deque<Operator> stack, List<Object> outputList) {
		stack.push(this);
		return true; // return hasOperandOccuredLastTime value
	}

}