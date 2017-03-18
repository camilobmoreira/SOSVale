package hello;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import java.util.LinkedList;

public class Model {
	
	ObjectContainer admins = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/admins.db4o");
	ObjectContainer users = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/users.db4o");
	ObjectContainer posts = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/posts.db4o");
	
	public boolean addAdmin (Admin admin)
	{
		Query query = admins.query();
		query.constrain(Admin.class);
		ObjectSet<Admin> allAdmins = query.execute();
		
		for (Admin a: allAdmins) 
		{
			if (a.getConta().getNomeUsuario().equals(admin.getConta().getNomeUsuario())) 
			{
				return false;
			}
		}
		admins.store(admin);
		return true;
	}
	
	public List<Admin> getAdmins()
	{
		Query query = admins.query();
		query.constrain(Admin.class);
		ObjectSet<Admin> allAdmins = query.execute();
		
		return allAdmins;
	}
	
	// FIXME 
	// Permitir logar por nome de usuario/email/cpf e senh
	public Admin logarAdmin(String nomeUsuario, String senha ) 
	{
		Query query = admins.query();
		query.constrain(Admin.class);
		ObjectSet<Admin> allAdmins = query.execute();
		
		for (Admin admin: allAdmins) 
		{
			if(admin.getConta().matches(nomeUsuario, senha ) ) 
			{
				return admin;
			}
		}
		return null;
	}
	
	public boolean cadastrarUsuario(Usuario usuario)
	{
		Query query = users.query();
		query.constrain(Usuario.class);
		ObjectSet<Usuario> allUsers = query.execute();
		
		for (Usuario u: allUsers) 
		{
			if (u.getConta().getNomeUsuario().equals(usuario.getConta().getNomeUsuario())) 
			{
				return false;
			}
		}
		users.store(usuario);
		return true;
	}
	
	public List<Usuario> getUsuarios()
	{
		Query query = users.query();
		query.constrain(Usuario.class);
		ObjectSet<Usuario> allUsers = query.execute();
		
		return allUsers;
	}
	
	// FIXME 
	// Permitir logar por nome de usuario/email/cpf e senha
	public Usuario logar(String nomeUsuario, String senha ) 
	{
		Query query = users.query();
		query.constrain(Usuario.class);
		ObjectSet<Usuario> allUsers = query.execute();
		
		for (Usuario usuario: allUsers) 
		{
			if (usuario.getConta().matches(nomeUsuario, senha)) 
			{
				return usuario;
			}
		}
		return null;
	}
	
	// FIXME 
	// alterar este metodo APENAS para editar email e criar novo APENAS para alterar senha
	public void editarUsuario(Conta conta, String email, String senha)
	{
		Query query = users.query();
		query.constrain(Usuario.class);
		ObjectSet<Usuario> allUsers = query.execute();
		
		for (Usuario usuario: allUsers)
		{
			if (conta.matches(usuario.getConta()))
			{
				conta.setEmail(email);
				conta.setSenha(senha);
			}
		}
	}

	// FIXME
	public void removerUsuario(Conta conta)
	{
		for (Usuario usuario: usuarios)
		{
			if (conta.matches(usuario.getConta())) 
			{
				usuarios.remove(usuario);
			}
		}
	}
	
	// FIXME
	// Verificar condiçoes unicas de post (nome de titulo unico ou algo assim)
	public void criarPost(Post post)
	{	
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute();
		
		posts.store(post);
	}
	
	// FIXME 
	// Verificar se não vai retornar todos os posts existentes ao inves de apenas os do próprio usuario
	public List<Post> getPosts()
	{
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute();
		
		return allPosts;
	}
	
	// FIXME 
	// Verificar metodo melhor pra buscar post
	public Post buscarPost(String nomeUsuario, String titulo)
	{
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute(); 
		
		for (Post post: allPosts) 
		{
			if(post.getNomeUsuario().equals(nomeUsuario) && post.getTitulo().equals(titulo)) 
			{
				return post;
			}
		}
			
		return null;
	}

	/* Criar metodo para buscar por localização aproximada / nome local
	public Post buscarPost(Localizacao localizacao)
	{
		for (Post post: posts) if()
		return null;
	}*/
	
	// FIXME
	// Verificar se metodo vai funcionar ainda com a view
	public void editarPost(Post postAntigo, Post novoPost)
	{
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute();
		
		for (Post post: allPosts) 
			{
				if(post.getNomeUsuario().equals(postAntigo.getNomeUsuario()) && post.getDescricao().equals(postAntigo.getDescricao())) 
					{
						post.setTitulo(novoPost.getTitulo());
						post.setDescricao(novoPost.getDescricao());
						post.setImagem(novoPost.getImagem());
						post.setLocalizacao(novoPost.getLocalizacao());
					}
			}
	}
	
	public void aprovarPost(Post post)
	{
		post.setAprovado(true);
	}
}
