package hello;



import static spark.Spark.get;
import static spark.Spark.post;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	
	// User routes
	public void addUser() {
		post("/add/user", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String fullName = json.getString("fullname");
	        	String username = json.getString("username");
	        	String password = json.getString("password");
	        	String password2 = json.getString("password2");
	        	String cpf = json.getString("cpf");
	        	String email = json.getString("email");
    			
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	
	        	if (!password.equals(password2)) {
	        		jsonObj.put("mensagem", "Senhas não conferem.");
	        		jsonObj.put("status", 0);
	        		jsonResult.put(jsonObj);
	        		return jsonResult;
	        	} 
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
        			jsonObj.put("mensagem", e.getMessage());
        			jsonObj.put("status", 0);
        			jsonResult.put(jsonObj);
        			return jsonResult;
        		} 
	        	jsonObj.put("mensagem", "Cadastrado com sucesso!");
	        	jsonObj.put("status", 1);
	        	jsonResult.put(jsonObj);
    			return jsonResult;
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
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	if (!user.getPassword().equals(password)) {
	        		jsonObj.put("mensagem", "Senha inválida.");
	        		jsonResult.put(jsonObj);
	    			return jsonResult;
	        	}
	        	
	        	model.changeEmail(user, newEmail, password);
	        	
	        	jsonObj.put("mensagem", "Email alterado com sucesso.");
	        	jsonResult.put(jsonObj);
    			return jsonResult;
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
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	if (!password.equals(password2)) {
	        		jsonObj.put("mensagem", "Senhas não conferem.");
	        		jsonResult.put(jsonObj);
	        		return jsonResult;
	        	} 
	        	try {
	        		model.changePassword(user, password, newPassword);
	        	} catch (RuntimeException e) {
	        		jsonObj.put("mensagem", e.getMessage());
	        		jsonResult.put(jsonObj);
	    			return jsonResult;
				}
	        	
	        	jsonObj.put("mensagem", "Senha alterada com sucesso.");
	        	jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });	
	}
	
	public void loginCpf() {
		post("/login/cpf", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String cpf = json.getString("username");
	        	String password = json.getString("password");
	        	
	            User user = model.loginCpf(cpf, password);
	            
	            JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	if(user == null){
	        		jsonObj.put("mensagem", "CPF e/ou senha inválidos.");
	        		jsonObj.put("status", 0); //not logged
	        		jsonResult.put(jsonObj);
	        		
	         	    return jsonResult;
            	}
	        	
    			jsonObj.put("username", user.getUsername());
	        	jsonObj.put("fullname", user.getFullName());
	        	jsonObj.put("accountType", user.getAccountType());
	        	jsonObj.put("status", 1); //logged in
	        	jsonResult.put(jsonObj);
	        	
				return jsonResult;
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
	        	
	        	User user = model.loginEmail(email, password);
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	if(user == null){
	        		jsonObj.put("mensagem", "Email e/ou senha inválidos.");
	        		jsonObj.put("status", 0); //not logged
	        		jsonResult.put(jsonObj);
	        		
	         	    return jsonResult;
            	}
	        	
    			jsonObj.put("username", user.getUsername());
	        	jsonObj.put("fullname", user.getFullName());
	        	jsonObj.put("accountType", user.getAccountType());
	        	jsonObj.put("status", 1); //logged in
	        	jsonResult.put(jsonObj);
	        	
				return jsonResult;
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
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	if(user == null){
	        		jsonObj.put("mensagem", "Nome de usuário e/ou senha inválidos.");
	        		jsonObj.put("status", 0); //not logged
	        		jsonResult.put(jsonObj);
	        		
	         	    return jsonResult;
            	}
	        	
    			jsonObj.put("username", user.getUsername());
	        	jsonObj.put("fullname", user.getFullName());
	        	jsonObj.put("accountType", user.getAccountType());
	        	jsonObj.put("status", 1); //logged in
	        	jsonResult.put(jsonObj);
	        	
				return jsonResult;	        	 
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
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	        	try {
	        		model.removeUser(username);
	        	} catch(RuntimeException e){
	        		jsonObj.put("mensagem", e.getMessage()); 
	        		jsonResult.put(jsonObj);
	    			return jsonResult;
	        	}
	        	
        		jsonObj.put("mensagem", "Usuário deletado com sucesso.");
        		jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });	
	}

	
	
	// Post routes
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
	        	post.setPostingDate(new Date()); 
	        	
	        	model.addPost(post);
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
             	jsonObj.put("mensagem", "Post criado com sucesso!");  
             	jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });	
	}
		
	public void searchApprovedPost() {
		get("/search/post/ApprovedPosts", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	            try {
	            	LinkedList<Post> posts = model.searchApprovedPost();
	            	
	            	if(posts != null){
	            		for (Post p : posts) {
			         	    jsonObj.put("aprovado ", p.isApproved());
			         	    jsonObj.put("titulo ", p.getTitle());
			         	    jsonObj.put("descricao ", p.getDescription());
			         	    jsonObj.put("imagem ", p.getImage());
			         	    jsonObj.put("nome usuario ", p.getUsername());
			         	    jsonObj.put("categoria ", p.getPostType());
			         	    jsonObj.put("data", 
			         	    		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
			         	    			.format(p.getPostingDate()));
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	            jsonObj.put("mensagem", "Nenhum post encontrado.");
	            jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });	
	}
	
	public void searchNonApprovedPost() {
		get("/search/post/NonApprovedPosts", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	            try {
	            	LinkedList<Post> posts = model.searchNonApprovedPost();
	            	
	            	if(posts != null){
	            		for (Post p : posts) {
			         	    jsonObj.put("aprovado ", p.isApproved());
			         	    jsonObj.put("titulo ", p.getTitle());
			         	    jsonObj.put("descricao ", p.getDescription());
			         	    jsonObj.put("imagem ", p.getImage());
			         	    jsonObj.put("nome usuario ", p.getUsername());
			         	    jsonObj.put("categoria ", p.getPostType());
			         	    jsonObj.put("data",
			         	    		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
			         	    			.format(p.getPostingDate()));
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	            jsonObj.put("mensagem", "Nenhum post encontrado.");
	            jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });
	}

	public void searchPostsByType() {
		get("/search/post/byType", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
	            try {
	            	LinkedList<Post> posts = model.searchPostsByType(request.params(":postType"));
	            	
	            	if(posts != null){
	            		for (Post p : posts) {
			         	    jsonObj.put("aprovado ", p.isApproved());
			         	    jsonObj.put("titulo ", p.getTitle());
			         	    jsonObj.put("descricao ", p.getDescription());
			         	    jsonObj.put("imagem ", p.getImage());
			         	    jsonObj.put("nome usuario ", p.getUsername());
			         	    jsonObj.put("categoria ", p.getPostType());
			         	    jsonObj.put("data",
			         	    		new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
			         	    			.format(p.getPostingDate()));
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	            jsonObj.put("mensagem", "Nenhum post encontrado.");
	            jsonResult.put(jsonObj);
    			return jsonResult;
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
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
        		jsonObj.put("mensagem", "Post aprovado com sucesso.");
        		jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });	
	}
}
