package hello;

import java.util.LinkedList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Model {
	
	ObjectContainer users = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/users.db4o");
	ObjectContainer posts = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/posts.db4o");

	
	//User methods
	public void addUser(User user) {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		for (User u: allUsers) {
			if (u.getCpf().equalsIgnoreCase(user.getCpf())) {
				throw(new RuntimeException("Cpf já cadastrado"));
			} else if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
				throw(new RuntimeException("Nome de usuário já cadastrado"));
			} else if (u.getEmail().equalsIgnoreCase(user.getEmail())) {
				throw(new RuntimeException("Email já cadastrado"));
			}
		}
		this.users.store(user);
		this.users.commit();
	}
	
	public List<User> getUsers() {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		return allUsers;
	}
	
	public void changeEmail(User user, String newEmail, String password) {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		/*for (User u: allUsers) {
			if (u.matchesCpf(user.getCpf(),
					user.getPassword())) {
				
				u.setEmail(newEmail);
				u.setPassword(password);
				this.users.store(u);
				this.users.commit();
			}
		}*/
		
		user.setEmail(newEmail);
		allUsers.set(allUsers.indexOf(user), user);
	}
	
	public void changePassword(User user, String password, String newPassword) throws RuntimeException {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		/*for (User u: allUsers) {
			if (u.matchesCpf(user.getCpf(),
					user.getPassword())) {
				
				u.setPassword(newPassword);
				this.users.store(u);
				this.users.commit();
			}
		}*/
		
		user.setPassword(newPassword);
		allUsers.set(allUsers.indexOf(user), user);
	}
	
	public User loginCpf(String cpf, String senha ) {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		for (User user: allUsers) {
			if (user.matchesCpf(cpf, senha)) {
				return user;
			}
		}
		return null;
	}
	
	public User loginEmail(String email, String senha ) {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		for (User user: allUsers) {
			if (user.matchesEmail(email, senha)) {
				return user;
			}
		}
		return null;
	}
	
	public User loginUsername(String nomeUsuario, String senha ) {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		for (User user: allUsers) {
			if (user.matchesUserName(nomeUsuario, senha)) {
				return user;
			}
		}
		return null;
	}
	
	public void removeUser(String username) {
		Query query = users.query();
		query.constrain(User.class);
		ObjectSet<User> allUsers = query.execute();
		
		for (User u: allUsers) {
			if (u.getUsername().equalsIgnoreCase(username)){
				this.users.delete(u);
				this.users.commit();
			}
		}
		throw(new RuntimeException("Usuário não localizado"));
	}
	
	
	//Post methods
	public void addPost(Post post) {	
		/*
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute();
		*/
		
		this.posts.store(post);
		this.posts.commit();
	}

	// FIXME
	public void approvePost(Post post) {
		post.setApproved(true);
		this.posts.store(post);
		this.posts.commit();
	}
	
	// FIXME
	public void editPost(Post postAntigo, Post novoPost) {
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute();
		
		for (Post post: allPosts) {
			if(post.getUsername().equalsIgnoreCase(postAntigo.getUsername()) 
					&& post.getDescription().equals(postAntigo.getDescription())) {
				
				post.setTitle(novoPost.getTitle());
				post.setDescription(novoPost.getDescription());
				post.setImage(novoPost.getImage());
				post.setLocation(novoPost.getLocation());
				this.posts.store(post);
				this.posts.commit();
			}
		}
	}
	
	public LinkedList<Post> searchApprovedPost() {
		
		LinkedList<Post> allApprovedPosts = new LinkedList<>();
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute(); 
		
		for (Post post: allPosts) {
			if(post.isApproved()) {
					allApprovedPosts.add(post);
			}
		}
		return allApprovedPosts;
	}
	
	public LinkedList<Post> searchNonApprovedPost() {
		
		LinkedList<Post> allNonApprovedPosts = new LinkedList<>();
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute(); 
		
		for (Post post: allPosts) {
			if(!post.isApproved()) {
					allNonApprovedPosts.add(post);
			}
		}
		return allNonApprovedPosts;
	}

	public LinkedList<Post> searchPostsByType (String postType) {
		
		LinkedList<Post> allPostsByType = new LinkedList<>();
		Query query = posts.query();
		query.constrain(Post.class);
		ObjectSet<Post> allPosts = query.execute(); 
		
		for (Post post: allPosts) {
			if(post.isApproved()) {
				if(post.getPostType().equalsIgnoreCase(postType)) {
					allPostsByType.add(post);
				}
			}
		}
		return allPostsByType;
	}
	
	// PARA APP ANDROID
	/* Criar metodo para buscar por localização aproximada / nome local
	public Post buscarPost(Location localizacao)
	{
		for (Post post: posts) if()
		return null;
	}*/
	
	
}
