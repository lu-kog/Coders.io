package utils;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.*;


public class JSON {

	static Logger logger = new CommonLogger(JSON.class).getLogger();
	
	
	public static JSONObject Create(int code, String msg) {

		JSONObject responseObj = new JSONObject();
		try {
			responseObj.put("statuscode", code);
			responseObj.put("message", msg);
		} catch (JSONException e) {
			logger.error("Json Exception on creating new errorObject");
		}
		
		return responseObj;
	}
		

}
