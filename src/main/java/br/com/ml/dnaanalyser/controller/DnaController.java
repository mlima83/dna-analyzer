package br.com.ml.dnaanalyser.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ml.dnaanalyser.dto.DnaDTO;
import br.com.ml.dnaanalyser.dto.StatsDTO;
import br.com.ml.dnaanalyser.model.Dna;
import br.com.ml.dnaanalyser.model.Species;
import br.com.ml.dnaanalyser.service.DnaService;
import br.com.ml.dnaanalyser.service.StatsService;

@RestController
@RequestMapping
public class DnaController {
	
	@Autowired
	private DnaService dnaService;
	
	@Autowired
	private StatsService statsService;
	
	@PostMapping("simian")
	@SuppressWarnings("rawtypes")
	public ResponseEntity analyse(@RequestBody @Valid DnaDTO dnaDTO) throws Exception {
		Dna dna = dnaService.dnaAnalize(dnaDTO);
		dnaService.save(dna);
		if (Species.SIMIAN.equals(dna.getSpecies())) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}
	
	@GetMapping("species/list")
	public List<DnaDTO> list() {
		return dnaService.listAll();
	}
	
	@GetMapping("/stats")
	public StatsDTO stats() {
		return statsService.generateStats();
	}

}
