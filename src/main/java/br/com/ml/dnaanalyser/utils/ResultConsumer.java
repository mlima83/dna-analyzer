package br.com.ml.dnaanalyser.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ResultConsumer<T> implements Consumer<T> {
	private T result;
	private Predicate<T> acceptResult;

	public ResultConsumer() {}
	public ResultConsumer(T defaultResult) {
		this.result = defaultResult;
	}

	public ResultConsumer(T defaultResult, Predicate<T> acceptResult) {
		this.result = defaultResult;
		this.acceptResult = acceptResult;
	}

	@Override
	public void accept(T result) {
		if (this.acceptResult != null && !this.acceptResult.test(result)) {
			return;
		}
		this.result = result;
	}

	public T getResult() {
		return result;
	}
}