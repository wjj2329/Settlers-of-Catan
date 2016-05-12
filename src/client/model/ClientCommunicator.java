package client.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import org.json.JSONObject;
import server.param.Param;
import server.response.ServerResponse;

/**
 * @author William
 * ClientCommunicator class: communicates with the client via the proxy.
 */
public class ClientCommunicator
{
	/**
	 * serverhost: the URL of the server.
	 */
	String serverhost = "localhost";
	
	/**
	 * serverport: which port we are accessing the server from
	 */
	String serverport = "8081";
	
	/**
	 * The prefix to the URL. 
	 */
	String urlprefix = "http://";
	
	/**
	 * Send function. Sends the message to the proxy. 
	 * @param urlsuffix: the URL address.
	 * @param data: any data that is included. Read it from the JSON as a string,
	 * but implemented as an object. 
	 * @pre urlpath and data are not null
	 * @exception: throws exception if URL path or Data are invalid. 
	 */
	public ServerResponse send(String urlsuffix, Param data) {
		URL url;
		try {
			url = new URL(urlprefix+serverhost+":"+serverport+urlsuffix);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(data.getRequestType());
			connection.setDoOutput(true);
			
			if(data.getHeaders() != null){
				for(String string: data.getHeaders().keySet()){
					connection.addRequestProperty(string, data.getHeaders().get(string));
				}
			}
			
			connection.connect();

			

			if(data.getRequestType().equals("POST")){
				OutputStream requestBody = connection.getOutputStream();
	            requestBody.write(data.getRequest().getBytes());
	            requestBody.close();
			}

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				 Map<String, List<String>> headers = connection.getHeaderFields();
				String galleta = connection.getHeaderField("Set-cookie");
                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                String responseBodyData = baos.toString();
                ServerResponse response = new ServerResponse(galleta, connection.getResponseCode());
                response.setCookie(galleta);
                
                return response;
            }
            else {
            	ServerResponse response = new ServerResponse();
            	response.setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST);
                return response;

            }


		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return null;
	}
	
	/**
	 * SerializeModel: Serializes all our data to JSON so that the server can have
	 * the data.
	 */
	ModelFacade myobject=new ModelFacade();

	//william
	public JSONObject serializeModel() throws JSONException {

		return myobject.serializeModel();
	}
	
	/**
	 * updateFromJSON: The opposite of SerializeModel. Reads JSON data and 
	 * puts it into the model. 
	 */
	public void updateFromJSON(JSONObject given) throws JSONException {
			myobject.updateFromJSON(given);
	}
}
