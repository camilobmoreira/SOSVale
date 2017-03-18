package hello;

public class Admin {

	private String nomeCompleto;
	private Cpf cpf;
	private Conta conta;
	
	public Admin(String nomeCompleto, Cpf cpf, Conta conta)
	{
		this.nomeCompleto = nomeCompleto;
		this.cpf = cpf;
		this.conta = conta;
	}
	
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public Cpf getCpf() {
		return cpf;
	}
	public void setCpf(Cpf cpf) {
		this.cpf = cpf;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	
}
