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
	
	public Subcategory[] getSubcategories(){
		return (Subcategory[]) this.geJson(API_SUBCATEGORIES, Subcategory[].class);
	}
	
	public Subcategory[] getSubcategories(Category parent){
		Subcategory[] subs = (Subcategory[]) this.geJson(API_CATEGORIES + "/" 
				+ parent.category_id + "/" + API_SUBCATEGORIES, Subcategory[].class);
		
		for(Subcategory s : subs) s.parent = parent;
		
		return subs;
	}
	
	public Articles getArticles(Category category){
		return (Articles) this.geJson(API_CATEGORIES +"/"  + category.category_id + "/" + API_ARTICLES, Articles.class);
	}
	
	public Article[] getArticle(Subcategory subcategory){
		return (Article[]) this.geJson(API_CATEGORIES +"/"  + subcategory.category_id 
				+ "/" + API_SUBCATEGORIES + "/" + subcategory.subcategory_id
				+ "/" + API_ARTICLES
				, Article[].class);
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
	
	private String sendRequest(String query){
		return this.sendRequest(query , null);
	}
	
	/**
	 * 
	 * @param query
	 * @param claz
	 * @return
	 */
	private String sendRequest(String query , String format){
		if (format == null) format = this.format;
		
		StringBuilder sb = new StringBuilder(API_BASE);
		sb.append('/');
		sb.append(API_VERSION);
		sb.append('/');
		sb.append(query);
		sb.append('.');
		sb.append(format);
		sb.append("?culture_code=");
		sb.append(this.culture);
		
		Client client = ClientBuilder.newClient();
		WebTarget myResource = client.target(sb.toString());
		
		String result = null;
		System.out.println("Requête : " + query);
		try {
			result = myResource.request().get(String.class);
		}catch(Exception ex){
			System.out.println("Erreur requête : " + query);
			ex.printStackTrace();
			throw ex;
		}
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
