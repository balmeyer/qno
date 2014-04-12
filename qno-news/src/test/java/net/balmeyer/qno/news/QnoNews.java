package net.balmeyer.qno.news;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientResponse;

import com.google.gson.Gson;



/**
 * 
 * @author vovau
 *
 */
public class QnoNews {
	
	public static final String API_BASE = "http://api.feedzilla.com";
	public static final String API_VERSION = "v1";
	public static final String API_CULTURES = "cultures";
	public static final String API_CATEGORIES = "categories";
	public static final String API_SUBCATEGORIES = "subcategories";
	public static final String API_ARTICLES = "articles";

	private String format = "json";
	private String culture = "fr";
	
	private Gson gson = new Gson();
	
	public static void main(String [] args) {
		
		QnoNews qn = new QnoNews();
		
		qn.getCultures();
		Category[]cats = qn.getCategories();
		
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target(API_CULTURES);
		
		String v = myResource.request().get(String.class);
		
		System.out.println(v);
		
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Culture [] getCultures(){
		String text = sendRequest(API_CULTURES);
		
		Object result = convertJson(text, Culture[].class);
		
		return (Culture[]) result;
	}
	
	/**
	 * 
	 * @return
	 */
	public Category[] getCategories(){
		return (Category[]) this.geJson(API_CATEGORIES, Category[].class);
	}
	
	/**
	 * 
	 * @param query
	 * @param claz
	 * @return
	 */
	private Object geJson(String query, Class claz){
		
		String text = sendRequest(query);
		return convertJson(text, claz);
		
	}
	
	/**
	 * 
	 * @param query
	 * @param claz
	 * @return
	 */
	private String sendRequest(String query ){
		StringBuilder sb = new StringBuilder(API_BASE);
		sb.append('/');
		sb.append(API_VERSION);
		sb.append('/');
		sb.append(query);
		sb.append('.');
		sb.append(this.format);
		sb.append("?culture_code=");
		sb.append(this.culture);
		
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target(sb.toString());
		
		String result = myResource.request().get(String.class);
		return result;
		
	}
	
	/**
	 * 
	 * @param data
	 * @param claz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object convertJson(String data, Class claz){
		return this.gson.fromJson(data, claz);
	}
	
}
