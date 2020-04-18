package br.com.ml.dnaanalyser.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

public class DnaDTO {
	
	@NotEmpty(message = "The field cannot be empty")
	private List<String> dna;

	public List<String> getDna() {
		return dna;
	}

	public void setDna(List<String> dna) {
		this.dna = dna;
	}
	
	public String[] toArray() {
		return this.dna.toArray(new String[dna.size()]);
	}

}
