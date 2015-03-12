package net.balmeyer.qno.dico;

public class Adverb implements TypedWord {
	
	private String invariableText ;
	private Definition definition;
	
	public Adverb(String text) {
		invariableText = text;
		this.definition = new Definition("adv");
	}

	@Override
	public String toString(){
		return this.invariableText;
	}

	@Override
	public Definition getDefinition() {
		// TODO Auto-generated method stub
		return this.definition;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return Type.adverb;
	}
}
