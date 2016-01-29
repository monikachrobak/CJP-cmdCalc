package com.luxoft.cmdcalc.operators;

import java.util.Deque;
import java.util.List;

import com.luxoft.cmdcalc.model.CalculatorException;

public interface Operator {

	/**
	 * Returns priority of the operator
	 * 
	 * @return value representing operator priority
	 */
	int getPriority();

	/**
	 * Returns operator's symbol
	 * 
	 * @return string representation of the operator
	 */
	String getSymbol();

	/**
	 * In case of <b>MathOperator</b>: compares current operator with another
	 * operators from given stack and checks their priorities.<br>
	 * If stack is not empty or the priority of current operator is equal or
	 * less than priority of the operator from top of stack, than operator from
	 * the stack must be added to the given output list, else the current
	 * operator is added to the given stack. <br>
	 * Operations continues unless current operator is added to the stack. <br>
	 * In case of <b>Bracket</b>: if current operator is opening bracket than
	 * adds it to the stack else adds elements from the stack to the output list
	 * until opening bracket occurred (deletes it from stack).
	 * 
	 * @param stack
	 *            - stack implementation which contains operators
	 * @param outputList
	 *            - RPN output list which is in process of preparation
	 * @return true if this operator can't stand next to another operator, false
	 *         otherwise
	 * @throws CalculatorException
	 */
	Boolean addOperatorsFromStackToOutputListIfNeededAndAddToStackNewOperator(Deque<Operator> stack,
			List<Object> outputList) throws CalculatorException;

}
