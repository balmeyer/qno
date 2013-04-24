package net.balmeyer.qno.dico;

import net.balmeyer.qno.Word;

public interface TypedWord extends Word {

	/** 
	 * Get raw definition (or type) in dictionary
	 * @return
	 */
	public String getDefinition();
	
	public void setDefinition(String definition);
	
}
