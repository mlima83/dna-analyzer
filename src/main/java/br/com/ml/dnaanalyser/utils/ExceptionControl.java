package br.com.ml.dnaanalyser.utils;

import java.util.function.Function;

public class ExceptionControl implements Function<Throwable, Boolean> {
	private Throwable ex;
	@Override
	public Boolean apply(Throwable t) {
		this.ex = t;
		return false;
	}
	
	public boolean isError() throws Throwable { 
		if (ex != null) {
			throw ex;
		}
		return false;
	}
}
