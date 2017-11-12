package com.ricardococati.exception;

public class VogalNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 3633616238547048957L;

	public VogalNaoEncontradaException() {
		super("Não foi encontrada nenhuma ocorrência de vogal na String!!");
	}
}
