package net.balmeyer.qno.news;

public class Category {

	
	public long category_id;
	
	public String display_category_name;
	
	public String english_category_name;
	
	public String url_category_name;
	
	
	@Override
	public String toString(){
		return "[" + category_id + "] " + display_category_name;
	
	}
	
}
