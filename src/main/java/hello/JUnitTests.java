package hello;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

/**
 * STORY CARDS
 * 01 - Cadastrar usuario
 * 02 - Cadastrar administrador
 * 03 - Logar com username
 * 04 - Matches da classe conta
 * 05 - Editar usuario
 * 06 - Remover usuario 
 * 07 - Cadastrar post de incendio
 * 08 - Cadastrar post de alagamento
 * 09 - Cadastrar post de deslizamento
 * 10 - Buscar post
 * 11 - Editar post
 * 12 - Aprovar post
 * 13 - Validar CPF
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
		user.setPassword("12345");
		user.setFullName("Maria Joaquina");
		user.setCpf("44247355830");
		user.setAccountType((byte) 1);
		try {
			model.addUser(user);
			assertEquals(model.getUsers().size(), 1);
		} catch (RuntimeException e) {
			//System.out.println(e.getMessage());
		}
		
		//02 - Criando e cadastrando administrador
		User admin = new User();
		admin.setUsername("root");
		admin.setEmail("root");
		admin.setPassword("12345");
		admin.setFullName("Administrador 01");
		admin.setCpf("33023667870");
		admin.setAccountType((byte) 0);
		try {
			model.addUser(admin);
			assertEquals(model.getUsers().size(), 2);
		} catch (RuntimeException e) {
			//System.out.println(e.getMessage());
		}

		//03 - Logar com username
		User u = model.loginUsername("majo", "12345");
		assertEquals(user.getUsername(), u.getUsername());
		
		//04 - Matches da classe User
		assertEquals(user.matches(user.getCpf(), user.getEmail(), user.getUsername(), user.getPassword()), true);
		assertEquals(admin.matches("12345678900", "jfran@gmail.com", "jfran", "654321"), false);
		
		/*
		//05 - Editar usuario
		model.editarUsuario(conta, "maria_joaquina@gmail.com", "senha");
		assertEquals(conta.getEmail(), "maria_joaquina@gmail.com");
		
		//06 - Remover usuario
		model.removerUsuario(conta);
		assertEquals(model.getUsuarios().size(), 0);
		*/
		
		//Criando nova localizacao
		Location location = new Location(47.09, 130.70);
		
		//Criando nova imagem
		String image = "oi";
				
		//07 - Criando novo post de incendio
		Post pI = new Post();
		pI.setTitle("Incendio no Parque Tecnológico ");
		pI.setDescription("Um incendio em Área de Proteção Permanente (APP) no Parque Tecnológico de São José dos Campos, atingiu a Fatec. O ocorrido interrompeu tres dias de aulas, atingiu  8 motos e 3 carros que haviam no estacionamento. Embora não teve feridos, os estragos foram exorbitantes. ");
		pI.setLocation(location);
		pI.setImage(image);
		pI.setUsername("majo");
		pI.setPostType("incendio");
		pI.setPostingDate(new Date());
		model.addPost(pI);
		model.approvePost(pI);
		assertEquals(pI.getUsername(), "majo");
		
		//08 - Criando novo post de alagamento
		Post pA = new Post();
		pA.setTitle("Enxurrada no Cemitério Municipal de SJC");
		pA.setDescription("Chuvas fortes causou inundação do Rio Paraíba do Sul, causando transtornos em São José dos Campos. No cemiterio municipal, a força da água destruiu alguns túmulos, e a enxurrada levou fragmentos por todo o centro da cidade.");
		pA.setLocation(location);
		pA.setImage(image);
		pA.setUsername("majo");
		pA.setPostType("alagamento");
		pA.setPostingDate(new Date());
		model.addPost(pA);
		model.approvePost(pA);
		assertEquals(pA.getUsername(), "majo");
		
		//09 - Criando novo post de deslizamento
		Post pD = new Post();
		pD.setTitle("Deslizamento de terra em bairro nobre");
		pD.setDescription("Após três dias consecutivos de chuva, um loteamento irregular no bairro Urbanova (localizado na margem esquerda do Rio Paraíba do Sul) foi totalmente destruido por movimentos de massa do tipo planar. O ocorrido interditou o hospital ViValle e o Thermas do Vale, além de um intenso congestionamento próximo ao Anel Viário. ");
		pD.setLocation(location);
		pD.setImage(image);
		pD.setUsername("majo");
		pD.setPostType("deslizamento");
		pD.setPostingDate(new Date());
		model.addPost(pD);
		//model.approvePost(pD);
		assertEquals(pD.getUsername(), "majo");
		
		//10 - Buscar post
		assertEquals(pI.getTitle(), model.searchPostsByType("incendio").get(0).getTitle());
		assertEquals(pA.getTitle(), model.searchPostsByType("alagamento").get(0).getTitle());
		//assertEquals(pD.getTitle(), model.searchPostsByType("deslizamento").get(0).getTitle());
		
		/*
		//11 - Editar post
		Post post1 = new Post("Erupção no vulcão Xinxango", "Muita lava e chuva de pedra", localizacao, imagem, "majo", false);
		model.editarPost(post, post1);
		assertEquals(post1.getDescricao(), model.buscarPost("majo", "Erupção no vulcão Xinxango").getDescricao());
		
		//12 - Aprovar post
		model.aprovarPost(model.buscarPost("majo", "Erupção no vulcão Xinxango"));
		assertEquals(true, model.buscarPost("majo", "Erupção no vulcão Xinxango").isAprovado());
		*/
		//13 - Validar CPF
		assertEquals(true, User.cpfIsValid("44247355830"));
	}

}
