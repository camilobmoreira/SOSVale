package hello;

public class Post {
	
	private String titulo;
	private String descricao;
	private Localizacao localizacao;
	private Imagem imagem;
	private String nomeUsuario;
	private boolean aprovado;
	
	public Post (String titulo, String descricao, Localizacao localizacao, Imagem imagem, String nomeUsuario, boolean aprovado)
	{
		this.titulo = titulo;
		this.descricao = descricao;
		this.localizacao = localizacao;
		this.imagem = imagem;
		this.nomeUsuario = nomeUsuario;
		this.aprovado = aprovado;
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}

	public Imagem getImagem() {
		return imagem;
	}

	public void setImagem(Imagem imagem) {
		this.imagem = imagem;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public boolean isAprovado() {
		return aprovado;
	}

	public void setAprovado(boolean aprovado) {
		this.aprovado = aprovado;
	}
	
	
}
