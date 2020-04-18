package br.com.ml.dnaanalyser.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatsDTO {
	
	@JsonProperty("count_mutant_dna")
	private long countMutantDna;
	
	@JsonProperty("count_human_dna")
	private long countHumanDna;
	
	private BigDecimal ratio;

	public StatsDTO(long totalSimian, long totalHuman) {
		this.countMutantDna = totalSimian;
		this.countHumanDna = totalHuman;
		if (this.countHumanDna > 0 && this.countMutantDna > 0) {
			this.ratio = BigDecimal.valueOf(totalSimian).divide(BigDecimal.valueOf(totalHuman), 1, RoundingMode.HALF_EVEN);
		} else {
			this.ratio = BigDecimal.ZERO;
		}
	}

	public long getCountMutantDna() {
		return countMutantDna;
	}

	public void setCountMutantDna(long countMutantDna) {
		this.countMutantDna = countMutantDna;
	}

	public long getCountHumanDna() {
		return countHumanDna;
	}

	public void setCountHumanDna(long countHumanDna) {
		this.countHumanDna = countHumanDna;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}

}
