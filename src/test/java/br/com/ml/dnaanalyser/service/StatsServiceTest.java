package br.com.ml.dnaanalyser.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.ml.dnaanalyser.dto.StatsDTO;
import br.com.ml.dnaanalyser.model.Species;
import br.com.ml.dnaanalyser.repository.DnaRepository;

public class StatsServiceTest {
	
	@Mock
	private DnaRepository dnaRepository;
	
	@InjectMocks
	private StatsService statsService;
	
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getStatsWithRatioTest() {
		when(dnaRepository.countBySpecies(Species.SIMIAN)).thenReturn(40L);
		when(dnaRepository.countBySpecies(Species.HUMAN)).thenReturn(100L);
		
		StatsDTO statsDTO = statsService.generateStats();
		
		assertEquals(new BigDecimal(0.4).setScale(1, RoundingMode.HALF_EVEN), statsDTO.getRatio());
	}
	
	
	@Test
	public void getStatsWithoutHumanTest() {
		when(dnaRepository.countBySpecies(Species.SIMIAN)).thenReturn(40L);
		when(dnaRepository.countBySpecies(Species.HUMAN)).thenReturn(0L);
		
		StatsDTO statsDTO = statsService.generateStats();
		
		assertEquals(BigDecimal.ZERO, statsDTO.getRatio());
	}
	
	
	@Test
	public void getStatsWithoutSimianTest() {
		when(dnaRepository.countBySpecies(Species.SIMIAN)).thenReturn(0L);
		when(dnaRepository.countBySpecies(Species.HUMAN)).thenReturn(100L);
		
		StatsDTO statsDTO = statsService.generateStats();
		
		assertEquals(BigDecimal.ZERO, statsDTO.getRatio());
	}

}
