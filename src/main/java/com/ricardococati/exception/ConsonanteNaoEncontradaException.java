package com.ricardococati.exception;

public class ConsonanteNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1895153665332534530L;

	public ConsonanteNaoEncontradaException(){
		super("Consonante não encontrada na string");
	}
}
