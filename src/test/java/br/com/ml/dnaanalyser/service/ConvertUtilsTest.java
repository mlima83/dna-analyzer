package br.com.ml.dnaanalyser.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.ml.dnaanalyser.dto.DnaDTO;
import br.com.ml.dnaanalyser.model.Dna;
import br.com.ml.dnaanalyser.utils.ConvertUtils;

public class ConvertUtilsTest {
	
	@Spy
	private ConvertUtils convertUtils = new ConvertUtils();
	
	private List<String> dnaCode;
	
	private byte[] dnaByteCode;
	
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.dnaCode = Arrays.asList("CTGGTA", "CTATGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG");
        this.dnaByteCode = new byte[] {0, 6, 67, 84, 71, 71, 84, 65, 0, 6, 67, 84, 65, 84, 71, 67, 0, 6, 84, 65, 84, 84, 71, 84, 0, 6, 65, 71, 65, 71, 71, 71, 0, 6, 67, 67, 67, 67, 84, 65, 0, 6, 84, 67, 65, 67, 84, 71};
    }
	
	@Test
	public void convertToModelTest() throws IOException {
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		Dna dnaResult = convertUtils.dtoToModel(dnaDTO);
		
		assertNotNull(dnaResult);
		assertNotNull(dnaResult.getId());
		assertNotNull(dnaResult.getDna());
		assertNull(dnaResult.getSpecies());
	}
	
	@Test
	public void convertToDtoTest() throws IOException {
		Dna dna = new Dna();
		dna.setDna(convertUtils.listToByteArray(dnaCode));
		
		DnaDTO dnaDTO = convertUtils.modelToDto(dna);
		
		assertNotNull(dnaDTO);
		assertNotNull(dnaDTO.getDna());
		assertTrue(dnaCode.containsAll(dnaDTO.getDna()));
	}
	
	@Test
	public void hashIdTest() throws IOException {
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(dnaCode);
		Long dnaId = convertUtils.generateHashDna(dnaDTO);
		
		assertNotNull(dnaId);
	}	
	
	@Test
	public void convertDnaByteArrayTest() throws IOException {
		byte[] dnaConverted = convertUtils.listToByteArray(dnaCode);
		assertNotNull(dnaConverted);
		assertTrue(dnaConverted.length > 0);
		assertArrayEquals(this.dnaByteCode, dnaConverted);
	}	
	
	@Test
	public void convertDnaTest() throws IOException {
		List<String> dnaConverted = convertUtils.byteArrayToList(dnaByteCode);
		assertNotNull(dnaConverted);
		assertTrue(dnaConverted.size() > 0);
		assertTrue(dnaConverted.containsAll(dnaCode));
	}	

}
