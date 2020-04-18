package br.com.ml.dnaanalyser.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ml.dnaanalyser.model.Dna;
import br.com.ml.dnaanalyser.model.Species;

public interface DnaRepository extends CrudRepository<Dna, Long>{
	
	public long countBySpecies(Species species);

}
