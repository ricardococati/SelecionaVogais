package com.ricardococati.encontravogal.impl;

import com.ricardococati.encontravogal.Stream;
import org.springframework.util.StringUtils;

public class StreamImpl implements Stream {
	
	private final char[] characteres;
	private final String texto;
	private int index;

	public StreamImpl(String text) {
		if (StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException("Texto não pode ser nulo.");
		}
		this.texto = text;
		this.characteres = text.toCharArray();
	}

	@Override
	public char getNext() {
		return characteres[index++];
	}

	@Override
	public boolean hasNext() {
		return this.index < characteres.length;
	}

	public String getTexto() {
		return texto;
	}

}
