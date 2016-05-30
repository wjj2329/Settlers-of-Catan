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
		//System.out.println("I am sending stuff with the URL " + urlsuffix + " and the param " + data.getRequestType());
		//System.out.println("The url as a whole is " + urlprefix+serverhost+":"+serverport+urlsuffix);
		//System.out.println("MY DATA IS THIS" +data.toString());
		try {
			url = new URL(urlprefix+serverhost+":"+serverport+urlsuffix);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			
			//Sets it the request method as a POST or a GET depending on the param
			connection.setRequestMethod(data.getRequestType());
			connection.setDoOutput(true);
			
			//only the Param that use this are those that require cookies(some of the moves, and game operations)
			//it basically does addRequestProperty("Cookie", the user and game cookie string)
			if(data.getHeaders() != null){
				for(String string: data.getHeaders().keySet()){
					//System.out.println("i come in the this thing and stuff"+string);
					connection.addRequestProperty(string, data.getHeaders().get(string));
				}
			}
			//System.out.println("CLIENT COMMUNICATOR: I am here in the try and before the connection");
			connection.connect();
			//System.out.println("CLIENT COMMUNICATOR: After the connection");

			//If the Param is a POST request it gives the request body that is formed in the Param Object. 
			if(data.getRequestType().equals("POST")){
				OutputStream requestBody = connection.getOutputStream();
	            requestBody.write(data.getRequest().getBytes());
	            requestBody.close();
			}
			//System.out.println("CLIENT COMMUNICATOR: Am I here eh?");
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				//System.out.println("CLIENT COMMUNICATOR: this is a crap response code. ");
			}
			else
			{
				//System.out.println("CLIENT COMMUNICATOR: A-OK");
			}
			System.out.println(connection.getResponseCode());
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//System.out.println("CLIENT COMMUNICATOR: Http was okay");
				 Map<String, List<String>> headers = connection.getHeaderFields();
				 
				 //This is where it gets the cookies. 
				String galleta = connection.getHeaderField("Set-cookie");
                InputStream responseBody = connection.getInputStream();
				//System.out.println(" I SURVIVE THE COOKIE ORDEAL");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }
				System.out.println("I COMHE ERER");
                String responseBodyData = baos.toString();
                //System.out.println("CLIENT COMMUNICATOR: "+responseBodyData + " y la galleta " + galleta);
                ServerResponse response = new ServerResponse(connection.getResponseCode(), responseBodyData);
                response.setCookie(galleta);
                return response;
            }
            else {
				//System.out.println("This was a bad request");
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
