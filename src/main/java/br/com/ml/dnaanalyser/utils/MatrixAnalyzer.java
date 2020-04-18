package br.com.ml.dnaanalyser.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ml.dnaanalyser.exception.ValidationException;


public class MatrixAnalyzer implements Supplier<Boolean> {
	final static Logger log = LoggerFactory.getLogger(MatrixAnalyzer.class);
	private List<String> matrix;
	private Direction direction;
	private final String PATTERN = ".*(GGGG|CCCC|AAAA|TTTT).*";

	public MatrixAnalyzer(List<String> matrix, Direction direction) throws CompletionException, ValidationException {
		if (matrix == null || matrix.size() == 0) {
			throw new ValidationException("Informe a cadeia de DNA.");
		}
		this.matrix = matrix;
		this.direction = direction;
	}

	@Override
	public Boolean get() {
		List<String> matrixToProcess = null;
		if (Direction.HORIZONTAL.equals(direction)) {
			matrixToProcess = this.matrix;

		} else if (Direction.VERTICAL.equals(direction)) {
			matrixToProcess = convertToVertical(this.matrix);

		} else if (Direction.DIAGONAL.equals(direction)) {
			matrixToProcess = convertToDiagonal(this.matrix);
			matrixToProcess.addAll(convertToDiagonalInverse(this.matrix));
		}
		boolean result = matrixToProcess.parallelStream().anyMatch(line -> line.matches(PATTERN));
		log.debug(String.format("Resultado %s %s", direction, result));
		return result;
	}

	private List<String> convertToDiagonal(List<String> matrix) throws ValidationException {
		return convertTo(matrix, true);
	}

	private List<String> convertToDiagonalInverse(List<String> matrix) {
		return convertToInverse(matrix, true);
	}

	private List<String> convertToVertical(List<String> matrix) throws ValidationException {
		return convertTo(matrix, false);
	}

	private List<String> convertTo(List<String> matrix, boolean diagonal) throws ValidationException {
		int matrixConvertedSize = diagonal ? (matrix.size() * 2 - 1) : matrix.size();
		String matrixConverted[] = new String[matrixConvertedSize];
		for (int lineIndex = 0; lineIndex < matrix.size(); lineIndex++) {
			String line = matrix.get(lineIndex);
			validateLine(line, matrix.size());
			for (int colIndex = 0; colIndex < line.length(); colIndex++) {
				int position = diagonal ? (colIndex + lineIndex) : colIndex;
				String newLine = matrixConverted[position] == null ? "" : matrixConverted[position];
				matrixConverted[position] = newLine + line.charAt(colIndex);
			}
		}
		return Arrays.stream(matrixConverted).collect(Collectors.toList());
	}

	private List<String> convertToInverse(List<String> matrix, boolean diagonal) {
		int matrixConvertedSize = diagonal ? (matrix.size() * 2 - 1) : matrix.size();
		String matrixConverted[] = new String[matrixConvertedSize];
		for (int lineIndex = matrix.size() - 1; lineIndex >= 0; lineIndex--) {
			String line = matrix.get(lineIndex);
			for (int colIndex = 0; colIndex < line.length(); colIndex++) {
				int position = diagonal ? (colIndex + matrix.size() - lineIndex - 1) : colIndex;
				String newLine = matrixConverted[position] == null ? "" : matrixConverted[position];
				matrixConverted[position] = newLine + line.charAt(colIndex);
			}
		}
		return Arrays.stream(matrixConverted).collect(Collectors.toList());
	}
	
	private void validateLine(String line, int matrixSize) throws ValidationException {
		if (matrixSize != line.length()) {
			throw new ValidationException(
					"DNA sequence is outside the standard of supported size, a table must be square (NxN).​");
		} else if (line.matches(".*[^ACGT].*")) {
			throw new ValidationException("The DNA must contain only (A, T, C, G)");
		}
	}
}