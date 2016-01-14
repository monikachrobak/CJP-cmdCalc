package com.luxoft.cmdcalc.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.luxoft.cmdcalc.operators.MathOperator;
import com.luxoft.cmdcalc.operators.AdditionOperator;
import com.luxoft.cmdcalc.operators.Bracket;
import com.luxoft.cmdcalc.operators.DivisionOperator;
import com.luxoft.cmdcalc.operators.LeftBracket;
import com.luxoft.cmdcalc.operators.MultiplicationOperator;
import com.luxoft.cmdcalc.operators.Operator;
import com.luxoft.cmdcalc.operators.RightBracket;
import com.luxoft.cmdcalc.operators.SubtractionOperator;

public final class Calculator {

	private static Map<String, Operator> operators = new HashMap<String, Operator>();

	/**
	 * Clear operators Map and add all valid operators with default priorities
	 */
	public static void initOperators() {
		operators.clear();
		operators.put("+", new AdditionOperator());
		operators.put("-", new SubtractionOperator());
		operators.put("*", new MultiplicationOperator());
		operators.put("/", new DivisionOperator());
		operators.put("(", new LeftBracket());
		operators.put(")", new RightBracket());
	}

	/**
	 * Adds specified operator to the operator Map
	 * 
	 * @param symbol
	 *            - character representation of operator
	 * @param operator
	 */
	public static void addOperator(String symbol, Operator operator) {
		operators.put(symbol, operator);
	}

	/**
	 * Parse number from String to Double. If String object is null or empty,
	 * the {@link CalculatorException} is thrown.
	 * 
	 * @param stringNumber
	 * @return Double representation of a given parameter
	 * @throws CalculatorException
	 */
	public static Double parseNumber(String stringNumber) throws CalculatorException {
		Double number = null;
		if (stringNumber.equals("") || stringNumber.equals(".")) {
			throw new CalculatorException("Error: Invalid number format");
		} else {
			number = Double.valueOf(stringNumber);
		}
		return number;
	}

	/**
	 * Converts given String with expression into numbers and operands and adds
	 * them to the output list in correct order
	 * 
	 * @param line
	 *            - String with expression
	 * @return List of objects (Doubles and Characters) as RPN list
	 * @throws CalculatorException
	 */
	public static List<Object> convertStringLineToRPN(String line) throws CalculatorException {
		StringBuilder numberBuilder = new StringBuilder("");
		Deque<Operator> stack = new LinkedList<Operator>(); // for operators
		List<Object> outputList = new ArrayList<Object>(); // for RPN output

		if (line.startsWith("-(")) {
			line = "0" + line;
		}

		interpretGivenLine(line, numberBuilder, stack, outputList);

		if (!numberBuilder.toString().equals("")) {
			// if number was putted as last - have to parse it
			Double number = parseNumber(numberBuilder.toString());
			outputList.add(number);
			// System.out.println(number);
			// clearing StringBuilder content
			numberBuilder.setLength(0);
			numberBuilder.trimToSize();
		}
		if (!stack.isEmpty()) {
			// after reading all numbers and operators stack can't contain any
			// parantheses
			if (stack.contains(operators.get("("))) {
				throw new CalculatorException("Error: Missing parentheses");
			}
			// have to pop all elements from stack to outputList
			outputList.addAll(stack);
		}
		return outputList;
	}

	private static void interpretGivenLine(String line, StringBuilder numberBuilder, Deque<Operator> stack,
			List<Object> outputList) throws CalculatorException {
		boolean isFloat = false;
		// true because only number, '(', '-' or '.' can be first in a line
		boolean hasOperandOccuredLastTime = true;
		boolean isMinusNumber = false;
		for (char c : line.toCharArray()) {
			if ((c >= '0') & (c <= '9')) {
				hasOperandOccuredLastTime = false;
				numberBuilder.append(c);
			} else if (c == '.') {
				hasOperandOccuredLastTime = false;
				if (!isFloat) {
					numberBuilder.append(c);
					isFloat = true;
				} else {
					throw new CalculatorException("Error: Double dot occurance");
				}
			} else if (c == '-' & hasOperandOccuredLastTime) {
				if (hasOperandOccuredLastTime & !isMinusNumber) {
					numberBuilder.append(c);
					isMinusNumber = true;
				} else {
					throw new CalculatorException("Error: Too many minus operators");
				}
			} else {
				isFloat = false;
				if (!numberBuilder.toString().equals("")) {
					Double number = parseNumber(numberBuilder.toString());
					outputList.add(number);
					// System.out.println(number);
					// clearing StringBuilder content
					numberBuilder.setLength(0);
					numberBuilder.trimToSize();
					isMinusNumber = false;
				}
				Operator operator = operators.get(String.valueOf(c));
				if (operator != null) {
					if ((operator instanceof MathOperator) & hasOperandOccuredLastTime) {
						throw new CalculatorException("Error: Two operators next to each other");
					}
					hasOperandOccuredLastTime = operator.checkPrioritiesAndAddToStackNewOperator(stack, outputList);
				} else {
					throw new CalculatorException("Error: Letter or invalid operator");
				}
			}
		}
	}

	/**
	 * Calculate result of expression from prepared list. Throws
	 * CalculatorException in case the list is not correctly prepared
	 * 
	 * @param rpnList
	 *            - specially prepared RPN list (with Doubles and Characters) or
	 *            result of {@link #convertStringLineToRPN(String)
	 *            convertStringLineToRPN} method
	 * @return result of expression as Double
	 * @throws CalculatorException
	 */
	public static Double calculateRPNOutput(List<Object> rpnList) throws CalculatorException {
		Deque<Double> stack = new LinkedList<Double>();
		Double result = new Double(0);
		if (rpnList == null || rpnList.isEmpty()) {
			throw new CalculatorException("Error: Empty RPN list");
		}
		for (Object outputElement : rpnList) {
			if (outputElement instanceof Double) {
				stack.push((Double) outputElement);
			} else if (outputElement instanceof Operator) {
				if (outputElement instanceof Bracket) {
					throw new CalculatorException("Error: Bracket in the RPN List");
				}
				if (outputElement != null) {
					((MathOperator) outputElement).calculate(stack);
				}
			} else {
				throw new CalculatorException("Error: Invalid type of element in a list");
			}
		}
		result = stack.pop();
		if (result == null) {
			throw new CalculatorException("Error: No result obtained");
		}
		if (!stack.isEmpty()) {
			throw new CalculatorException("Error: Invalid number of operators");
		}
		return result;
	}

	public static void main(String args[]) {
		initOperators();
		System.out.println("Program started. Type 'exit' to stop.");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean stopCondition = false;
		while (!stopCondition) {
			try {
				// Reverse Polish Notation - RPN
				System.out.println("Type expression and press 'Enter':");
				String line = br.readLine();
				if (line.equalsIgnoreCase("exit")) {
					stopCondition = true;
				} else {
					List<Object> RPN = convertStringLineToRPN(line);

					// check RPN output
					// for (Object o : RPN) {
					// if(o instanceof Operator) {
					// System.out.print(((Operator) o).getSymbol() + " ");
					// } else {
					// System.out.print(o + " ");
					// }
					// }
					// System.out.println();

					// calculate result
					System.out.println("Result: " + calculateRPNOutput(RPN));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CalculatorException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
