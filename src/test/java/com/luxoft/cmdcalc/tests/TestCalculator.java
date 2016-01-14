package com.luxoft.cmdcalc.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.Test;

import com.luxoft.cmdcalc.model.Calculator;
import com.luxoft.cmdcalc.model.CalculatorException;

public class TestCalculator {
//
//	private URL dirToFile = this.getClass().getClassLoader().getResource("src/test/resources/");
	private File dir = new File("src/test/resources/");

	private Map<String, Double> parseDataFromFile(BufferedReader br) {
		Map<String, Double> parsedFile = new HashMap<String, Double>();

		try {
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] data = line.split(";");
				parsedFile.put(data[0], Double.valueOf(data[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		return parsedFile;
	}

	@Test
	public void testSimpleAddition() {
		File testFile = new File(dir,"testSimpleAddition.txt");
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
//					System.out.println(expectedResult);
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

}
