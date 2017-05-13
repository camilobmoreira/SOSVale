package hello;



import static spark.Spark.get;
import static spark.Spark.post;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import spark.Request;
import spark.Response;
import spark.Route;


public class REST{
	
	private Model model;
	
	public REST(Model store){
		this.model = store;
	}
	
	
	// User rotes
	public void addUser() {
		post("/addUser", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String fullName = json.getString("fullname");
	        	String username = json.getString("username");
	        	String password = json.getString("password");
	        	String cpf = json.getString("cpf");
	        	String email = json.getString("email");
    			
	        	try {
		        	User user = new User();
		        	user.setFullName(fullName);
		        	user.setUsername(username);
		        	user.setPassword(password);
	        		user.setCpf(cpf);
	        		user.setEmail(email);
		        	user.setAccountType((byte)1);
	        	
	        	
	            	model.addUser(user);
        		} catch (RuntimeException e) {
        			return new JSONObject().put("mensagem", e.getMessage());
        		} 
	        	return new JSONObject().put("mensagem", "Cadastrado com sucesso!");
	         }
	      });	
	}
	
	public void changeEmail() {
		post("/change/email", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String newEmail = json.getString("newEmail");
	        	String password = json.getString("password");
	        	
	        	// FIXME
	        	User user = new User(); //pegar user da sessao html 
	        	
	        	if (!user.getPassword().equals(password)) {
	        		return new JSONObject().put("mensagem", "Senha inválida.");
	        	}
	        	
	        	model.changeEmail(user, newEmail, password);
	        	
	        	return new JSONObject().put("mensagem", "Email alterado com sucesso.");
	         }
	      });	
	}
	
	public void changePassword() {
		post("/change/email", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String password = json.getString("password");
	        	String password2 = json.getString("password2");
	        	String newPassword = json.getString("newPassword");
	        	
	        	// FIXME
	        	User user = new User(); //pegar user da sessao html 
	        	
	        	if (!password.equals(password2)) {
	        		return new JSONObject().put("mensagem", "Senhas não conferem.");
	        	} 
	        	try {
	        		model.changePassword(user, password, newPassword);
	        	} catch (RuntimeException e) {
	        		return new JSONObject().put("mensagem", e.getMessage());
				}
	        	
	        	return new JSONObject().put("mensagem", "Senha alterada com sucesso.");
	         }
	      });	
	}
	
	public void loginCpf() {
		post("/login/username", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String cpf = json.getString("username");
	        	String password = json.getString("password");
	        	
	            User user = model.loginUsername(cpf, password);
	            
	            if(user == null){
	         	    return new JSONObject().put("mensagem", "CPF e/ou senha inválidos.");
            	}
				return user;              	
	         }
	      });	
	}

	public void loginEmail() {
		post("/login/email", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String email = json.getString("username");
	        	String password = json.getString("password");
	        	
	        	User user = model.loginUsername(email, password);
	            
	        	if(user == null){
	         	    return new JSONObject().put("mensagem", "Email e/ou senha inválidos.");
            	}
				return user;
	         }
	      });	
	}
	
	public void loginUserName() {
		post("/login/username", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String username = json.getString("username");
	        	String password = json.getString("password");
	        	
	        	User user = model.loginUsername(username, password);
	        	
	        	if(user == null){
	         	    return new JSONObject().put("mensagem", "Nome de usuário e/ou senha inválidos.");
            	}
				return user; 
	         }
	      });	
	}
	
	public void removeUser() {
		post("/remove/user", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String username = json.getString("username");
	        	
	        	try {
	        		model.removeUser(username);
	        	} catch(RuntimeException e){
	        		return new JSONObject().put("mensagem", e.getMessage()); 
	        	}
	        	
        		return new JSONObject().put("mensagem", "Usuário deletado com sucesso.");
	         }
	      });	
	}

	
	
	// Post rotes
	public void addPost() {
		post("/ceatePost", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String postTitle = json.getString("title");
	        	String postDescription = json.getString("postDescription");
	        	String username = json.getString("username");
	        	String postType = json.getString("postType");
	        	
	        	double latitude = json.getDouble("latitude");
	        	double longitude = json.getDouble("longitude");
	        	Location location = new Location(latitude, longitude);
	        	
	        	String imgDescription = json.getString("imgDescription");
	        	String imgUrl = json.getString("imgUrl");
	        	String imgIcon = json.getString("imgIcon");
	        	Image image = new Image(imgDescription, imgUrl, imgIcon);      
	        	
	        	Post post = new Post();
	        	post.setTitle(postTitle);
	        	post.setDescription(postDescription);
	        	post.setUsername(username);
	        	post.setLocation(location);
	        	post.setImage(image);
	        	post.setPostType(postType);
	        	
	        	model.addPost(post);
        		
             	return new JSONObject().put("mensagem", "Post criado com sucesso!");  
	         }
	      });	
	}
		
	public void searchApprovedPost() {
		get("/search/post/ApprovedPosts", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	            try {
	            	LinkedList<Post> posts = model.searchApprovedPost();
	            	
	            	if(posts != null){
	            		JSONArray jsonResult = new JSONArray();
	            		
	            		for (Post p : posts) {
		            		
			         	    JSONObject jsonObj = new JSONObject();

			         	    jsonObj.put("aprovado ", p.isApproved());
			         	    jsonObj.put("titulo ", p.getTitle());
			         	    jsonObj.put("descricao ", p.getDescription());
			         	    jsonObj.put("imagem ", p.getImage());
			         	    jsonObj.put("nome usuario ", p.getUsername());
			         	    jsonObj.put("Categoria ", p.getPostType());
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	            return new JSONObject().put("mensagem", "Nenhum post encontrado.");
	         }
	      });	
	}
	
	public void searchNonApprovedPost() {
		get("/search/post/NonApprovedPosts", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	            try {
	            	LinkedList<Post> posts = model.searchNonApprovedPost();
	            	
	            	if(posts != null){
	            		JSONArray jsonResult = new JSONArray();
	            		
	            		for (Post p : posts) {
		            		
			         	    JSONObject jsonObj = new JSONObject();

			         	    jsonObj.put("aprovado ", p.isApproved());
			         	    jsonObj.put("titulo ", p.getTitle());
			         	    jsonObj.put("descricao ", p.getDescription());
			         	    jsonObj.put("imagem ", p.getImage());
			         	    jsonObj.put("nome usuario ", p.getUsername());
			         	    jsonObj.put("Categoria ", p.getPostType());
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	            return new JSONObject().put("mensagem", "Nenhum post encontrado.");
	         }
	      });
	}

	public void searchPostsByType() {
		get("/search/post/byType", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	            try {
	            	LinkedList<Post> posts = model.searchPostsByType(request.params(":postType"));
	            	
	            	if(posts != null){
	            		JSONArray jsonResult = new JSONArray();
	            		
	            		for (Post p : posts) {
		            		
			         	    JSONObject jsonObj = new JSONObject();

			         	    jsonObj.put("aprovado ", p.isApproved());
			         	    jsonObj.put("titulo ", p.getTitle());
			         	    jsonObj.put("descricao ", p.getDescription());
			         	    jsonObj.put("imagem ", p.getImage());
			         	    jsonObj.put("nome usuario ", p.getUsername());
			         	    jsonObj.put("Categoria ", p.getPostType());
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	            return new JSONObject().put("mensagem", "Nenhum post encontrado.");
	         }
	      });
	}
	
	public void approvePost() {
		post("/approve/post", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	// FIXME 
	        	Post post = new Post(); //PEGAR POST DA VIEW
	        	
	        	model.approvePost(post);
	        	
        		return new JSONObject().put("mensagem", "Post aprovado com sucesso.");
	         }
	      });	
	}
}
