package net.balmeyer.qno.dico;

public abstract class TypeAndGenre {
	private Genre genre;
	private Type type;
	
	/**
	 * Define type and genre on a word
	 * @param e
	 * @param definition
	 */
	public static void define(TypeAndGenre e, String definition){
		if (definition.startsWith("n")) e.setType(Type.nom);
		if (definition.startsWith("v")) e.setType(Type.verbe);
		if (definition.startsWith("vi")) e.setType(Type.verbeIntransitif);
		if (definition.startsWith("adj")) e.setType(Type.adjectif);
		if (definition.startsWith("adv")) e.setType(Type.adverbe);
		
		if (definition.startsWith("nf")) e.setGenre(Genre.feminin);
		if (definition.startsWith("nm")) e.setGenre(Genre.masculin);
	}
	
	
	public Genre getGenre() {
		return genre;
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
}
