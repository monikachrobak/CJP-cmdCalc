package com.luxoft.cmdcalc.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.luxoft.cmdcalc.model.Calculator;
import com.luxoft.cmdcalc.model.CalculatorException;

public class TestCalculator {

	private File dir = new File("src/test/resources/");

	private Map<String, Double> parseDataFromFile(BufferedReader br) {
		Map<String, Double> parsedFile = new TreeMap<String, Double>();

		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(";");
				parsedFile.put(data[0].trim(), Double.valueOf(data[1].trim()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return parsedFile;
	}

	@Test
	public void testSimpleExpressions() {
		File testFile = new File(dir, "testSimpleExpressions.txt");
		if (!testFile.exists()) {
			fail();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(testFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		Map<String, Double> data = parseDataFromFile(br);
		Iterator<String> iterator = data.keySet().iterator();
		Calculator.initOperators();
		while (iterator.hasNext()) {
			try {
				// Reverse Polish Notation - RPN
				String expression = iterator.next();
				if (expression.equalsIgnoreCase("exit")) {
					break;
				} else {
					Double expectedResult = data.get(expression);
					// System.out.println(expectedResult);
					List<Object> RPN = Calculator.convertStringLineToRPN(expression);

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
					assertThat(Calculator.calculateRPNOutput(RPN), equalTo(expectedResult));
				}
			} catch (CalculatorException e) {
				System.out.println(e.getMessage());
				fail();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
	}

	@Test
	public void testSimpleExpressionsWithExceptions() {
		File testFile = new File(dir, "testSimpleExpressionsWithExceptions.txt");
		if (!testFile.exists()) {
			fail();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(testFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		Calculator.initOperators();
		String expression = "";
		try {
			while ((expression = br.readLine().trim()) != null) {
				try {
					// Reverse Polish Notation - RPN
					if (expression.equalsIgnoreCase("exit")) {
							break;
					} else {
						List<Object> RPN = Calculator.convertStringLineToRPN(expression);
						Calculator.calculateRPNOutput(RPN);
					}
				} catch (CalculatorException e) {
					System.out.println(e.getMessage());
					continue;
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
				fail();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testExpressionsWithBrackets() {
		File testFile = new File(dir, "testExpressionsWithBrackets.txt");
		if (!testFile.exists()) {
			fail();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(testFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		Map<String, Double> data = parseDataFromFile(br);
		Iterator<String> iterator = data.keySet().iterator();
		Calculator.initOperators();
		while (iterator.hasNext()) {
			try {
				// Reverse Polish Notation - RPN
				String expression = iterator.next();
				if (expression.equalsIgnoreCase("exit")) {
					break;
				} else {
					Double expectedResult = data.get(expression);
					List<Object> RPN = Calculator.convertStringLineToRPN(expression);
					assertThat(Calculator.calculateRPNOutput(RPN), equalTo(expectedResult));
				}
			} catch (CalculatorException e) {
				System.out.println(e.getMessage());
				fail();
			} catch (Exception e) {
				e.printStackTrace();
				fail();
			}
		}
	}
	
	@Test
	public void testExpressionsWithBracketsWithEceptions() {
		File testFile = new File(dir, "testExpressionsWithBracketsWithEceptions.txt");
		if (!testFile.exists()) {
			fail();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(testFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			fail();
		}
		Calculator.initOperators();
		String expression = "";
		try {
			while ((expression = br.readLine().trim()) != null) {
				try {
					// Reverse Polish Notation - RPN
					if (expression.equalsIgnoreCase("exit")) {
							break;
					} else {
						List<Object> RPN = Calculator.convertStringLineToRPN(expression);
						Calculator.calculateRPNOutput(RPN);
					}
				} catch (CalculatorException e) {
					System.out.println(e.getMessage());
					continue;
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
				fail();
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

}
