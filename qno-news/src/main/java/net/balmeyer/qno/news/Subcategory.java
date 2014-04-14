package net.balmeyer.qno.news;

public class Subcategory {
	
	public long category_id;
	
	public long subcategory_id ;
	
	public String display_subcategory_name;
	
	public String english_subcategory_name;
	
	public String url_subcategory_name;
	
	public Category parent;
	
	@Override
	public String toString(){
		return "[" + subcategory_id + "] " + display_subcategory_name;
	
	}
}
