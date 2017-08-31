package hello;



import static spark.Spark.get;
import static spark.Spark.post;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.List;

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
		        	user.setAccountType((byte)1); // 0 = admin / 1 = user
	        	
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
		post("/add/post", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONObject json = new JSONObject(request.body());
	        	
	        	String postTitle = json.getString("title");
	        	String description = json.getString("description");
	        	String username = json.getString("username");
	        	String postType = json.getString("postType");
	        	
	        	double latitude = json.getDouble("latitude");
	        	double longitude = json.getDouble("longitude");
	        	Location location = new Location(latitude, longitude);
	        	
	        	// FIXME
	        	/*String imgDescription = json.getString("imgDescription");
	        	String imgUrl = json.getString("imgUrl");
	        	String imgIcon = json.getString("imgIcon");
	        	Image image = new Image(imgDescription, imgUrl, imgIcon);*/      
	        	
	        	Post post = new Post();
	        	post.setTitle(postTitle);
	        	post.setDescription(description);
	        	post.setUsername(username);
	        	post.setLocation(location);
	        	//post.setImage(image);
	        	post.setPostType(postType);
	        	post.setPostingDate(Calendar.getInstance()); 
	        	
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
	            try {
	            	List<Post> posts = model.searchApprovedPost();
	            	
	            	if(posts != null){
	            		for (Post p : posts) {
	        	        	JSONObject jsonObj = new JSONObject();
			         	    jsonObj.put("title", p.getTitle());
			         	    jsonObj.put("description", p.getDescription());
			         	    jsonObj.put("image", p.getImage());
			         	    jsonObj.put("latitue", p.getLocation().getLatitude());
			         	    jsonObj.put("longitude", p.getLocation().getLongitude());
			         	    jsonObj.put("username", p.getUsername());
			         	    jsonObj.put("postType", p.getPostType());
			         	    //jsonObj.put("postingDate", p.getPostingDate().toString()); //FIXME
			         	    jsonObj.put("approved", p.isApproved());
			         	    		
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	}
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("mensagem", "Nenhum post encontrado.");
	            jsonObj.put("status", 0);
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
	            try {
	            	List<Post> posts = model.searchNonApprovedPost();
	            	
	            	if(posts != null){
	            		for (Post p : posts) {
	            			JSONObject jsonObj = new JSONObject();
			         	    jsonObj.put("approved", p.isApproved());
			         	    jsonObj.put("title", p.getTitle());
			         	    jsonObj.put("description", p.getDescription());
			         	    jsonObj.put("image", p.getImage());
			         	    jsonObj.put("username", p.getUsername());
			         	    jsonObj.put("postType", p.getPostType());
			         	    jsonObj.put("postingDate", p.getPostingDate().toString());
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
						return jsonResult;
	            	} else { }
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("mensagem", "Nenhum post encontrado.");
	            jsonObj.put("status", 0);
	            jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });
	}

	public void searchPostsByType() {
		get("/search/post/byType/:postType", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	JSONArray jsonResult = new JSONArray();
	        	String postType = request.params(":postType");
	        	
	        	System.out.println("search post by type: "  + postType);
	            try {
	            	List<Post> posts = model.searchPostsByType(postType);
	            	
	            	if(posts != null){
	            		for (Post p : posts) {
	            			JSONObject jsonObj = new JSONObject();
	            			jsonObj.put("approved", p.isApproved());
			         	    jsonObj.put("title", p.getTitle());
			         	    jsonObj.put("description", p.getDescription());
			         	    jsonObj.put("image", p.getImage());
			         	    jsonObj.put("username", p.getUsername());
			         	    jsonObj.put("postType", p.getPostType());
			         	    jsonObj.put("postingDate", p.getPostingDate().toString());
			         	   
			         	    jsonResult.put(jsonObj);	            			
	            		}
		             	
	            		System.out.println("Encontrado " + jsonResult.length() + " posts de " + postType);
						return jsonResult;
	            	}
        		} catch (JSONException e) {
        			e.printStackTrace();
        		}
	            JSONObject jsonObj = new JSONObject();    	
	            jsonObj.put("mensagem", "Nenhum post da categoria " + postType + " encontrado.");
	            jsonObj.put("status", 0);
	            jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });
	}
	
	public void approvePost() {
		post("/approve/post", new Route() {
			@Override
            public Object handle(final Request request, final Response response){
				JSONObject json = new JSONObject(convertJSONString(request.body()));

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	        	// FIXME 
	        	for (Post p : model.searchNonApprovedPost()) {
					if(p.getTitle().equals(json.getString("title"))) {
						model.approvePost(p);
						break;
					}
				}
	        	
	        	JSONArray jsonResult = new JSONArray();
	        	JSONObject jsonObj = new JSONObject();
        		jsonObj.put("mensagem", "Post aprovado com sucesso.");
        		jsonObj.put("status", 1);
        		jsonResult.put(jsonObj);
    			return jsonResult;
	         }
	      });	
	}
	
	private String convertJSONString(String str){
		
		Charset utf8charset = Charset.forName("UTF-8");
		Charset iso88591charset = Charset.forName("ISO-8859-1");
	
		ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());
	
		// decode UTF-8
		CharBuffer data = utf8charset.decode(inputBuffer);
	
		// encode ISO-8559-1
		ByteBuffer outputBuffer = iso88591charset.encode(data);
		byte[] outputData = outputBuffer.array();
		
		str = new String(outputData);
		
		return str;
	}
	
}
