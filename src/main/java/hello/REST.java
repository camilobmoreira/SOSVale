package hello;



import static spark.Spark.get;
import static spark.Spark.post;


import java.io.UnsupportedEncodingException;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;


public class REST{
	
	private Model model;
	
	
	public REST(Model store){
		this.model = store;
	}
	
	
	public void logarAdmin(){
		
		//get("/login/:username/:password", new Route() {
		get("/loginAdmin/:username/:password", new Route() {
			@Override
            public Object handle(final Request request, final Response response){

	        	response.header("Access-Control-Allow-Origin", "*");
	            
	            try 
	            {
	            	Admin admin = model.logarAdmin(request.params(":username"), request.params(":password"));
	            	
	            	if(admin != null){
	            		JSONArray jsonResult = new JSONArray();
		         	    JSONObject jsonObj = new JSONObject();

		         	   jsonObj.put("email", admin.getConta().getEmail()); 
		        		
		             	jsonResult.put(jsonObj);
		             	
		             	return jsonResult;
	            		
	            	} else {}
        		} catch (JSONException e) {
        			//e.printStackTrace();
        		}
	         	    	
	
	            JSONArray jsonResult = new JSONArray();
         	    JSONObject jsonObj = new JSONObject();

        		jsonObj.put("email", "");
        		
        		
             	jsonResult.put(jsonObj);
             	
             	return jsonResult;
	            
	     	     
	         }
	         
	      });
			

	}
	
	
}
