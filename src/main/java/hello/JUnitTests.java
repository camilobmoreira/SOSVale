package hello;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * STORY CARDS
 * 01 - Matches da classe conta
 * 02 - Cadastrar usuario
 * 03 - Logar
 * 04 - Editar usuario
 * 05 - Remover usuario 
 * 06 - Cadastrar post
 * 07 - Buscar post
 * 08 - Editar post
 * 09 - Aprovar post
 * 10 - Validar CPF
 * 11 - Add Admin
 * */

public class JUnitTests {

	@Test
	public void test() {
		
		//Criando model
		Model model = new Model();
		
		//Criando nova conta
		Conta conta = new Conta("majo", "maria_joaquina@gmail.com", "123456", 'u');
		
		//01 - Matches da classe Conta
		assertEquals(conta.matches(conta.getNomeUsuario(), conta.getSenha()), true);
		assertEquals(conta.matches("jfran", "654321"), false);
		
		//02 - Criando e cadastrando usuario
		Cpf cpf = new Cpf("44247355830");
		Usuario usuario = new Usuario("Maria Joaquina", cpf, conta);
		model.cadastrarUsuario(usuario);
		assertEquals(model.getUsuarios().size(), 1);
		
		
		Usuario u = model.loginUsuario("majo", "123456");
		
		//03 - Logar
		assertEquals(u.getCpf().getCpf(), "44247355830");
		/*
		//04 - Editar usuario
		model.editarUsuario(conta, "maria_joaquina@gmail.com", "senha");
		assertEquals(conta.getEmail(), "maria_joaquina@gmail.com");
		
		//05 - Remover usuario
		model.removerUsuario(conta);
		assertEquals(model.getUsuarios().size(), 0);
		*/
		
		//Criando nova localizacao
		Localizacao localizacao = new Localizacao(47.09, 130.70);
		
		//Criando nova imagem
		Imagem imagem = new Imagem("Erupção vulcânica", "imagem", "vulcão");
				
		//06 - Criando novo post
		Post post = new Post("Erupção no vulcão Xinxango", "Muita lava e chuva de meteoro", localizacao, imagem, "majo", false);
		model.criarPost(post);
		assertEquals(post.getNomeUsuario(), "majo");
		
		//07 - Buscar post
		assertEquals(post.getTitulo(), model.buscarPost("majo", "Erupção no vulcão Xinxango").getTitulo());
		
		/*
		//08 - Editar post
		Post post1 = new Post("Erupção no vulcão Xinxango", "Muita lava e chuva de pedra", localizacao, imagem, "majo", false);
		model.editarPost(post, post1);
		assertEquals(post1.getDescricao(), model.buscarPost("majo", "Erupção no vulcão Xinxango").getDescricao());
		
		//09 - Aprovar post
		model.aprovarPost(model.buscarPost("majo", "Erupção no vulcão Xinxango"));
		assertEquals(true, model.buscarPost("majo", "Erupção no vulcão Xinxango").isAprovado());
		*/
		//10 - Validar CPF
		assertEquals(true, cpf.validarCpf("44247355830"));
		
		//Criando nova conta Admin 
		Conta contaAdmin = new Conta("root", "root@root.com", "pass", 'a');
				
		//11 - Criando e cadastrando Admin
		Cpf cpf1 = new Cpf("33023667870");
		Admin admin = new Admin("admin", cpf1 , contaAdmin);
		model.addAdmin(admin);
		assertEquals(model.getAdmins().size(), 1);
		
	}

}
