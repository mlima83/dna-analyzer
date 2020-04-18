package br.com.ml.dnaanalyser.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.ml.dnaanalyser.dto.DnaDTO;
import br.com.ml.dnaanalyser.model.Dna;

@Component
public class ConvertUtils {
	
	public byte[] listToByteArray(List<String> targetList) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);
		for (String element : targetList) {
			out.writeUTF(element);
		}
		return baos.toByteArray();
	}
	
	
	public List<String> byteArrayToList(byte[] targetByteArray) throws IOException{
		List<String> result = new ArrayList<String>();
		ByteArrayInputStream bais = new ByteArrayInputStream(targetByteArray);
		DataInputStream in = new DataInputStream(bais);
		while (in.available() > 0) {
			result.add(in.readUTF());
		}
		return result;
	}
	
	public Dna dtoToModel(DnaDTO dnaDTO) throws IOException {
		return dtoToModel(dnaDTO,  null);
	}	
	
	public Dna dtoToModel(DnaDTO dnaDTO, Long hashId) throws IOException {
		Dna dna = new Dna();
		dna.setId(hashId != null ? hashId : generateHashDna(dnaDTO));
		dna.setDna(listToByteArray(dnaDTO.getDna()));
		return dna;
	}
	
	public Long generateHashDna(DnaDTO dnaDTO) {
		return Long.valueOf(dnaDTO.getDna().hashCode());
	}
	
	public DnaDTO modelToDto(Dna dna) throws IOException {
		DnaDTO dnaDTO = new DnaDTO();
		dnaDTO.setDna(byteArrayToList(dna.getDna()));
		return dnaDTO;
	}


}
