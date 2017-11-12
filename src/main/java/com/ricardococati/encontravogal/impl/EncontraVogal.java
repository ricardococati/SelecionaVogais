package com.ricardococati.encontravogal.impl;


import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.ricardococati.exception.VogalNaoEncontradaException;
import com.ricardococati.exception.ConsonanteNaoEncontradaException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EncontraVogal {

	@Value("${regex.vogal}")
	private String regexVogal;

	public char primeiroCaracter(final StreamImpl input) {
		if (Objects.isNull(input)) {
			throw new IllegalArgumentException("Entrada não pode ser nula!!!!");
		}
		List<String> caracter = Arrays.asList(input.getTexto().split("")).stream()
				.map(String::toUpperCase).collect(Collectors.toList());

		this.verificaConsoante(caracter);
		this.verificaVogal(caracter);
		
		final Map<Character, Boolean> vogaisMap = new LinkedHashMap<>();
		final Map<Character, List<Character>> predecessores = new HashMap<>();

		char caracterAnterior = ' ';
		while (input.hasNext()) {
			final char caracterCorrente = input.getNext();

			this.verificaVogal(vogaisMap, caracterCorrente);
			this.verificaPredecessor(predecessores, caracterCorrente, caracterAnterior);

			caracterAnterior = caracterCorrente;
		}
		
		return this.encontraVogal(vogaisMap, predecessores);
	}
	
	private void verificaConsoante(List<String> chars) {
		chars.stream().filter(this::isConsonante)
		.findFirst().orElseThrow(ConsonanteNaoEncontradaException::new);
	}
	
	private void verificaVogal(List<String> chars) {
		chars.stream().filter(this::isVogal)
		.findFirst().orElseThrow(VogalNaoEncontradaException::new);
	}
	

	private final Character encontraVogal(final Map<Character, Boolean> vowelsByOccurrence,
										  final Map<Character, List<Character>> predecessors) {
		
		char emptyChar = ' ';
		
		for (Map.Entry<Character, Boolean> vowelOccurrence : vowelsByOccurrence.entrySet()) {
			if (BooleanUtils.isFalse(vowelOccurrence.getValue())) {
				continue;
			}
			for (Character vowelPredecessor : predecessors.get(vowelOccurrence.getKey())) {
				if (BooleanUtils.isFalse(this.isConsonante(String.valueOf(vowelPredecessor)))) {
					continue;
				}
				for (Character consonantPredecessor : predecessors.get(vowelPredecessor)) {
					if (BooleanUtils.isTrue(this.isVogal(String.valueOf(consonantPredecessor)))) {
						return vowelOccurrence.getKey();
					}
				}
			}
		}
		return emptyChar;
	}

	private void verificaPredecessor(final Map<Character, List<Character>> predecessores, final char caracterCorrente,
									 final char previousChar) {
		List<Character> characters = CollectionUtils.isEmpty(predecessores.get(caracterCorrente)) ? new ArrayList<>() : predecessores.get(caracterCorrente);
		predecessores.put(caracterCorrente, characters);
		
		if (previousChar != ' ') {
			characters.add(previousChar);
		}
	}

	private void verificaVogal(final Map<Character, Boolean> vogaisPorOcorrencia, final char caracterCorrente) {
		if (isVogal(String.valueOf(caracterCorrente)) && Objects.isNull(vogaisPorOcorrencia.get(caracterCorrente))) {
			vogaisPorOcorrencia.put(caracterCorrente, Boolean.TRUE);
		} else {
			vogaisPorOcorrencia.put(caracterCorrente, Boolean.FALSE);
		}
	}

	private final boolean isConsonante(final String vowelPredecessor) {
		return BooleanUtils.isFalse(isVogal(vowelPredecessor));
	}

	private final boolean isVogal(final String character) {
		return character.matches(this.regexVogal);
	}

}
