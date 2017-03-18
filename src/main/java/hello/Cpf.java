package hello;

public class Cpf {
	
	private String cpf;
	
	public Cpf (String cpf)
	{
		this.cpf = cpf;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		if (validarCpf(cpf)) this.cpf = cpf; 
	}
	
	public String gerarNumeroControleCPF(String cpf)
	{
		String cpf9Dig = cpf.substring(0, 9), c;
		int s = 0, decDigito = 0, decPDigito = 0;
		
		//Calcular 10º digito
		for (int x = 1; x < 10; x++) 
			{
				c = String.valueOf(cpf9Dig.charAt(x -1));
				s += Integer.parseInt((c)) * x;
			}
		if (s % 11 != 10) decDigito = s % 11;
		cpf9Dig += String.valueOf(decDigito);
		
		//Calcular 11ª digito
		s = 0;
		for (int x = 1; x < 11; x++) 
		{
			c = String.valueOf(cpf9Dig.charAt(x - 1));
			s += Integer.parseInt((c)) * x;
		}
		if (s % 11 != 10) decPDigito = s % 11;
		cpf9Dig += String.valueOf(decPDigito);		
		
		return cpf9Dig;
	}
	
	public boolean validarCpf(String cpf)
	{
		if (cpf.length() != 11) return false;
		String cpfGerado = gerarNumeroControleCPF(cpf);
		if (String.valueOf(cpf).equals(cpfGerado)) return true;
		else return false;
	}

}
