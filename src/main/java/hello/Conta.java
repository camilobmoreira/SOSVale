package hello;

public class Conta {

	private String nomeUsuario;
	private String email;
	private String senha;
	private char tipoConta;
	
	public Conta(String nomeUsuario, String email, String senha, char tipoConta) {
				
		this.nomeUsuario = nomeUsuario;
		this.email = email;
		this.senha = senha;
		this.tipoConta = tipoConta;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getNomeUsuario()
	{
		return nomeUsuario;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public char getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(char tipoConta) {
		this.tipoConta = tipoConta;
	}

	//public boolean matches(Conta conta)
	public boolean matches(String nomeUsuario, String senha)
	{
		//if (!nomeUsuario.equals(conta.nomeUsuario)) return false;
		if (this.nomeUsuario.equalsIgnoreCase(nomeUsuario) && this.senha.equals(senha)) return true;
		return false;
	}
}
