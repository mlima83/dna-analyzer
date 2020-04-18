package br.com.ml.dnaanalyser.exception;

public class ExecutionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ExecutionException(String message) {
		super(message);
	}

	public ExecutionException(String message, InterruptedException e) {
		super(message, e);
	}

}
