package br.com.ml.dnaanalyser.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ml.dnaanalyser.dto.StatsDTO;
import br.com.ml.dnaanalyser.model.Species;
import br.com.ml.dnaanalyser.repository.DnaRepository;

@Service
public class StatsService {
	
	@Autowired
	private DnaRepository dnaRepository;
	
	public StatsDTO generateStats() {
		long totalSimian = dnaRepository.countBySpecies(Species.SIMIAN);
		long totalHuman = dnaRepository.countBySpecies(Species.HUMAN);
		return new StatsDTO(totalSimian, totalHuman);
	}
	

}
