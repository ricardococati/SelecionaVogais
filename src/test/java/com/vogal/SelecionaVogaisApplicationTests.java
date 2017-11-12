package com.vogal;

import com.ricardococati.SelecionaVogaisApplication;
import com.ricardococati.encontravogal.impl.EncontraVogal;
import com.ricardococati.encontravogal.impl.StreamImpl;
import com.ricardococati.exception.ConsonanteNaoEncontradaException;
import com.ricardococati.exception.VogalNaoEncontradaException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SelecionaVogaisApplication.class)
public class SelecionaVogaisApplicationTests {
	
	@Autowired
	private EncontraVogal encontraVogal;

	@Test(expected = IllegalArgumentException.class)
	public void naoRetornarNadaNoStream() {
		this.encontraVogal.primeiroCaracter(new StreamImpl(null));
	}

	@Test
	public void naoRetornaVogalNoStreamTest() {
		char firstChar = this.encontraVogal.primeiroCaracter(new StreamImpl("aAbBABacaff"));
		Assert.assertEquals(firstChar, ' ');
	}
	
	@Test(expected = ConsonanteNaoEncontradaException.class)
	public void retornaExceptionPorNaoEncontrarCaracterConsoante() {
		char firstChar = this.encontraVogal.primeiroCaracter(new StreamImpl("eeeeeeeeeeeee"));
		Assert.assertEquals(firstChar, ' ');
	}
	
	@Test(expected = VogalNaoEncontradaException.class)
	public void retornaExceptionPorNaoEncontrarVogalTest() {
		char firstChar = this.encontraVogal.primeiroCaracter(new StreamImpl("zzzzzzzzzzzzzzzzYYYYYYYYYXXXX"));
		Assert.assertEquals(firstChar, ' ');
	}
	
	@Test
	public void retornaVogalENoStreamTest() {
		char firstChar = this.encontraVogal.primeiroCaracter(new StreamImpl("aAbBABacafe"));
		Assert.assertEquals(firstChar, 'e');
	}

}
