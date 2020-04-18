package br.com.ml.dnaanalyser.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import br.com.ml.dnaanalyser.dto.DnaDTO;
import br.com.ml.dnaanalyser.exception.ExecutionException;
import br.com.ml.dnaanalyser.exception.ValidationException;
import br.com.ml.dnaanalyser.model.Dna;
import br.com.ml.dnaanalyser.model.Species;
import br.com.ml.dnaanalyser.repository.DnaRepository;
import br.com.ml.dnaanalyser.utils.ConvertUtils;
import br.com.ml.dnaanalyser.utils.Direction;
import br.com.ml.dnaanalyser.utils.ExceptionControl;
import br.com.ml.dnaanalyser.utils.MatrixAnalyzer;
import br.com.ml.dnaanalyser.utils.ResultConsumer;

@Service
public class DnaService {
	
	final static Logger log = LoggerFactory.getLogger(DnaService.class);
	
	@Autowired
	private DnaRepository dnaRepository;
	
	@Autowired
	private ConvertUtils convertUtils;

	public Dna dnaAnalize(@Valid DnaDTO dnaDTO) throws IOException, ValidationException {
		Long hashId = convertUtils.generateHashDna(dnaDTO);
		Optional<Dna> dnaDB = dnaRepository.findById(hashId);
		if (dnaDB.isPresent()) {
			return dnaDB.get();
		}
		boolean isSimian = isSimian(dnaDTO.getDna().toArray(new String[dnaDTO.getDna().size()]));
		Dna dna = convertUtils.dtoToModel(dnaDTO, hashId);
		dna.setSpecies(isSimian ? Species.SIMIAN : Species.HUMAN);
		return dna;
	}
	
	@Async
	public Future<Dna> save(Dna dna) {
		if (dna.getId() == null 
				|| dna.getDna() == null 
				|| dna.getDna().length < 1
				|| dna.getSpecies() == null) {
			throw new ValidationException("Invalid data for DNA.");
		}
		return new AsyncResult<Dna>(dnaRepository.save(dna));
	}
	
	public List<DnaDTO> listAll() {
		List<Dna> dnas = (List<Dna>) dnaRepository.findAll();
		return  dnas.parallelStream()
				.map( dna -> {
					try {
						return convertUtils.modelToDto(dna);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toList());
	}
	
	@SuppressWarnings("rawtypes")
	private boolean isSimian(String[] dna) throws ValidationException {
		ExecutorService executor = null;
		try {
			executor = Executors.newCachedThreadPool();
			List<String> matrix = Arrays.asList(dna);
			ResultConsumer<Boolean> resultConsumer = new ResultConsumer<>(false, (result) -> result);
			ExceptionControl exceptionControl = new ExceptionControl();
			CompletableFuture all = CompletableFuture.allOf(
					createCompletableFuture(executor, new MatrixAnalyzer(matrix, Direction.HORIZONTAL), resultConsumer, exceptionControl),
					createCompletableFuture(executor, new MatrixAnalyzer(matrix, Direction.VERTICAL), resultConsumer, exceptionControl),
					createCompletableFuture(executor, new MatrixAnalyzer(matrix, Direction.DIAGONAL), resultConsumer, exceptionControl));
			all.join();
			while (!exceptionControl.isError() && !resultConsumer.getResult() && !all.isDone());
			log.debug(String.format("Resultado isSimian: %s", resultConsumer.getResult()));
			finalizeExecutor(executor);
			return resultConsumer.getResult();
		}  catch (Throwable e) {
			finalizeExecutor(executor);
			handleException(e);
		}
		return false;
	}
	
	private void finalizeExecutor(ExecutorService executor) throws ValidationException {
		try {
			if (executor != null) {
				executor.shutdownNow();
				executor.awaitTermination(1, TimeUnit.SECONDS);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new ExecutionException("Could not finish execution.", e);
		}
	}
	
	private void handleException(Throwable e) throws ValidationException {
		if (e instanceof CompletionException
				&& e.getCause() instanceof ArrayIndexOutOfBoundsException) {
			throw new ValidationException(
					"DNA sequence is outside the standard of supported size, a table must be square (NxN).​",
					e.getCause());
		} else if (e instanceof ValidationException) {
			throw (ValidationException) e;
		} else if (e.getCause() instanceof ValidationException) {
			throw (ValidationException) e.getCause();
		} else {
			e.printStackTrace();
			throw new ValidationException(
					"Unfortunately an unexpected error occurred, please check the parameters entered.", e);
		}
		
	}
	
	private CompletableFuture<Void> createCompletableFuture(
			ExecutorService executor,
			MatrixAnalyzer matrixAnalyzer,  
			ResultConsumer<Boolean> resultConsumer, 
			ExceptionControl exceptionControl) {
		return CompletableFuture
				.supplyAsync(matrixAnalyzer, executor)
				.exceptionally(exceptionControl)
				.thenAcceptAsync(resultConsumer);
	}

}
