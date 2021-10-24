package br.com.aula;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import br.com.aula.exception.*;
import org.junit.Assert;
import org.junit.Test;

public class ContaTest {

	@Test
	public void deveCreditar() throws ContaInvalidaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 10, TipoConta.CORRENTE);

		// Ação
		c.creditar(5);

		// Verificação
		assertEquals(15, c.getSaldo());
		assertThat(c.getSaldo(), is(15));
	}

	@Test
	public void deveTransferir() throws ContaInvalidaException, ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 11, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta c2 = new Conta(cliente2, 124, 5, TipoConta.POUPANCA);
		Banco b = new Banco();

		// Ação

		b.cadastrarConta(c);
		b.cadastrarConta(c2);
		b.efetuarTransferencia(123, 124, 3);

		// Verificação
		assertEquals(8, c.getSaldo());
		assertEquals(8, c2.getSaldo());
	}

	@Test
	public void deveVerificarSeExisteContaOrigem() throws ContaInvalidaException, ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 11, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta c2 = new Conta(cliente2, 124, 5, TipoConta.POUPANCA);
		Banco b = new Banco();

		// Ação
		b.cadastrarConta(c);
		b.cadastrarConta(c2);
		if(b.obterContaPorNumero(123) != null)
			b.efetuarTransferencia(123, 124, 3);

		// Verificação
		assertNotNull(b.obterContaPorNumero(123));
		assertEquals(8, c.getSaldo());
		assertEquals(8, c2.getSaldo());
	}

	@Test(expected = ContaSemSaldoException.class)
	public void deveTerPoupancaPositiva() throws ContaInvalidaException, ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 11, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta c2 = new Conta(cliente2, 124, 0, TipoConta.POUPANCA);
		Banco b = new Banco();

		// Ação
		b.cadastrarConta(c);
		b.cadastrarConta(c2);
		b.efetuarTransferencia(124, 123, 1);

		// Verificação
		Assert.fail();
	}

	@Test()
	public void deveVerificarExistencia() throws ContaInvalidaException, ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 11, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta c2 = new Conta(cliente2, 124, 17, TipoConta.POUPANCA);
		Banco b = new Banco();

		// Ação
		b.cadastrarConta(c);
		b.cadastrarConta(c2);
		if(b.obterContaPorNumero(123) != null)
			b.efetuarTransferencia(124, 123, 3);

		// Verificação
		assertNotNull(b.obterContaPorNumero(123));
		assertEquals(14, c.getSaldo());
		assertEquals(14, c2.getSaldo());
	}

	@Test(expected = ContaNaoExistenteException.class)
	public void deveVerificarNaoExistencia() throws ContaInvalidaException, ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 125, 11, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta c2 = new Conta(cliente2, 124, 17, TipoConta.POUPANCA);
		Banco b = new Banco();

		// Ação
		b.cadastrarConta(c);
		b.cadastrarConta(c2);
		b.obterContaPorNumero(123);
		b.efetuarTransferencia(124, 123, 3);

		// Verificação
		Assert.fail();
	}

	@Test(expected = TransferenciaNegativaException.class)
	public void deveVerificarTransferenciaPositiva() throws ContaInvalidaException, ContaSemSaldoException, ContaNaoExistenteException, ContaJaExistenteException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 11, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta c2 = new Conta(cliente2, 124, 17, TipoConta.POUPANCA);
		Banco b = new Banco();

		// Ação
		b.cadastrarConta(c);
		b.cadastrarConta(c2);
		b.efetuarTransferencia(124, 123, -1);

		// Verificação
		Assert.fail();
	}
}
