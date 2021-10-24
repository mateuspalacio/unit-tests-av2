package br.com.aula;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import br.com.aula.exception.*;
import org.junit.Assert;
import org.junit.Test;

public class BancoTest {

	@Test
	public void deveCadastrarConta() throws ContaJaExistenteException, ContaInvalidaException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = null;
			conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta);

		// Verificação
		assertEquals(1, banco.obterContas().size());
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() throws ContaJaExistenteException, ContaInvalidaException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = null;
			conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = null;
			conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test(expected = ContaJaExistenteException.class) // criado por mim
	public void naoDeveCadastrarContaNumeroExistente() throws ContaJaExistenteException, ContaInvalidaException {

		// Cenario
		Cliente cliente = new Cliente("Mateus");
		Conta conta1 = null;
		conta1 = new Conta(cliente, 134, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Samantha");
		Conta conta2 = null;
		conta2 = new Conta(cliente2, 134, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test
	public void naoDeveCadastrarContaInvalida() throws ContaJaExistenteException, ContaInvalidaException {
		// Arrange
		Cliente cliente = new Cliente("Mateus");
		Conta conta = new Conta(cliente, 11111, 1, TipoConta.CORRENTE);

		Banco banco = new Banco();
		// Act
		banco.cadastrarConta(conta);

		// Assert
		Assert.fail();

	}

	@Test(expected = ContaJaExistenteException.class) // criado por mim
	public void naoDeveCadastrarContaClienteExistente() throws ContaJaExistenteException, ContaInvalidaException {

		// Cenario
		Cliente cliente = new Cliente("Mateus");
		Conta conta1 = null;
		conta1 = new Conta(cliente, 134, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Mateus");
		Conta conta2 = null;
		conta2 = new Conta(cliente2, 135, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test
	public void deveEfetuarTransferenciaContasCorrentes() throws ContaSemSaldoException, ContaNaoExistenteException, ContaInvalidaException, TransferenciaNegativaException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = null;
		contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = null;
		contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}
}
