package net.balmeyer.qno.dico;

public class Definition {

	private String definition ;
	
	public Definition(String def){
		this.definition = def;
		if (def == null){
			throw new IllegalArgumentException("Definition text cannot be null !");
		}
	}
	
	public Definition(Type type){
		this.definition = type.toString().substring(0,1) ;
	}
	
	public Definition(Type type, Gender gender){
		this.definition = type.toString().substring(0,1) 
				+ gender.toString().substring(0,1);
	}
	
	@Override
	public String toString(){
		return definition;
	}
	
	@Override
	public boolean equals(Object obj){
		Definition other = (Definition) obj;
		return other.toString().equals(this.toString());
	}
	
	@Override
	public int hashCode(){
		return this.definition.hashCode();
	}
	
}
