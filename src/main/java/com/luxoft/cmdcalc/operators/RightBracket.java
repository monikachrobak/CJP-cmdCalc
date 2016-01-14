package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.EmptyStackException;
import java.util.List;

import com.luxoft.cmdcalc.model.CalculatorException;

public class RightBracket extends Bracket {

	public RightBracket() {
		super(")", 0);
	}

	public RightBracket(String symbol, int priority) {
		super(symbol, priority);
	}

	@Override
	public Boolean checkPrioritiesAndAddToStackNewOperator(Deque<Operator> stack, List<Object> outputList)
			throws CalculatorException {
		try {
			Operator operator;
			while (!((operator = stack.pop()) instanceof LeftBracket)) {
				outputList.add(operator);
			}
		} catch (EmptyStackException e) {
			throw new CalculatorException("Error: Missing parentheses");
		}
		return false; // return hasOperandOccuredLastTime value
	}

}