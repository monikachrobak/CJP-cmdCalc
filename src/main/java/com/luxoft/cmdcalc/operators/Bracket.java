package com.luxoft.cmdcalc.operators;

public abstract class Bracket implements Operator {

	private int priority;
	private String symbol;

	public Bracket(String symbol, int priority) {
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
		Bracket other = (Bracket) obj;
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
