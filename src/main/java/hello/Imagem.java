package hello;

public class Imagem {
	
	private String descricao;
	private String url;
	private String icone;
	
	public Imagem(String descricao, String url, String icone) 
	{
		this.descricao = descricao;
		this.url = url;
		this.icone = icone;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}
}
