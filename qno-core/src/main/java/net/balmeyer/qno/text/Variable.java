package net.balmeyer.qno.text;

public class Variable {

	private String ID;
	private String text;
	private int start;
	private int end;
	
	public String getID() {
		if (ID == null && text != null){
			this.ID = text.substring(text.indexOf("{")+1);
			this.ID = ID.substring(0 , ID.indexOf("}")).toLowerCase();
		}
		return this.ID;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}

	
}
