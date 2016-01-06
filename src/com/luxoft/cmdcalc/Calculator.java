package com.luxoft.cmdcalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class Calculator {

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
	 * @param line - String with expression
	 * @return List of objects (Doubles and Characters) as RPN list
	 * @throws CalculatorException
	 */
	public static List<Object> convertStringLineToRPN(String line) throws CalculatorException {
		StringBuilder numberBuilder = new StringBuilder("");
		Double number = null; // parsed number
		boolean isFloat = false;
		// true because only number, '(', '-' or '.' can be first in a line
		boolean hasOperandOccuredLastTime = true;
		boolean isMinusNumber = false;
		Stack<Character> stack = new Stack<Character>(); // for operators
		List<Object> outputList = new ArrayList<Object>(); // for RPN output
		Map<Character, Integer> priority = new HashMap<Character, Integer>();

		// priorities of operators
		priority.put('(', 0);
		priority.put('+', 1);
		priority.put('-', 1);
		priority.put('*', 2);
		priority.put('/', 2);
		
		if (line.startsWith("-(")) {
			line = "0" + line;
		}

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
					number = parseNumber(numberBuilder.toString());
					outputList.add(number);
					// System.out.println(number);
					// clearing StringBuilder content
					numberBuilder.setLength(0);
					numberBuilder.trimToSize();
					isMinusNumber = false;
				}

				switch (c) {
				case '(':
					hasOperandOccuredLastTime = true;
					stack.push(c);
					break;
				case ')':
					hasOperandOccuredLastTime = false;
					Character tempC;
					try {
						while ((tempC = stack.pop()) != '(') {
							outputList.add(tempC);
						}
					} catch (EmptyStackException e) {
						throw new CalculatorException("Error: Missing parentheses");
					}
					break;
				// all this operators does the same
				case '+':
				case '-':
				case '*':
				case '/':
					// can't have two operators next to each other - except '-'
					if (hasOperandOccuredLastTime) {
						throw new CalculatorException("Error: Two operators next to each other");
					}
					// check if stack is empty or check priorities
					int priorityOfC = priority.get(c);
					while (!stack.isEmpty() && (priorityOfC <= priority.get(stack.peek()))) {
						outputList.add(stack.pop());
					}
					hasOperandOccuredLastTime = true;
					// add new operator to the stack
					stack.push(c);
					break;
				default:
					hasOperandOccuredLastTime = false;
					throw new CalculatorException("Error: Letter or invalid operator");
				}
			}
		}
		if (!numberBuilder.toString().equals("")) {
			// if number was putted as last - have to parse it
			number = parseNumber(numberBuilder.toString());
			outputList.add(number);
			// System.out.println(number);
			// clearing StringBuilder content
			numberBuilder.setLength(0);
			numberBuilder.trimToSize();
		}
		if (!stack.isEmpty()) {
			// after reading all numbers and operators stack can't contain any
			// parantheses
			if (stack.contains('(')) {
				throw new CalculatorException("Error: Missing parentheses");
			}
			// have to pop all elements from stack to outputList
			Collections.reverse(stack); // because next line adds all elements
										// from stack's bottom
			outputList.addAll(stack); /// reverse operation to popping all
										/// elements
		}
		return outputList;
	}

	/**
	 * Calculate result of expression from prepared list. Throws
	 * CalculatorException in case the list is not correctly prepared
	 * 
	 * @param rpnList
	 *            - specially prepared RPN list (with Doubles and Characters) or result of
	 *            {@link #convertStringLineToRPN(String) convertStringLineToRPN}
	 *            method
	 * @return result of expression as Double
	 * @throws CalculatorException
	 */
	public static Double calculateRPNOutput(List<Object> rpnList) throws CalculatorException {
		Stack<Double> stack = new Stack<Double>();
		Double result = new Double(0);
		if (rpnList == null || rpnList.isEmpty()) {
			throw new CalculatorException("Error: Empty RPN list");
		}
		for (Object outputElement : rpnList) {
			if (outputElement instanceof Double) {
				stack.push((Double) outputElement);
			} else if (outputElement instanceof Character) {
				Double operandA = null;
				Double operandB = null;
				try {
					operandA = stack.pop();
					operandB = stack.pop();
				} catch (EmptyStackException e) {
					throw new CalculatorException("Error: Not enought operands");
				}
				switch ((Character) outputElement) {
				case '+':
					stack.push(operandB + operandA);
					break;
				case '-':
					stack.push(operandB - operandA);
					break;
				case '*':
					stack.push(operandB * operandA);
					break;
				case '/':
					stack.push(operandB / operandA);
					break;
				default:
					throw new CalculatorException("Error: Invalid operator");
				}
			} else {
				throw new CalculatorException("Error: Invalid type of element in a list");
			}
		}
		result = stack.pop();
		if (!stack.isEmpty()) {
			throw new CalculatorException("Error: Invalid number of operators");
		}
		return result;
	}

	public static void main(String args[]) {
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
					/*
					 * for (Object o : RPN) { System.out.print(o + " "); }
					 */
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
