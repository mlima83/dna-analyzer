package br.com.ml.dnaanalyser.service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.ml.dnaanalyser.dto.DnaDTO;
import br.com.ml.dnaanalyser.exception.ValidationException;
import br.com.ml.dnaanalyser.model.Dna;
import br.com.ml.dnaanalyser.model.Species;
import br.com.ml.dnaanalyser.repository.DnaRepository;
import br.com.ml.dnaanalyser.utils.ConvertUtils;

public class DnaServiceTest {
	
	@Mock
	private DnaRepository dnaRepository;
	
	@Spy
	private ConvertUtils convertUtils = new ConvertUtils();
	
	@InjectMocks
	private DnaService dnaService;
	
	
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void dnaAnalizeSimianHorizontalTest() throws IOException {
		System.out.println("dnaAnalizeSimianHorizontalTest");
		List<String> dnaCode = Arrays.asList("CTGGTA", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCCTA", 
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		Dna dnaResult = dnaService.dnaAnalize(dnaDTO);
		
		assertEquals(Species.SIMIAN, dnaResult.getSpecies());
	}
	
	
	@Test
	public void dnaAnalizeSimianVerticalTest() throws IOException {
		System.out.println("dnaAnalizeSimianVerticalTest");
		List<String> dnaCode = Arrays.asList("CTGTGA", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCTTA", 
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		Dna dnaResult = dnaService.dnaAnalize(dnaDTO);
		
		assertEquals(Species.SIMIAN, dnaResult.getSpecies());
	}
	
	
	@Test
	public void dnaAnalizeSimianDiagonalTest() throws IOException {
		System.out.println("dnaAnalizeSimianDiagonalTest");
		List<String> dnaCode = Arrays.asList("CTGAAA", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCTTA", 
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		Dna dnaResult = dnaService.dnaAnalize(dnaDTO);
		
		assertEquals(Species.SIMIAN, dnaResult.getSpecies());
	}
	
	@Test
	public void dnaAnalizeSimianDiagonalInverseTest() throws IOException {
		System.out.println("dnaAnalizeSimianDiagonalInverseTest");
		List<String> dnaCode = Arrays.asList("CGTATA", 
											 "CTTTGC", 
											 "TAGTTT", 
											 "TGAGGT", 
											 "CCCGTA", 
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		Dna dnaResult = dnaService.dnaAnalize(dnaDTO);
		
		assertEquals(Species.SIMIAN, dnaResult.getSpecies());
	}
	
	
	@Test
	public void dnaAnalizeHumanTest() throws IOException {
		System.out.println("dnaAnalizeHumanTest");
		List<String> dnaCode = Arrays.asList("CTGGTA", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCGTA", 
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		
		Dna dnaResult = dnaService.dnaAnalize(dnaDTO);
		
		assertEquals(Species.HUMAN, dnaResult.getSpecies());
	}
	
	
	@Test(expected = ValidationException.class)
	public void dnaValidationEmpty() throws IOException {
		List<String> dnaCode = Arrays.asList();
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		
		dnaService.dnaAnalize(dnaDTO);
	}
	
	@Test(expected = ValidationException.class)
	public void dnaValidationColumnSize() throws IOException {
		List<String> dnaCode = Arrays.asList("CTGATA", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCGTA", 
											 "TCACTG",
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		
		dnaService.dnaAnalize(dnaDTO);
	}
	
	@Test(expected = ValidationException.class)
	public void dnaValidationLineSize() throws IOException {
		List<String> dnaCode = Arrays.asList("CTGATAT", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCGTA", 
											 "TCACTG");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		
		dnaService.dnaAnalize(dnaDTO);
	}
	
	
	@Test(expected = ValidationException.class)
	public void dnaValidationChar() throws IOException {
		List<String> dnaCode = Arrays.asList("CTGATA", 
											 "CTATGC", 
											 "TATTGT", 
											 "AGAGGG", 
											 "CCCGTA", 
											 "TCACTX");
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		
		dnaService.dnaAnalize(dnaDTO);
	}
	
	@Test(expected = ValidationException.class)
	public void dnaSaveValidation()  {
		dnaService.save(new Dna());
	}


}
