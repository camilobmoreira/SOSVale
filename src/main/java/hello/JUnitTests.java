package hello;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * STORY CARDS
 * 01 - Cadastrar usuario
 * 02 - Logar com username
 * 03 - Matches da classe conta
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
					
		//01 - Criando e cadastrando usuario
		User user = new User();
		user.setUsername("majo");
		user.setEmail("maria_joaquina@teste.com");
		user.setPassword("123456");
		user.setFullName("Maria Joaquina");
		user.setCpf("33023667870");
		user.setAccountType((byte) 1);
		try {
			model.addUser(user);
			assertEquals(model.getUsers().size(), 1);
		} catch (RuntimeException e) {
			//System.out.println(e.getMessage());
		}
		

		//03 - Logar com username
		User u = model.loginUsername("majo", "123456");
		assertEquals(user.getUsername(), u.getUsername());
		
		//03 - Matches da classe User
		assertEquals(user.matches(user.getCpf(), user.getEmail(), user.getUsername(), user.getPassword()), true);
		assertEquals(user.matches("12345678900", "jfran@gmail.com", "jfran", "654321"), false);
		
		/*
		//04 - Editar usuario
		model.editarUsuario(conta, "maria_joaquina@gmail.com", "senha");
		assertEquals(conta.getEmail(), "maria_joaquina@gmail.com");
		
		//05 - Remover usuario
		model.removerUsuario(conta);
		assertEquals(model.getUsuarios().size(), 0);
		*/
		
		//Criando nova localizacao
		Location location = new Location(47.09, 130.70);
		
		//Criando nova imagem
		Image image = new Image("Erupção vulcânica", "imagem", "vulcão");
				
		//06 - Criando novo post
		Post post = new Post("Erupção no vulcão Xinxango", "Muita lava e chuva de meteoro", location, image, "majo", "incendio");
		model.addPost(post);
		model.approvePost(post);
		assertEquals(post.getUsername(), "majo");
		
		//07 - Buscar post
		assertEquals(post.getTitle(), model.searchApprovedPost().get(0).getTitle());
		
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
		assertEquals(true, User.cpfIsValid("44247355830"));
	}

}
