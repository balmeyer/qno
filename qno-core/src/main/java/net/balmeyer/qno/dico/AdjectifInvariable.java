package net.balmeyer.qno.dico;

public class AdjectifInvariable extends Adjectif {

	private String invariableText ;
	
	public AdjectifInvariable(String text) {
		super(text);
		invariableText = text;
	}

	@Override
	public String toString(){
		return this.invariableText;
	}
	
}
